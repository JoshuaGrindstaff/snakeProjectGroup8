package com.example.snake;

import android.content.Context;
import android.graphics.Point;
import android.media.SoundPool;
import android.view.MotionEvent;

class SnakeGame implements Runnable, OnTouch {

    //Branch Test
    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // How many points does the player have
    private int mScore;

    // Objects for drawing


    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;

   private Audio sGS;
   private Viewer view;
    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size,Viewer view) {
        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;
        this.view = view;
        sGS = new Audio(5);
        sGS.load(context);
        // Call the constructors of our two game objects
        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        mSnake = new Snake(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

    }


    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mApple.spawn();

        // Reset the mScore
        mScore = 0;

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
            System.out.println("update time");
            view.updateViewer(mScore,mSnake,mApple);
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    // Update all the game objects
    public void update() {

        // Move the snake
        mSnake.move();

        // Did the head of the snake eat the apple?
        if(mSnake.checkDinner(mApple.getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();

            // Add to  mScore
            mScore = mScore + 1;

            // Play a sound
            sGS.playSound(0);
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            sGS.playSound(1);

            view.setPaused(true);
        }

    }



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

    @Override
    public void update(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (view.getPaused()) {
                    view.setPaused(false);
                    newGame();

                    // Don't want to process snake direction for this tap

                }

                // Let the Snake class handle the input
                mSnake.switchHeading(motionEvent);
                break;

            default:
                break;
        }
    }
}
