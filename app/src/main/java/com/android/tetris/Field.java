package com.android.tetris;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Field {
    public Cell[][] cells;

    public Field() {
        cells = new Cell[16][10];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                cells[i][j] = new Cell(0, true);
            }
        }
    }

    public int getHeight() {
        return cells.length;
    }

    public int getWidth() {
        return cells[0].length;
    }

    public Piece dropPiece(Piece piece) {
        Piece droppedPiece = piece;
        while (canPut(droppedPiece.shiftDown())) {
            droppedPiece = droppedPiece.shiftDown();
        }
        return droppedPiece;
    }

    public boolean isOnField(Position position) {
        return position.row < getHeight() && position.row >= 0 && position.column < getWidth() && position.column >= 0;
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

    boolean isRowComplete(int row) {
        for (int j = 0; j < getWidth(); j++) {
            if (cells[row][j].isEmpty) {
                return false;
            }
        }
        return true;
    }

    void draw(Canvas canvas) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                cells[i][j].draw(new Position(i, j), canvas);
            }
        }
    }

    void putPiece(Piece piece) {
        int[][] pieceArray = piece.shape.getArray();
        for (int i = 0; i < pieceArray.length; i++) {
            for (int j = 0; j < pieceArray[0].length; j++) {
                if (pieceArray[i][j] == 1) {
                    cells[i + piece.position.row][j + piece.position.column].isEmpty = false;
                    cells[i + piece.position.row][j + piece.position.column].color = piece.shape.getColor();
                }
            }
        }
    }

    public void removeRow(int row) {
        for (int k = row; k >= 1; k--) {
            for (int l = 0; l < getWidth(); l++) {
                cells[k][l] = cells[k - 1][l];
            }
        }
    }

    public ArrayList<Integer> getCompleteRows() {
        ArrayList<Integer> completeRows = new ArrayList<>();
        for (int i = 0; i < getHeight(); i++) {
            if (isRowComplete(i)) {
                completeRows.add(i);
            }
        }
        return completeRows;
    }
}
