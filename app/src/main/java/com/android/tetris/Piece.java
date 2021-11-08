package com.android.tetris;


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

//    void draw(Canvas canvas) {}
//    PieceShadow getShadow(field) {}
//
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
//    Piece shiftUp() {} чдч
}



//        Position position = new Position(0, 4);
//        PieceShape shape = new PieceShape(1, 3, Color.BLACK);
//        Piece p = new Piece(shape, position);