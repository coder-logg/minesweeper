package ru.studiotg.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.studiotg.minesweeper.dto.GameInfoResponseDto;
import ru.studiotg.minesweeper.dto.GameTurnRequestDto;
import ru.studiotg.minesweeper.dto.NewGameRequestDto;
import ru.studiotg.minesweeper.model.Minesweeper;
import ru.studiotg.minesweeper.service.MinesweeperService;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/new")
    public ResponseEntity<GameInfoResponseDto> createNewGame(@RequestBody @Valid NewGameRequestDto newGameDto) {
        if (newGameDto.getWidth() * newGameDto.getHeight() - 1 <= newGameDto.getMinesCount())
            throw new ValidationException("Mines count should be less than (cellsNumber - 1)");
        Minesweeper gameInfo = MinesweeperService.createGame(newGameDto);
        MapSession session = new MapSession(gameInfo.getGameId().toString());
        session.setAttribute("game", gameInfo);
        sessionRepository.save(session);
        return ResponseEntity.ok(new GameInfoResponseDto(gameInfo));
    }

    @PostMapping("/turn")
    public ResponseEntity<GameInfoResponseDto> turn(@RequestBody  @Valid  GameTurnRequestDto turnDto) {
        String sessionId = turnDto.gameId().toString();
        Session session = sessionRepository.findById(sessionId);
        Minesweeper gameInfo = session.getAttribute("game");
        if (gameInfo == null || !gameInfo.getGameId().equals(turnDto.gameId()))
            throw new IllegalStateException("Game was not created");
        GameInfoResponseDto response = MinesweeperService.openCells(gameInfo, turnDto.col(), turnDto.row());
        if (response.completed())
            sessionRepository.deleteById(sessionId);
        return ResponseEntity.ok(response);
    }
}


