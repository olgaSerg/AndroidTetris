package com.android.tetris;
import android.graphics.Canvas;


public class Piece {
    PieceShape shape;
    Position position;

    public Piece(PieceShape shape, Position position) {
        this.shape = shape;
        this.position = position;
    }


    public Piece rotate() {
        return new Piece(shape.rotate(), position);
    }

    void draw(Canvas canvas) {
        shape.draw(position.row, position.column, canvas);
    }

    PieceShadow getShadow(Field field) {
        int shadowShift = 0;
        while (field.canPut(new Piece(shape, new Position(position.row + shadowShift + 1, position.column)))) {
            shadowShift += 1;
        }
        return new PieceShadow(shape, new Position(position.row + shadowShift, position.column));
//        return new PieceShadow(shape, position.shiftBy(shadowShift, 0));
    }

    Piece shiftBy(int rowShift, int columnShift) {
        return new Piece(shape, position.shiftBy(rowShift, columnShift));
    }

    Piece shiftLeft() {
        return shiftBy(0, -1);
    }

    Piece shiftRight() {
        return shiftBy(0, 1);
    }

    Piece shiftDown() {
        return shiftBy(1, 0);
    }
}



//        Position position = new Position(0, 4);
//        PieceShape shape = new PieceShape(1, 3, Color.BLACK);
//        Piece p = new Piece(shape, position);