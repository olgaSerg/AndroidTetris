package com.android.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

   public Timer timer;
   MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        music = MediaPlayer.create(MainActivity.this, R.raw.tetris);
        music.start();
        startTimer();


//        timer.cancel();


    }
    public void startTimer() {
        final DrawingView drawView = findViewById(R.id.drawing);
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                drawView.tick();
            }
        }, 1000, 1000);
    }


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
            drawView.clickDown();
        }

        public void onClickRotation(View view) {
            DrawingView drawView = findViewById(R.id.drawing);
            drawView.clickRotation();
        }

    public void onClickPause(View view) {
        DrawingView drawView = findViewById(R.id.drawing);
        drawView.clickPause();
    }
}

