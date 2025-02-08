package ru.studiotg.minesweeper.model;

import lombok.*;
import ru.studiotg.minesweeper.dto.GameInfoResponseDto;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static ru.studiotg.minesweeper.Utils.convertToWrapperArray;
import static ru.studiotg.minesweeper.service.MinesweeperService.*;

@Data
@AllArgsConstructor
public class Minesweeper {
    private final UUID gameId = UUID.randomUUID();
    private final Integer width;
    private final Integer height;
    private final Integer minesCount;
    @Setter(value = AccessLevel.PRIVATE)
    private Boolean completed;
    private final char[][] field;
    private final ArrayList<Point> minesCoordinate;
    private final char[][] fieldView;

    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private int openedCellsCounter = 0;

    private final boolean[][] revealed;


    public Minesweeper(Integer width,
                       Integer height,
                       Integer minesCount,
                       char[][] field,
                       ArrayList<Point> minesCoordinates,
                       char[][] fieldView) {
        this(width, height, minesCount, false, field, minesCoordinates, fieldView, 0, new boolean[height][width]);
    }

    public GameInfoResponseDto mapToResponseDto(char[][] fieldView){
        return new GameInfoResponseDto(gameId, width, height, minesCount, completed, convertToWrapperArray(fieldView, width, height));
    }

    public void finish(boolean isWin) {
        this.setCompleted(true);
        if (!isWin) {
            for (int i = 0; i < height; i++) {
                fieldView[i] = Arrays.copyOf(field[i], field[i].length);
            }
            minesCoordinate.forEach(p -> this.fieldView[p.y][p.x] = EXPLODED_MINE_SIGN);
        } else
            minesCoordinate.forEach(p -> this.fieldView[p.y][p.x] = MINE_SIGN);
    }

    public void moveCellToView(int x, int y) {
        this.fieldView[y][x] = this.field[y][x];
        openedCellsCounter++;
    }

    public void openFreeCellsAroundAndTryFinish(int x, int y) {
        openFreeCellsAround(x, y);
        if (openedCellsCounter >= width * height - minesCoordinate.size())
            finish(true);
    }

    public void openFreeCellsAround(int x, int y) {
        openCellAndLookAround(x - 1, y - 1);
        openCellAndLookAround(x - 1, y);
        openCellAndLookAround(x - 1, y + 1);
        openCellAndLookAround(x, y + 1);
        openCellAndLookAround(x + 1, y + 1);
        openCellAndLookAround(x + 1, y);
        openCellAndLookAround(x + 1, y - 1);
        openCellAndLookAround(x, y - 1);
    }

    private void openCellAndLookAround(int x, int y) {
        if (isCellExistsAndNotMine(field, x, y) && !revealed[y][x]) {
            revealed[y][x] = true;
            moveCellToView(x, y);
            if (field[y][x] == FREE_CELL_SIGN)
                openFreeCellsAround(x, y);
        }
    }

    public static boolean isCellExistsAndNotMine(char[][] field, int x, int y) {
        return isCellExists(field, x, y) && field[y][x] != MINE_SIGN;
    }

    public static boolean isCellExists(char[][] field, int x, int y) {
        return y < field.length && y >= 0 && x < field[y].length && x >= 0;
    }


}
