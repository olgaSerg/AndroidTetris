package com.android.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class DrawingView extends View {

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(0.8f, 0.8f);
        ((MainActivity) getContext()).game.draw(canvas);
    }
}
