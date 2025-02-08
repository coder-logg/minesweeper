package ru.studiotg.minesweeper.dto;

import ru.studiotg.minesweeper.model.Minesweeper;

import java.util.UUID;

import static ru.studiotg.minesweeper.Utils.convertToWrapperArray;

public record GameInfoResponseDto (
        UUID gameId,
        Integer width,
        Integer height,
        Integer minesCount,
        Boolean completed,
        Character[][] field
) {
    public GameInfoResponseDto(Minesweeper gameInfo) {
        this(gameInfo.getGameId(), gameInfo.getWidth(), gameInfo.getHeight(), gameInfo.getMinesCount(), false,
                convertToWrapperArray(gameInfo.getFieldView(), gameInfo.getWidth(), gameInfo.getHeight()));
    }

}