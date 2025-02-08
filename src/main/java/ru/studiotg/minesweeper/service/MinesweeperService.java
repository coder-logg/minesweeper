package ru.studiotg.minesweeper.service;

import ru.studiotg.minesweeper.dto.GameInfoResponseDto;
import ru.studiotg.minesweeper.dto.NewGameRequestDto;
import ru.studiotg.minesweeper.model.Minesweeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static ru.studiotg.minesweeper.model.Minesweeper.isCellExistsAndNotMine;


public class MinesweeperService {

    public static final char MINE_SIGN = 'M';
    public static final char FREE_CELL_SIGN = '0';
    public static final char NOT_OPENED_CELL_SIGN = ' ';
    public static final char EXPLODED_MINE_SIGN = 'X';

    public static Minesweeper createGame(int width, int height, int minesCount) {
        char[][] field = createEmptyField(width, height, FREE_CELL_SIGN);
        ArrayList<Point> minesCoordinates = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < minesCount; ) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (field[y][x] == MINE_SIGN)
                continue;
            field[y][x] = MINE_SIGN;
            minesCoordinates.add(new Point(x, y));
            i++;
        }
        minesCoordinates.forEach((mine) -> {
            if (isCellExistsAndNotMine(field, mine.x - 1, mine.y - 1))
                increaseCell(field, mine.x - 1, mine.y - 1);

            if (isCellExistsAndNotMine(field, mine.x - 1, mine.y))
                increaseCell(field, mine.x - 1, mine.y);

            if (isCellExistsAndNotMine(field, mine.x - 1, mine.y + 1))
                increaseCell(field, mine.x - 1, mine.y + 1);

            if (isCellExistsAndNotMine(field, mine.x, mine.y + 1))
                increaseCell(field, mine.x, mine.y + 1);

            if (isCellExistsAndNotMine(field, mine.x + 1, mine.y + 1))
                increaseCell(field, mine.x + 1, mine.y + 1);

            if (isCellExistsAndNotMine(field, mine.x + 1, mine.y))
                increaseCell(field, mine.x + 1, mine.y);

            if (isCellExistsAndNotMine(field, mine.x + 1, mine.y - 1))
                increaseCell(field, mine.x + 1, mine.y - 1);

            if (isCellExistsAndNotMine(field, mine.x, mine.y - 1))
                increaseCell(field, mine.x, mine.y - 1);

        });
        return new Minesweeper(width, height, minesCount, field, minesCoordinates, createEmptyField(width, height, NOT_OPENED_CELL_SIGN));
    }

    public static Minesweeper createGame(NewGameRequestDto newGameDto) {
        return createGame(newGameDto.getWidth(), newGameDto.getHeight() - 1, newGameDto.getMinesCount());
    }

    public static GameInfoResponseDto openCells(Minesweeper game, int clickedX, int clickedY) {
        var cellVal = game.getField()[clickedY][clickedX];
        switch (cellVal) {
             case MINE_SIGN -> game.finish(false);
             case '1','2','3','4','5','6','7','8' -> {
                 game.moveCellToView(clickedX, clickedY);
             }
             case '0' -> {
                 game.moveCellToView(clickedX, clickedY);
                 game.openFreeCellsAroundAndTryFinish(clickedX, clickedY);
             }
        }
        return new GameInfoResponseDto(game);
    }

    public static char[][] createEmptyField(int width, int height, char fillSymbol) {
        char[][] coordinates = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(coordinates[i], fillSymbol);
        }
        return coordinates;
    }



    private static void increaseCell(char[][] field, int x, int y) {
        if (field[y][x] == FREE_CELL_SIGN)
            field[y][x] = '1';
        else field[y][x]++;
    }

}
