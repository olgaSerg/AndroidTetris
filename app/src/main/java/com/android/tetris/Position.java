package com.android.tetris;

public class Position {
    int row;
    int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }


//    Position shiftBy(int rowShift, int columnShift) {
//        return new Position(row + rowShift, column + columnShift);
//    }
//
//    Position shiftLeft() {
//        return shiftBy(0, -1);
//    }
//
//    Position shiftRight() {}
//    Position shiftUp() {}
//    public Position shiftDown() {
//        return new Position(row - 1, column);
//    }
//
}

