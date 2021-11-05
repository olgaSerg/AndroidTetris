package com.android.tetris;

public class Field {
   public Cell[][] cells;

    public Field() {
        cells = new Cell[16][10];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell((int) 0,true);
            }
        }
    }

    public Piece dropPiece(Piece piece) {
        return piece;
    }

    public boolean isOnField(Position position) {
        return position.row < cells.length && position.row >= 0 && position.column < cells[0].length && position.column >= 0;
    }

    public boolean canPut(Piece piece) {
        int[][] curPiece = piece.shape.getArray();
        for (int i = 0; i < curPiece.length; i++) {
            for (int j = 0; j < curPiece[0].length; j++) {
                if (curPiece[i][j] == 1 && (!isOnField(new Position(piece.position.row + i, piece.position.column + j)) || !cells[i + piece.position.row][j + piece.position.column].isEmpty)) {
                    return false;
                }
            }
        }
        return true;
    }

//    boolean isRowEmpty() {}
//    void draw(Canvas canvas) {}
}
