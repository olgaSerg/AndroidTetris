package com.android.tetris;

public class Cell {
    int color;
    boolean isEmpty = true;

    public Cell(int color, boolean isEmpty) {
        this.color = color;
        this.isEmpty = isEmpty;
    }
//    public Cell(boolean isEmpty) {
//        this.isEmpty = isEmpty;
//    }

}
