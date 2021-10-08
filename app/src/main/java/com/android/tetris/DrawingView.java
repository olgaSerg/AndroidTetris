package com.android.tetris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


//class Piece {
//    Piece(pieceType, rotationIndex, color)
//
//    rotate() {rotationIndex += 1}
//
//    int[][] getArray() {}
//}
//
// class Cell {
//    int color;
//    Cell(int color) {}
// }
//
// class Field {
//    int[][] field;
//    Cell[][] field;
//
//    boolean isOnField(int row, int column) {}
//    boolean isOnField(Position position) {}
//
//    void canPut(int row, int column, Piece piece) {}
//
//    void deleteEmptyRows() {}
//}
//
//class Position {
//    int row;
//    int column;
//
//    ??Position getLeft() {}
//    ??Position getRight() {}
//
//    Position add(Position other) {
//        return Position(row + other.row, column + other.column);
//    }
//}
//
//shift = Position(1, 0);
//
//source_cell = Position(5, 7)
//target_cell = source_cell.add(shift)
//
//target_array[target_cell.row][target_cell.column] = source_array[source_cell.row][source_cell.column]
//
//
//Position(0, 1)
//Position(-1, 0)


///void copyArrayTo(int[][] sourceArray, int[][] targetArray, rowShift, columnShift)

public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, drawPaintBlack, canvasPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;



    // TODO: вынести state в MainActivity, cделать чтобы вьюхи обращались к стейту через getContext()
    // Рисование следующей фигурки перенести в onDraw для соответствующей вьюхи (надо создать новую).
    GameState state = new GameState();


    public void clickRight() {
        if (!state.mode.equals("game")) return;
        if (canPut(state.currentPieceRow, state.currentPieceColumn + 1)) {
            state.currentPieceColumn += 1;
            invalidate();
        }
    }

    public void clickLeft() {
        if (!state.mode.equals("game")) return;
        if (canPut(state.currentPieceRow, state.currentPieceColumn - 1)) {
            state.currentPieceColumn -= 1;
            invalidate();
        }
    }

    public void clickDown() {
        if (!state.mode.equals("game")) return;
////      остановить старый таймер
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
//                    state.currentPieceRow += 1;
//                }
//            }
//        }, 500, 500);
//    }
        for (int i = 0; i < state.field.length; i++) {
            if (canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
                state.currentPieceRow += 1;
            }
        }
        invalidate();
    }


    public boolean putDown() {
        int[][] piece = state.currentPiece.getArray();
        if (!canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[0].length; j++) {
                    if (piece[i][j] == 1) {
                        state.field[i + state.currentPieceRow][j + state.currentPieceColumn].isEmpty = false;
                        state.field[i + state.currentPieceRow][j + state.currentPieceColumn].color = state.currentPiece.getColor();
                    }
                }
            } rememberCompletedRowsAndStartAnimation();
            return true;
        }
        return false;
    }

//        paintColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));


    public void rememberCompletedRowsAndStartAnimation() {
        int completedRowsCount = 0;
        int completedRowIndex = 0;
        for (int i = 0; i < state.field.length; i++) {
            int length = 0;
            for (int j = 0; j < state.field[0].length; j++) {
                if (!state.field[i][j].isEmpty) {
                    length++;
                }
                if (length == state.field[0].length) {
                    completedRowsCount++;
                    state.completedRows[completedRowIndex] = i;
                    completedRowIndex++;
                }
            }
        }
        deleteRowsWithAnimation();
        if (completedRowsCount > 0) {
            updateScore(completedRowsCount);
        }
    }

    public void displayScore() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        TextView scoreView = ((Activity) getContext()).findViewById(R.id.game_score);
                                                        // TODO: переписать на байндинг, по аналогии с:
                                                        // https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/552-urok-19-android-data-binding-vozmozhnosti.html
                                                        // <TextView
                                                        //           android:layout_width="wrap_content"
                                                        //           android:layout_height="wrap_content"
                                                        //           android:text="@{employee.name}" />


                                                        scoreView.setText(String.valueOf(state.score));
                                                    }
                                                }
        );
    }

    public void updateScore(int completedRowsCount) {
        int bonus = 0;
        if (completedRowsCount == 4) {
            bonus = 1200;
        } else if (completedRowsCount == 3) {
            bonus = 300;
        } else if (completedRowsCount == 2) {
            bonus = 100;
        } else if (completedRowsCount == 1) {
            bonus = 40;
        }
        state.score += bonus;
    }

    public void deleteCompletedRows() {
        for (int i = 0; i < state.completedRows.length; i++) {
            for (int k = state.completedRows[i]; k >= 1; k--) {
                for (int l = 0; l < state.field[0].length; l++) {
                    state.field[k][l] = state.field[k - 1][l];
                }
            }
        }
    }

    public void deleteRowsWithAnimation() {
        state.mode = "animation";
        ((MainActivity) getContext()).timer.cancel();
        state.animationIndex = 0;
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state.animationIndex > 5) {
                    state.mode = "game";
                    timer.cancel();
                    ((MainActivity) getContext()).startTimer();
                    deleteCompletedRows();
                    return;
                }
                for (int index = 0; index < state.completedRows.length; index++) {
                    state.field[state.completedRows[index]][5 - state.animationIndex].isEmpty = true;
                    state.field[state.completedRows[index]][5 - state.animationIndex].color = Color.WHITE;
                    state.field[state.completedRows[index]][4 + state.animationIndex].isEmpty = true;
                    state.field[state.completedRows[index]][4 + state.animationIndex].color = Color.WHITE;
                }
                invalidate();
                state.animationIndex++;
            }
        }, 50, 50);
    }


//        for (int i = 0; i < state.field[0].length; i++) {
//            Timer timer = new Timer(true);
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    state.field[row][i].color = Color.WHITE;
//                }
//            }, 1000, 1000);
//        }



    public void tick() {
        if (!state.mode.equals("game")) return;
        moveDown();
        boolean landed = putDown();
//        checkGameOver();
        if (landed) {
            state.switchToNextPiece();
        }
        displayScore();
        invalidate();
    }

    boolean isPaused = false;
    public void clickPause() {
        if (!isPaused) {
            isPaused = true;
            ((MainActivity) getContext()).timer.cancel();
            Button btnPause = ((MainActivity) getContext()).findViewById(R.id.button_pause);
            btnPause.setText("Continue");
            btnPause.setTextSize(11);
            ((MainActivity) getContext()).music.pause();
        } else {
            isPaused = false;
            Button btnPause = ((MainActivity) getContext()).findViewById(R.id.button_pause);
            btnPause.setText("Pause");
            ((MainActivity) getContext()).music.start();
            ((MainActivity) getContext()).startTimer();
        }
    }

    public void moveDown() {
        if (canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
            state.currentPieceRow += 1;
        }
        ;
    }

    public void clickRotation() {
        state.currentPiece.rotate();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        state.field = new Cell[16][10];
//        for (int i = 0; i < 19; i++) {
//            for (int j = 0; j < 10; j++) {
//                state.field[i][j] = new Cell((int) 0,true);
//            }
//        }
//        int k = 15;
//            for (int l = 0; l < 10; l++) {
//                if (l == 9) {
//                    state.field[k][l] = new Cell((int) 0, true);
//                } else
//                    state.field[k][l] = new Cell((int) 13369395, false);
//            }
//        k = 16;
//        for (int l = 0; l < 10; l++) {
//            if (l == 9) {
//                state.field[k][l] = new Cell((int) 0, true);
//            } else
//                state.field[k][l] = new Cell((int) 13369395, false);
//        }

//        for (int k = 15; k < 17; k++) {
//            for (int l = 0; l < 10; l++) {
//                if (l == 9) {
//                    state.field[k][l] = new Cell((int) 0, true);
//                } else
//                    state.field[k][l] = new Cell((int) 13369395, false);
//            }
//        }


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                state.field[i][j] = new Cell((int) 0,true);
            }
        }

        setupDrawing();
    }

    boolean canPut(int row, int column) {
        int[][] piece = state.currentPiece.getArray();
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 1 && (!isOnField(i + row, j + column) || !state.field[i + row][j + column].isEmpty)) {
                    return false;
                }
            }
        }
        return true;
    }


//    public void checkGameOver() {
//        if (!canPut(0, 4)) {
//            ((Activity) getContext()).runOnUiThread(new Runnable() {
//                public void run() {
//                    View view = ((Activity) getContext()).findViewById(R.id.game_over_view);
//
//                    view.setVisibility(View.VISIBLE);
//                    invalidate();
//
//                    ((MainActivity) getContext()).timer.cancel();
//                    state.mode = "game-over";
//                }
//            });
//        }
//    }


    boolean isOnField(int row, int column) {
        return row < state.field.length && row >= 0 && column < state.field[0].length && column >= 0;
    }

    private void setupDrawing() {
//get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.GRAY);
        drawPaint.setStrokeWidth(10);

        drawPaintBlack = new Paint();
        drawPaintBlack.setColor(Color.GRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//view given size
//        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        canvasBitmap.eraseColor(Color.WHITE);
//        drawCanvas = new Canvas(canvasBitmap);
    }

    void drawCell(int row, int column, int color, Canvas canvas) {
        int marginSize = 3;
        int margin2Size = 13;
        Paint cellPaint = new Paint();
        cellPaint.setColor(color);
        cellPaint.setStrokeWidth(10);
        canvas.drawRect(
                column * 100 + marginSize, row * 100 + marginSize,
                (column + 1) * 100 - marginSize - 1, (row + 1) * 100 - marginSize - 1,
                drawPaintBlack
        );
        canvas.drawRect(
                column * 100 + margin2Size, row * 100 + margin2Size,
                (column + 1) * 100 - margin2Size - 1, (row + 1) * 100 - margin2Size - 1,
                cellPaint
        );
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

    void drawPiece(Piece piece, Canvas canvas) {
        int[][] pieceArray = piece.getArray();
        for (int i = 0; i < pieceArray.length; i++) {
            for (int j = 0; j < pieceArray[0].length; j++) {
                if (pieceArray[i][j] == 1) {
                    drawCell(i, j, piece.getColor(), canvas);
                }
            }
        }
    }

    void drawCurrentPiece(Canvas canvas) {
        int[][] piece = state.currentPiece.getArray();
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 1) {
                    drawCell(i + state.currentPieceRow, j + state.currentPieceColumn, state.currentPiece.getColor(), canvas);
                }
            }
        }
    }

    void drawCurrentPieceShadow(Canvas canvas) {
        int shadowShift = 0;
        while (canPut(state.currentPieceRow + shadowShift + 1, state.currentPieceColumn)) {
            shadowShift += 1;
        }
        int[][] piece = state.currentPiece.getArray();
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 1) {
                    drawCellShadow(i + state.currentPieceRow + shadowShift, j + state.currentPieceColumn, state.currentPiece.getColor(), canvas);
                }
            }
        }
    }

    void drawField(Canvas canvas) {
        for (int i = 0; i < state.field.length; i++) {
            for (int j = 0; j < state.field[0].length; j++) {
                if (!state.field[i][j].isEmpty) {
                    drawCell(i, j, state.field[i][j].color, canvas);
                }
            }
        }
    }

    void drawNextPiece() {
        // runOnUithread ...
        Bitmap nextImageBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas nextImageCanvas = new Canvas(nextImageBitmap);

//        Piece nextPiece = new Piece(4, 3);
        Piece nextPiece = state.nextPiece;
        drawPiece(nextPiece, nextImageCanvas);

        ImageView imageView = ((Activity) getContext()).findViewById(R.id.next_piece_image);
        imageView.setImageBitmap(nextImageBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        canvas.scale(0.8f, 0.8f);
        canvas.drawARGB(0, 255, 255, 255);

        // рисуем "зафиксированные" клетки (слой 1)
        drawField(canvas);

        // рисуем текущую фигурку (слой 2)
        // [0; 4)
        if (state.mode.equals("game")) {
            drawCurrentPiece(canvas);
            drawCurrentPieceShadow(canvas);
        }

        drawNextPiece();
    }
}
