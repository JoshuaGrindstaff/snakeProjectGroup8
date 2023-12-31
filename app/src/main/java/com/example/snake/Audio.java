package com.example.snake;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import java.io.IOException;
import android.content.Context;
public class Audio
{
    private final SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;
    private int mJumpID = -1;

    Audio(int maxStreams)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void load(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("spring.wav");
            mJumpID = mSP.load(descriptor, 0);
        } catch (IOException e) {
            // Error
        }

    }
    void playSound(int soundID)
    {
        switch(soundID)
        {
            case 0:
                mSP.play(mEat_ID, 1, 1, 0, 0, 1);
                break;
            case 1:
                mSP.play(mCrashID, 1, 1, 0, 0, 1);
                break;
            case 2:
                mSP.play(mJumpID, 1,1,0,0,1);

        }
    }
}



