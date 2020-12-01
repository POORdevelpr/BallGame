package com.campper.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import com.campper.coursework.controller.MediaPlayerController;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        startMediaPlayer();


        setupButtons();

        startGame();

        Log.d("onCreate","OnCreate");
    }

    @Override
    protected void onResume(){
        super.onResume();

        startMediaPlayer();
        Log.d("onREsume:","onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();

        pauseMediaPlayer();
        Log.d("onPause:","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
        Log.d("onStop:","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy:","onDestroy");


    }

    private void startGame(){
        buttonStart.setOnClickListener(v -> onStartButtonClick());
    }

    public void setupButtons(){
        buttonStart = findViewById(R.id.activity_main__btn_start);
    }

    private void onStartButtonClick(){
        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }

    public void startMediaPlayer(){
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.start();
    }

    public void releaseMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void pauseMediaPlayer(){
        if(mediaPlayer.isPlaying()) mediaPlayer.pause();

    }

}