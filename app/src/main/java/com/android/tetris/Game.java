package com.android.tetris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

class Game {
    Context context;
    DrawingView drawingView;
    public Timer timer;
    MediaPlayer music;
    private GameState state;

    public Game(Context context, DrawingView drawingView) {
        this.context = context;
        this.state = new GameState();
        this.drawingView = drawingView;
        state.field = new Cell[16][10];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                state.field[i][j] = new Cell((int) 0,true);
            }
        }

//        for (int j = 1; j < 10; j++) {
//                state.field[15][j] = new Cell((int) 0,false);
//        }

    }

    public void start() {
        music = MediaPlayer.create(context, R.raw.tetris);
        music.start();
        startTimer();
    }

    public void startTimer() {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 1000, 1000);
    }

    public void redraw() {
        drawingView.invalidate();
    }

    public void clickLeft() {
        if (canPut(state.currentPieceRow, state.currentPieceColumn - 1)) {
            state.currentPieceColumn -= 1;
        }
        redraw();
    }

    public void clickRight() {
        if (!state.mode.equals("game")) return;
        if (canPut(state.currentPieceRow, state.currentPieceColumn + 1)) {
            state.currentPieceColumn += 1;
        }
        redraw();
    }

    public void clickDown() {
        if (!state.mode.equals("game")) return;
        for (int i = 0; i < state.field.length; i++) {
            if (canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
                state.currentPieceRow += 1;
            }
        }
        redraw();
    }

    public void clickRotation() {
        state.currentPiece.rotate();
        redraw();
    }

    boolean isPaused = false;

    public void clickPause() {
        if (!isPaused) {
            isPaused = true;
            timer.cancel();
            Button btnPause =((MainActivity) context).findViewById(R.id.button_pause);
            btnPause.setText("Continue");
            btnPause.setTextSize(11);
            music.pause();
        } else {
            isPaused = false;
            Button btnPause = ((MainActivity) context).findViewById(R.id.button_pause);
            btnPause.setText("Pause");
            music.start();
            startTimer();
        }
    }

    private void tick() {
        if (!state.mode.equals("game")) return;
        moveDown();
        boolean landed = putDown();
//        checkGameOver();
        if (landed) {
            state.switchToNextPiece();
        }
        displayScore();
        drawingView.invalidate();
    }

    private void deleteRowsWithAnimation() {
        state.mode = "animation";
        timer.cancel();
        state.animationIndex = 0;
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state.animationIndex > 5) {
                    state.mode = "game";
                    timer.cancel();
                    startTimer();
                    deleteCompletedRows();
                    redraw();
                    return;
                }
                for (int index = 0; index < state.completedRows.length; index++) {
                    state.field[state.completedRows[index]][5 - state.animationIndex].isEmpty = true;
                    state.field[state.completedRows[index]][5 - state.animationIndex].color = Color.WHITE;
                    state.field[state.completedRows[index]][4 + state.animationIndex].isEmpty = true;
                    state.field[state.completedRows[index]][4 + state.animationIndex].color = Color.WHITE;
                }
                redraw();
                state.animationIndex++;
            }
        }, 50, 50);
    }

    private void moveDown() {
        if (canPut(state.currentPieceRow + 1, state.currentPieceColumn)) {
            state.currentPieceRow += 1;
        }
    }

    public boolean canPut(int row, int column) {
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

    private boolean isOnField(int row, int column) {
        return row < state.field.length && row >= 0 && column < state.field[0].length && column >= 0;
    }

    private boolean putDown() {
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


    private void rememberCompletedRowsAndStartAnimation() {
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

    private void displayScore() {
        ((Activity) context).runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        TextView scoreView = ((Activity) context).findViewById(R.id.game_score);
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

    private void updateScore(int completedRowsCount) {
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

    private void deleteCompletedRows() {
        for (int i = 0; i < state.completedRows.length; i++) {
            for (int k = state.completedRows[i]; k >= 1; k--) {
                for (int l = 0; l < state.field[0].length; l++) {
                    state.field[k][l] = state.field[k - 1][l];
                }
            }
        }
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

    public void draw(Canvas canvas) {
        canvas.drawARGB(0, 255, 255, 255);
        if (state.mode.equals("game")) {
            drawCurrentPiece(canvas);
            drawCurrentPieceShadow(canvas);
        }
        drawNextPiece();
        drawField(canvas);
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

    void drawCell(int row, int column, int color, Canvas canvas) {
        int marginSize = 3;
        int margin2Size = 13;
        Paint drawPaintBlack = new Paint();
        drawPaintBlack.setColor(Color.GRAY);
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

        ImageView imageView = ((Activity) context).findViewById(R.id.next_piece_image);
        imageView.setImageBitmap(nextImageBitmap);
    }

}

