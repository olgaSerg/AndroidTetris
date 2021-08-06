package com.android.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Handler;

import static com.android.tetris.GameState.piece;


public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, drawPaintBlack, canvasPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;


    Random random = new Random();
    int paintColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

    GameState state = new GameState();


    int [][] fieldToDisplay;

    public void clickRight() {
        if (canPut(state.currentPieceRow, state.currentPieceColumn + 1)) {
            state.currentPieceColumn += 1;
            invalidate();
        };
    }

    public void clickLeft() {
        if (canPut(state.currentPieceRow, state.currentPieceColumn - 1)) {
            state.currentPieceColumn -= 1;
            invalidate();
        };
    }

    public boolean putDown() {
        if (!canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
            for (int i = 0; i < piece[state.rotationIndex].length; i++) {
                for (int j = 0; j < piece[state.rotationIndex][0].length; j++) {
                    if (piece[state.pieceType][state.rotationIndex][i][j] == 1) {
                        state.field[i + state.currentPieceRow][j + state.currentPieceColumn] = 1;
                    }
                }
            }
            return true;
        }
        return false;
    }

//        paintColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

    public void deleteEmptyRows() {
        for (int i = 0; i < state.field.length; i++) {
            int length = 0;
            for (int j = 0; j < state.field[0].length; j++) {
                if (state.field[i][j] == 1) {
                    length++;
                }
                if (length == state.field[0].length) {
                    deleteEmptyRow(i);
                }
            }
        }
    }

    public void deleteEmptyRow(int number) {
        for (int k = number; k >= 1; k--) {
            for (int l = 0; l < state.field[0].length; l++) {
                state.field[k][l] = state.field[k - 1][l];
            }
        }
    }

    public void tick() {
        moveDown();
        boolean landed = putDown();
        deleteEmptyRows();

        if (landed) {
            state.newPiece();
        }
        invalidate();
    }



    public void moveDown() {
        if (canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
            state.currentPieceRow += 1;
        };
    }

    public void clickRotation() {
        if (state.rotationIndex == 3) {
            state.rotationIndex = 0;
        } else {
            state.rotationIndex++;
        }
    }

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);

        state.field = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        };

        fieldToDisplay = new int[state.field.length][state.field[0].length];

        setupDrawing();

    }

    boolean canPut(int row, int column) {
        for (int i = 0; i < piece[state.pieceType][state.rotationIndex].length; i++) {
            for (int j = 0; j < piece[state.pieceType][state.rotationIndex][0].length; j++) {
                if (piece[state.pieceType][state.rotationIndex][i][j] == 1 && (!isOnField(i + row, j + column) || state.field[i + row][j + column] == 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isOnField(int row, int column) {
        if (row < state.field.length && row >= 0 && column < state.field[0].length && column >= 0)
            return true;
        else
            return false;
    }

    private void setupDrawing() {
//get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setStrokeWidth(10);

        drawPaintBlack = new Paint();
        drawPaintBlack.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//view given size
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap.eraseColor(Color.WHITE);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//draw view
        canvas.drawARGB(80, 102, 204, 255);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

        // очистить поле для текущего кадра
        for (int i = 0; i < fieldToDisplay.length; ++i) {
            for (int j = 0; j < fieldToDisplay[0].length; ++j) {
                fieldToDisplay[i][j] = 0;
            }
        }

        // копируем текущую фигурку на fieldToDisplay (слой 1)
//        [0; 4)
        for (int i = 0; i < piece[state.pieceType][state.rotationIndex].length; i++) {
            for (int j = 0; j < piece[state.pieceType][state.rotationIndex][0].length; j++) {
                if (piece[state.pieceType][state.rotationIndex][i][j] == 1) {
                    fieldToDisplay[i + state.currentPieceRow][j + state.currentPieceColumn] = 1;
                }
            }
        }

        // копируем "зафиксированные" клетки на fieldToDisplay (слой 2)
        for (int i = 0; i < state.field.length; i++) {
           for (int j = 0; j < state.field[0].length; j++) {
               if (state.field[i][j] == 1) {
                   fieldToDisplay[i][j] = 1;
               }
           }
        }

        for (int k = 0; k < fieldToDisplay.length; k++) {
            for (int l = 0; l < fieldToDisplay[0].length; l++) {
                if (fieldToDisplay[k][l] == 1) {
                    int marginSize = 3;
                    int margin2Size = 13;
                    canvas.drawRect(l * 100 + marginSize, k * 100 + marginSize, (l + 1) * 100 - marginSize - 1, (k + 1) * 100 - marginSize - 1, drawPaintBlack);
                    canvas.drawRect(l * 100 + margin2Size, k * 100 + margin2Size, (l + 1) * 100 - margin2Size - 1, (k + 1) * 100 - margin2Size - 1, drawPaint);
                }
            }
        }
    }
}
