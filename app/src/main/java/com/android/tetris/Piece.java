package com.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;

public class Piece {
    PieceShape shape;
    Position position;

    public Piece(PieceShape shape, Position position) {
        this.shape = shape;
        this.position = position;
    }
//    public void rotate() {
//        if (rotationIndex == 3) {
//            rotationIndex = 0;
//        } else {
//            rotationIndex++;
//        }
//    }
    public Piece rotate() {
        return new Piece(shape.rotate(), position);
    }

//    void draw(Canvas canvas) {}
//    PieceShadow getShadow(field) {}
//
//    Piece shiftBy(int rowShift, int columnShift) {
//        return new Piece(shape, position.shiftBy(rowShift, columnShift));
//    }
//
//    Piece shiftLeft() {}
//    Piece shiftRight() {}
//    Piece shiftUp() {}
//    Piece shiftDown() {}


}
//        Position position = new Position(0, 4);
//        PieceShape shape = new PieceShape(1, 3, Color.BLACK);
//        Piece p = new Piece(shape, position);