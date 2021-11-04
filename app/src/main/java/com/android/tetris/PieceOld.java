package com.android.tetris;

import android.graphics.Color;

import java.util.Random;

public class PieceOld {
    private int pieceType;
    private int rotationIndex;
    private int color;

    public PieceOld(int pieceType, int rotationIndex, int color) {
        this.pieceType = pieceType;
        this.rotationIndex = rotationIndex;
        this.color = color;
    }

    public int getPieceType() {return pieceType;}
    public int getRotationIndex() {return rotationIndex;}
    public int getColor() {return color;}


    public void rotate() {
        if (rotationIndex == 3) {
            rotationIndex = 0;
        } else {
            rotationIndex++;
        }
    }

    public int[][] getArray() {
        return pieces[pieceType][rotationIndex];
    }

    int[] colors =  {
            Color.parseColor("#ff1744"),
            Color.parseColor("#ff3d00"),
            Color.parseColor("#ffea00"),
            Color.parseColor("#00e676"),
            Color.parseColor("#00b0ff"),
            Color.parseColor("#2a3eb1"),
            Color.parseColor("#ff3d00"),
    };

    // 0 - T
    // 1 - L
    // 2 - O
    // 3 - J
    // 4 - I
    // 5 - Z
    // 6 - S

    int[][][][] pieces = {
            {
                    {
                            {0, 0, 0, 0},
                            {0, 1, 0, 0},
                            {1, 1, 1, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 1, 0},
                            {0, 1, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {1, 1, 1, 0},
                            {0, 1, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 1, 0, 0},
                    }
            },
            {
                    {
                            {1, 0, 0, 0},
                            {1, 0, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 1, 1, 0},
                            {1, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 0, 1, 0},
                            {1, 1, 1, 0},
                            {0, 0, 0, 0},
                    },
            },
            {
                    {
                            {0, 0, 0, 0},
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
            },
            {
                    {
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 0, 0, 0},
                            {1, 1, 1, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 1, 0, 0},
                            {1, 0, 0, 0},
                            {1, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 1, 1, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 0},
                    },
            },
            {
                    {
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                    },
            },
            {
                    {
                            {0, 0, 0, 0},
                            {1, 1, 0, 0},
                            {0, 1, 1, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 1, 0, 0},
                            {1, 1, 0, 0},
                            {1, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {1, 1, 0, 0},
                            {0, 1, 1, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 1, 0, 0},
                            {1, 1, 0, 0},
                            {1, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
            },
            {
                    {
                            {0, 0, 0, 0},
                            {0, 1, 1, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 0, 0, 0},
                            {1, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {0, 0, 0, 0},
                            {0, 1, 1, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 0, 0, 0},
                            {1, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 0, 0},
                    },
            },
    };
}
