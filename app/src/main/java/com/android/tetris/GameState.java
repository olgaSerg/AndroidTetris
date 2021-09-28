package com.android.tetris;

import android.graphics.Color;

import java.util.Random;

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
        int rndColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Piece rndPiece = new Piece(rnd.nextInt(7), rnd.nextInt(4), rndColor);
        return rndPiece;
    }
}

// C N
// J T L O Z Z T S


