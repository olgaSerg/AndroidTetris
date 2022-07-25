package com.android.tetris;

import android.graphics.Canvas;

public class PieceShape {
    private int pieceType;
    private int rotationIndex;
    int color;

    PieceShape(int pieceType, int rotationIndex, int color) {
        this.color = color;
        this.pieceType = pieceType;
        this.rotationIndex = rotationIndex;
    }

    public int getColor() {
        return color;
    }

    public PieceShape rotate() {
        if (rotationIndex == 3) {
            rotationIndex = 0;
        } else {
            rotationIndex++;
        }
        return new PieceShape(pieceType, rotationIndex, color);
    }

    void draw(int rowShift, int columnShift, Canvas canvas) {
        Cell cell = new Cell(color, false);
        int[][] piece = getArray();
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 1) {
                    cell.draw(new Position(i + rowShift, j + columnShift), canvas);
                }
            }
        }
    }

    void draw(Canvas canvas) {
        draw(0, 0, canvas);
    }

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
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                    },
                    {
                            {1, 1, 0, 0},
                            {1, 1, 0, 0},
                            {0, 0, 0, 0},
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
