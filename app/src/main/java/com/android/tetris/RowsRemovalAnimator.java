package com.android.tetris;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RowsRemovalAnimator extends GameAnimator {
    Game game;
    ArrayList<Integer> completeRows;
    int animationIndex;

    public RowsRemovalAnimator(Game game, ArrayList<Integer> completeRows) {
        this.game = game;
        this.completeRows = completeRows;

    }

    public void removeCompleteRows() {
        for (int rowIndex : completeRows) {
            game.state.field.removeRow(rowIndex);
        }
    }

    @Override
    public void start() {
        game.state.mode = "animation";
        final GameState state = game.state;
        game.timer.cancel();
        animationIndex = 0;
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (animationIndex > 5) {
                    game.state.mode = "game";
                    timer.cancel();
                    game.startTimer();
                    removeCompleteRows();
                    game.switchToNextPiece();
                    game.redraw();
                    return;
                }
                for (int index = 0; index < completeRows.size(); index++) {
                    state.field.cells[completeRows.get(index)][5 - animationIndex].isEmpty = true;
                    state.field.cells[completeRows.get(index)][5 - animationIndex].color = Color.WHITE;
                    state.field.cells[completeRows.get(index)][4 + animationIndex].isEmpty = true;
                    state.field.cells[completeRows.get(index)][4 + animationIndex].color = Color.WHITE;
                }
                game.redraw();
                animationIndex++;
            }
        }, 50, 50);
    }
}
