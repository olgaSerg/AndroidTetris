package com.android.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

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

