package com.example.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class SnakeActivity extends Activity {

    // Declare an instance of SnakeGame
    private Viewer view;
    private SnakeGame mSnakeGame;


    // Set the game up
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the SnakeEngine class
        view = new Viewer(this);
        mSnakeGame = new SnakeGame(this,size,view);
        view.registerObserver(mSnakeGame);
        // Create a new instance of the Draw clas


        // Make snakeEngine the view of the Activity
        setContentView(view);

    }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        mSnakeGame.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        mSnakeGame.pause();
    }
}
