package com.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    int color;
    boolean isEmpty;

    public Cell(int color, boolean isEmpty) {
        this.color = color;
        this.isEmpty = isEmpty;
    }

    void draw(Position position, Canvas canvas) {
        if (isEmpty) return;
        int marginSize = 3;
        int margin2Size = 13;
        Paint drawPaintBlack = new Paint();
        drawPaintBlack.setColor(Color.GRAY);
        Paint cellPaint = new Paint();
        cellPaint.setColor(color);
        cellPaint.setStrokeWidth(10);
        canvas.drawRect(
                position.column * 100 + marginSize, position.row * 100 + marginSize,
                (position.column + 1) * 100 - marginSize - 1, (position.row + 1) * 100 - marginSize - 1,
                drawPaintBlack
        );
        canvas.drawRect(
                position.column * 100 + margin2Size, position.row * 100 + margin2Size,
                (position.column + 1) * 100 - margin2Size - 1, (position.row + 1) * 100 - margin2Size - 1,
                cellPaint
        );
    }
}
