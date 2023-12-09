package com.example.snake;

import android.content.Context;
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

        mBadApple = new BadApple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        objects.addCollidableObject(mBadApple);

        mSnake = new Snake(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);

        parameters = new GameParameters();
        collide = new Collide(parameters,mSnake,sGS,objects);
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
            view.updateViewer(parameters.getScore(),mSnake,mApple,mBadApple,objects);
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
//*Tiaera: if (!mPaused) {
        //Spawn Power Ups
        if(objects.getPowerListSize() < MAX_NUMBER_OF_POWERUPS && 3 > random.nextInt(100))
        {
            Spring spring = new Spring(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            objects.addPowerList(spring);
            objects.addCollidableObject(spring);
            System.out.println("Loading Spring");
            spring.spawn();
        }
        // Move the snake

        mSnake.move();
        // Does the Snake Collide into anything that is collidable

        for(Collidable collidable : objects.getCollidableObjects())
        {
            if(mSnake.checkCollision(collidable))
            {
                collide.collide(collidable);
            }

        }
        System.out.println("" + objects.getCollidableObjectsSize());
        objects.removeCollidableObject();


        /*// Collisions with Apple
        // Did the head of the snake eat the apple?
        if(mSnake.checkCollision(mApple)){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();

            // Add to  mScore
            parameters.addScore(1);
            // Add length to snake

            mSnake.makeLonger();
            // Play a sound
            sGS.playSound(0);
        }

        //Collisions with powerups
        for(int i = 0; i < powerList.size();i++)
        {
           if(mSnake.checkCollision(powerList.get(i)))
           {
               parameters.setSpring();
               powerList.get(i).despawn();
               powerList.remove(i);
           }

        }

        }*/


        // Did the snake die?
        if (mSnake.detectDeath()) {
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
            }
        }

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
//*Tiaera: private void saveHighScores() {
//    // Use SharedPreferences for simplicity (you can use a file or a database for more complex scenarios)
//    SharedPreferences preferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE);
//    SharedPreferences.Editor editor = preferences.edit();
//
//    // Convert the list of HighScore objects to a JSON string
//    Gson gson = new Gson();
//    String json = gson.toJson(highScores);
//
//    // Save the JSON string
//    editor.putString("highScores", json);
//    editor.apply();
//}
//
//private void loadHighScores() {
//    SharedPreferences preferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE);
//    String json = preferences.getString("highScores", "");
//
//    // Convert the JSON string back to a list of HighScore objects
//    Gson gson = new Gson();
//    Type type = new TypeToken<List<HighScore>>(){}.getType();
//    highScores = gson.fromJson(json, type);
//
//    if (highScores == null) {
//        highScores = new ArrayList<>();
//    }
//}
//public class HighScoresActivity extends AppCompatActivity {
//    // ...
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_high_scores);
//
//        // Retrieve high scores from SnakeGame
//        SnakeGame snakeGame = new SnakeGame(getApplicationContext());
//        List<HighScore> highScores = snakeGame.getHighScores();
//
//        // Display high scores in a ListView or RecyclerView
//        // You may need to implement a custom adapter for better control over the layout
//        // ...
//    }
//}