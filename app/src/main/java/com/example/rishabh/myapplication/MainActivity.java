package com.example.rishabh.myapplication;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void test(View view) {

        KeyguardManager myKM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        if (myKM.isKeyguardLocked()) {
            //it is locked
            Log.d(TAG, "Device is Locked");
        } else {
            //it is not locked
            Log.d(TAG, "Device is Un-Locked");
            AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            if (manager.isMusicActive()) {
                // Music is Playing
                Log.d(TAG, "Music is Playing");
                Toast.makeText(this, "Button Pressed ", Toast.LENGTH_SHORT).show();

            } else {
                // Music is Not-Playing
                Log.d(TAG, "Music is Not-Playing");
            }

        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    if (event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout()) {
                        //  long click action
                        Toast.makeText(this, "Pressed Up for a long time =) ", Toast.LENGTH_SHORT).show();

                        KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT);
                        mAudioManager.dispatchMediaKeyEvent(downEvent);

                        KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT);
                        mAudioManager.dispatchMediaKeyEvent(upEvent);
                    } else {

                        mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                    }
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_UP) {
                    if (event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout()) {
                        // long click action
                        Toast.makeText(this, "Pressed for a long time =) ", Toast.LENGTH_SHORT).show();

                        KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                        mAudioManager.dispatchMediaKeyEvent(downEvent);

                        KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                        mAudioManager.dispatchMediaKeyEvent(upEvent);
                    }else {

                        mAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}