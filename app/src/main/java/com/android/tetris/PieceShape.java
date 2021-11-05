package com.android.tetris;
import android.graphics.Color;

public class PieceShape {
    private int pieceType;
    private int rotationIndex;
    int color;

    PieceShape(int pieceType, int rotationIndex, int color) {
        this.color = color;
        this.pieceType = pieceType;
        this.rotationIndex = rotationIndex;
    }

    public int getColor() {return color;}

    public PieceShape rotate() {
        if (rotationIndex == 3) {
            rotationIndex = 0;
        } else {
            rotationIndex++;
        }
        return new PieceShape(pieceType, rotationIndex, color);
    }

//    void draw(int shiftX, int shiftY, Canvas canvas) {} ??
//    void draw(Canvas canvas) {}

    // 0 - T
    // 1 - L
    // 2 - O
    // 3 - J
    // 4 - I
    // 5 - Z
    // 6 - S

    int[] colors =  {
        Color.parseColor("#ff1744"),
        Color.parseColor("#ff3d00"),
        Color.parseColor("#ffea00"),
        Color.parseColor("#00e676"),
        Color.parseColor("#00b0ff"),
        Color.parseColor("#2a3eb1"),
        Color.parseColor("#ff3d00"),
    };

    public int[][] getArray() {
            return pieces[pieceType][rotationIndex];
    }

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
