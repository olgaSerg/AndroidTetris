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

    // TODO: вынести state в MainActivity, cделать чтобы вьюхи обращались к стейту через getContext()
    // Рисование следующей фигурки перенести в onDraw для соответствующей вьюхи (надо создать новую).

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//      view given size
//      canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//      canvasBitmap.eraseColor(Color.WHITE);
//      drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // change scale for different display size
        canvas.scale(0.8f, 0.8f);

        ((MainActivity) getContext()).game.draw(canvas);
    }
}
