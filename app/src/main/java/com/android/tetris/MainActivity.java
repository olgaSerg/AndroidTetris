package com.android.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawingView drawView = findViewById(R.id.drawing);

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                drawView.tick();
            }
        }, 1000, 1000);

    }

//        public void click(View view) {
//            DrawingView drawView = findViewById(R.id.drawing);
//            drawView.changeArray();
//        }

        public void onClickRight(View view) {
            DrawingView drawView = findViewById(R.id.drawing);
            drawView.clickRight();
        }

        public void onClickLeft(View view) {
            DrawingView drawView = findViewById(R.id.drawing);
            drawView.clickLeft();
        }

        public void onClickDown(View view) {
            DrawingView drawView = findViewById(R.id.drawing);
            drawView.moveDown();
        }

        public void onClickRotation(View view) {
            DrawingView drawView = findViewById(R.id.drawing);
            drawView.clickRotation();
        }
}

