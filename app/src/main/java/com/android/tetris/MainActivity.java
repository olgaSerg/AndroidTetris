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

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


   public Game game;
   public static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = this;
        DrawingView drawingView = findViewById(R.id.drawing);
        game = new Game(appContext, drawingView);

        game.start();
    }


    public void clickRight(View view) { game.clickRight(); }

    public void clickLeft(View view) {
        game.clickLeft();
    }

    public void clickDown(View view) {
        game.clickDown();
    }

    public void clickRotation(View view) {
        game.clickRotation();
    }

    public void clickPause(View view) {
            game.clickPause();
        }
}

