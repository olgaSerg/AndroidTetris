package com.android.tetris;

import android.graphics.Color;

import java.util.Random;

import static java.security.AccessController.getContext;

// class Cell {
//     int color;
//     boolean isEmpty;
// }

public class GameState {
//    public int[][] field;
    public Cell[][] field;
    public int currentPieceRow;
    public int currentPieceColumn;
    public Piece currentPiece;
    public int score = 0;
    public Piece nextPiece;
    public int animationCellIndex;

    public String mode = "game"; // "pause" "game-over"

    public GameState() {
        initializePieces();
    }

    public void resetCurrentPieceLocation() {
        currentPieceRow = 0;
        currentPieceColumn = 4;
    }

    public void initializePieces() {
        currentPiece = generateRandomPiece();
        nextPiece = generateRandomPiece();
        resetCurrentPieceLocation();
    }

    public void switchToNextPiece() {
        currentPiece = nextPiece;
        nextPiece = generateRandomPiece();
        resetCurrentPieceLocation();
    }

    public Piece generateRandomPiece() {
        Random rnd = new Random();
        int rndColor = colors[new Random().nextInt(colors.length)];
        Piece rndPiece = new Piece(rnd.nextInt(7), rnd.nextInt(4), rndColor);
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

// C N
// J T L O Z Z T S


