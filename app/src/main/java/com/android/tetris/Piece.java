package com.android.tetris;

public class Piece {
    private String name;
    private int currentPieceRow;
    private int currentPieceColumn;

    public Piece (String name, int currentPieceRow, int currentPieceColumn) {
        this.name = name;
        this.currentPieceColumn = currentPieceColumn;
        this.currentPieceRow = currentPieceRow;
    }

    public String getName() {return name; }
    public int getCurrentPieceRow() {return currentPieceRow; }
    public int getCurrentPieceColumn() {return currentPieceColumn; }
}
