package com.android.tetris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;


class Game {
    Context context;
    DrawingView drawingView;
    public Timer timer;
    MediaPlayer music;
    public GameState state;
    boolean isPaused = false;
    public GameAnimator animator;

    public Game(Context context, DrawingView drawingView) {
        this.context = context;
        this.state = new GameState();
        this.drawingView = drawingView;

//      for (int j = 1; j < 10; j++) {
//          state.field[15][j] = new Cell((int) 0,false);
//      }
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
        if (state.field.canPut(state.piece.shiftLeft())) {
            state.piece = state.piece.shiftLeft();
        }
        redraw();
    }

    public void clickRight() {
        if (!state.mode.equals("game")) return;
        if (state.field.canPut(state.piece.shiftRight())) {
            state.piece = state.piece.shiftRight();
        }
        redraw();
    }

    public void clickDown() {
        if (!state.mode.equals("game")) return;
        state.piece = state.field.dropPiece(state.piece);
        redraw();
    }

    public void clickRotation() {
        state.piece.rotate();
        redraw();
    }

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

    public void switchToNextPiece() {
        state.piece = state.nextPiece;
        state.nextPiece = state.generateRandomPiece();

        checkGameOver();

//        piece = new Piece(nextPieceShape, new Position(0, 4));
//        nextPieceShape = generateRandomPieceShape();
//        resetCurrentPieceLocation();
    }

    private void handlePieceLanded() {
        ArrayList<Integer> completeRows = state.field.getCompleteRows();
        if (completeRows.size() > 0) {
            updateScore(completeRows.size());
            deleteRowsWithAnimation(completeRows);
        } else {
            switchToNextPiece();
        }
    }

    private void tick() {
        if (!state.mode.equals("game")) return;

        boolean landed = !state.field.canPut(state.piece.shiftDown());

        if (landed) {
            state.field.putPiece(state.piece);
            handlePieceLanded();
        } else {
            moveDown();
        }
        displayScore();
        redraw();
    }

    private void deleteRowsWithAnimation(ArrayList<Integer> completeRows) {
        animator = new RowsRemovalAnimator(this, completeRows);
        animator.start();
    }

    private void moveDown() {
        if (state.field.canPut(state.piece.shiftDown())) {
            state.piece = state.piece.shiftDown();
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


    public void checkGameOver() {
        if (!state.field.canPut(state.piece)) {
            state.mode = "game-over";
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                View view = ((Activity) context).findViewById(R.id.game_over_view);
                view.setVisibility(View.VISIBLE);
                music.stop();
                redraw();
                }
            });
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawARGB(0, 255, 255, 255);
        state.field.draw(canvas);
        state.piece.draw(canvas);

        PieceShadow currentPieceShadow = state.piece.getShadow(state.field);
        currentPieceShadow.draw(canvas);

        drawNextPiece();
    }

    void drawNextPiece() {
        // runOnUithread ...
        Bitmap nextImageBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas nextImageCanvas = new Canvas(nextImageBitmap);

        state.nextPiece.shape.draw(nextImageCanvas);

        ImageView imageView = ((Activity) context).findViewById(R.id.next_piece_image);
        imageView.setImageBitmap(nextImageBitmap);
    }
}

