package ru.studiotg.minesweeper;

public class Utils {
    public static Character[][] convertToWrapperArray(char[][] field, int width, int height){
        Character[][] tmp = new Character[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tmp[i][j] = field[i][j];
            }
        }
        return tmp;
    }
}
