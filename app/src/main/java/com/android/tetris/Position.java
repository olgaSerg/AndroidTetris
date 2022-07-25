package com.android.tetris;

public class Position {
    int row;
    int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position shiftBy(int rowShift, int columnShift) {
        return new Position(row + rowShift, column + columnShift);
    }
}

