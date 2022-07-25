package com.android.tetris;

import android.graphics.Canvas;
import android.graphics.Paint;

public class PieceShadow {
    PieceShape shape;
    Position position;

    public PieceShadow(PieceShape shape, Position position) {
        this.shape = shape;
        this.position = position;
    }

    void draw(Canvas canvas) {
        int[][] piece = shape.getArray();
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 1) {
                    drawCellShadow(i + position.row, j + position.column, shape.getColor(), canvas);
                }
            }
        }
    }

    void drawCellShadow(int row, int column, int color, Canvas canvas) {
        int marginSize = 3;
        Paint drawPaintShadow = new Paint();
        drawPaintShadow.setColor(color);
        drawPaintShadow.setStyle(Paint.Style.STROKE);
        drawPaintShadow.setStrokeWidth(10);
        canvas.drawRect(
                column * 100 + marginSize, row * 100 + marginSize,
                (column + 1) * 100 - marginSize - 1, (row + 1) * 100 - marginSize - 1,
                drawPaintShadow
        );
    }
}
