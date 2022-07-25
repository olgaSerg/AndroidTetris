package com.android.tetris;

import android.graphics.Color;
import java.util.Random;

public class GameState {
    public Field field;
    public int score = 0;
    public Piece piece;
    public Piece nextPiece;
    public String mode = "game"; // "pause" "game-over"

    public GameState() {
        this.field = new Field();
        initializePieces();
    }

    public void initializePieces() {
        piece = generateRandomPiece();
        nextPiece = generateRandomPiece();
    }

    public Piece generateRandomPiece() {
        Random rnd = new Random();
        int rndColor = colors[new Random().nextInt(colors.length)];
        Piece rndPiece = new Piece(new PieceShape(rnd.nextInt(7), rnd.nextInt(4), rndColor), new Position(0,4));
        return rndPiece;
    }

    int[] colors =  {
            Color.parseColor("#ff4569"),
            Color.parseColor("#33eb91"),
            Color.parseColor("#76ff03"),
            Color.parseColor("#33bfff"),
            Color.parseColor("#f50057"),
            Color.parseColor("#ffee33"),
            Color.parseColor("#dd33fa"),
    };
}



