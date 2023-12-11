package com.example.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SnakeGame implements Runnable, OnTouch {

    //Branch Test
    // Objects for the game loop/thread
    private final int MAX_NUMBER_OF_POWERUPS = 2;
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private long mNextMoveTime;
    private volatile boolean mPlaying = false;
    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;
    // How many points does the player have
    // A snake ssss
    private Snake mSnake;
    // And an apple
   private Apple mApple;
   private BadApple mBadApple;
   private Audio sGS;
   private Viewer view;
   private Context context;
   private Random random = new Random();
   private Collide collide;
   private int blockSize;
    private GameParameters parameters;
   private GameObjectLists objects;
    private SharedPreferences.Editor mEditor;
   private int TopRight;

    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size,Viewer view) {
        System.out.println("building snake game");
        // Work out how many pixels each block is
        blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;
        this.view = view;
        sGS = new Audio(5);
        sGS.load(context);
        this.context = context;
        objects = new GameObjectLists();
        // Call the constructors of our two game objects
        mApple = new Apple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        //Added to list of collidable objects
        objects.addCollidableObject(mApple);
        //Code from TextBook
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore",Context.MODE_PRIVATE);
        mEditor = prefs.edit();

        mBadApple = new BadApple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        objects.addCollidableObject(mBadApple);

        mSnake = new Snake(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);

        parameters = new GameParameters();
        collide = new Collide(parameters,mSnake,sGS,objects);
        TopRight = size.x;


        System.out.println(prefs.getInt("hi_score",0));
        parameters.setHighScore(prefs.getInt("hi_score",0));


    }
//*Tiaera: public class SnakeGame {
//    private List<HighScore> highScores;
//    private static final int MAX_HIGH_SCORES = 10; // Adjust as needed
//
//    // ...
//
//    private void checkHighScore() {
//        HighScore currentScore = new HighScore(playerName, mScore);
//        highScores.add(currentScore);
//        Collections.sort(highScores);
//
//        // Keep only the top MAX_HIGH_SCORES scores
//        if (highScores.size() > MAX_HIGH_SCORES) {
//            highScores = highScores.subList(0, MAX_HIGH_SCORES);
//        }
//
//        // Save high scores to SharedPreferences or a file
//        saveHighScores();
//    }
//
//    private void saveHighScores() {
//        // Implement saving high scores to SharedPreferences or a file
//    }
//
//    public List<HighScore> getHighScores() {
//        // Return the high scores list
//        return highScores;
//    }
//}

    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mApple.spawn();


        // Calls resetDeath in Parameters
        parameters.resetDeath();

        mBadApple.spawn();
        // Reset the mScore
        parameters.resetScore();


        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!view.getPaused()) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }
            //System.out.println("update time");

            view.updateViewer(parameters,mSnake,mApple,mBadApple,objects, mPlaying);

        }
    }


    // Check to see if it is time for an update
    public boolean moveRequired()
    {
        // Run at 10 frames per second
        final long TARGET_MPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextMoveTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextMoveTime = (long) (System.currentTimeMillis()
                    + MILLIS_PER_SECOND / (TARGET_MPS * parameters.getSpMult()));

            // Return true so that the update and draw
            // methods are executed
            return true;
        }
        return false;
    }
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 60;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime = (long) (System.currentTimeMillis()
                                + MILLIS_PER_SECOND / (TARGET_FPS));

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    // Update all the game objects
    public void update() {
//*Tiaera: if (!mPaused) {
        //Spawn Power Ups
        if(objects.getPowerListSize() < MAX_NUMBER_OF_POWERUPS && 3 > random.nextInt(100))
        {
            //Spring spring = new Spring(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            PowerUps power = new PowerUps(new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh),blockSize,context);
            objects.addPowerList(power);
            objects.addCollidableObject(power);
            //System.out.println("Loading Spring");
            power.spawn();
        }
        // Move the snake
        if(moveRequired()) {
            mSnake.move();
            for(Collidable collidable : objects.getCollidableObjects())
            {
                if(mSnake.checkCollision(collidable))
                {
                    collide.collide(collidable);
                }

            }
            if (mSnake.detectSelf()) {
                // Pause the game ready to start again
                if(parameters.getSpring())
                {
                    parameters.resetSpring();
                    sGS.playSound(2);
                }
                else
                {
                    sGS.playSound(1);
                    view.setPaused(true);
                    parameters.setDeath();
                }
            }
            if(mSnake.detectEdge())
            {
                sGS.playSound(1);
                view.setPaused(true);
                parameters.setDeath();
            }
        }
        // Does the Snake Collide into anything that is collidable


        //remove objects that need removing
        objects.removeCollidableObject();
        parameters.updateSpMult();
    }
//*Tiaera: } else {
//        // Handle touch events when the game is paused (game over screen)
//        view.update(motionEvent);
//    }
//}


    //    public boolean onTouchEvent(MotionEvent motionEvent) {
//        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_UP:
//                if (mPaused) {
//                    mPaused = false;
//                    newGame();
//
//                    // Don't want to process snake direction for this tap
//                    return true;
//                }
//
//                // Let the Snake class handle the input
//                mSnake.switchHeading(motionEvent);
//                break;
//
//            default:
//                break;
//
//        }
//        return true;
//    }
    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();

    }

    public void checkForPause(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if(!mPlaying) {
            resume();

        }
        // Check if the touch is within the restart option
        if (x >= TopRight - 100 && x <= TopRight  && y >= 0 && y <= 100) {
            // Restart the game
            pause();
        }
    }

    @Override
    public void update(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                checkForPause(motionEvent);


                if (view.getPaused()) {
                    if(parameters.getGameOver())
                    {
                        mEditor.putInt("hi_score", parameters.getHighScore());
                        mEditor.commit();
                        parameters.resetDeath();
                        parameters.setShowScore(true);
                    }
                    else
                    {
                        view.setPaused(false);
                        parameters.setShowScore(false);
                        newGame();
                    }
                    // Don't want to process snake direction for this tap

                }



                      /*  float x = motionEvent.getX();
                        float y = motionEvent.getY();

                        // Check if the touch is within the restart option
                        if (x >= 200 && x <= 500 && y >= 400 && y <= 480) {
                            // Restart the game
                            view.setPaused(false);
                            newGame()
                        }

                        // Check if the touch is within the return to start screen option
                        if (x >= 200 && x <= 700 && y >= 600 && y <= 680) {
                            // Handle returning to the start screen (implement as needed)
                            // You might want to create a method in SnakeActivity to start a new game or return to the start screen.
                        }
                    } else {*/
                 //Let the Snake class handle the input
                mSnake.switchHeading(motionEvent);
                break;

            default:
                break;
        }
    }
}
//*Tiaera:
