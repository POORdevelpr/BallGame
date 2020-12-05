package com.campper.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean isFragmentDisplayed = false;
    private static final String FRAGMENT_STATE = "fragment_state";

    private volatile boolean isBackgroundMusicMute;

    private MediaPlayer mediaPlayerBackgroundMusic;
    private MediaPlayer mediaPlayerClick;

    private Button buttonStart;
    private Button buttonMusicOff;
    private Button buttonMusicOn;
    private Button buttonMenu;
    private Button buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        startMediaPlayerBackgroundMusic();


        setupButtons();

        // CheckInstance
        if(savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean(FRAGMENT_STATE);
        }
        setupButtonListeners();

        Log.d("onCreate","OnCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!isBackgroundMusicMute) {
            startMediaPlayerBackgroundMusic();
        }

        Log.d("onREsume:", "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();

        pauseMediaPlayerBackgroundMusic();
        pauseMediaPlayerClick();
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

    public void setupButtons(){
        buttonStart = findViewById(R.id.activity_main__btn_start);
        buttonMusicOff = findViewById(R.id.activity_main__btn_music_off);
        buttonMusicOn = findViewById(R.id.activity_main__btn_music_on);
        buttonMenu = findViewById(R.id.activity_main__btn_menu);
        buttonAbout = findViewById(R.id.activity_main__btn_about);
    }

    private void setupButtonListeners(){
        buttonStart.setOnClickListener(v -> onStartButtonClick());
        buttonMusicOff.setOnClickListener(v -> onButtonMuteClick());
        buttonMusicOn.setOnClickListener(v -> onButtonMusicOnClick());
        buttonMenu.setOnClickListener(v -> onButtonMenuClick());
        buttonAbout.setOnClickListener(v -> onButtonAboutClick());
    }

    private void onButtonAboutClick() {
        startMediaPlayerClick();
        if (!isFragmentDisplayed){
           displayFragment();
        } else {
            closeFragment();
        }
    }

    private void displayFragment() {
        FragmentAbout fragment = FragmentAbout.newInstance();
        // Get manager for current activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Commit -> start Schedule on Main thread
        fragmentTransaction.add(R.id.activity_main__fragment_container, fragment).addToBackStack(FRAGMENT_STATE).commit();
        buttonAbout.setText(R.string.btn_close);

        isFragmentDisplayed = true;
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentAbout fragmentAbout = (FragmentAbout)
                fragmentManager.findFragmentById(R.id.activity_main__fragment_container);

        if(fragmentAbout != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragmentAbout).commit();
        }
        buttonAbout.setText(R.string.btn_open);
        isFragmentDisplayed = false;
    }

    private void onStartButtonClick(){
        startMediaPlayerClick();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void onButtonMuteClick(){
        startMediaPlayerClick();
        isBackgroundMusicMute = true;
        pauseMediaPlayerBackgroundMusic();
    }

    private void onButtonMusicOnClick() {
        startMediaPlayerClick();
        isBackgroundMusicMute = false;
        startMediaPlayerBackgroundMusic();
    }

    private void onButtonMenuClick() {
        startMediaPlayerClick();
    }

    public void startMediaPlayerBackgroundMusic(){
        if(mediaPlayerBackgroundMusic == null) {
            mediaPlayerBackgroundMusic = MediaPlayer.create(this, R.raw.background);

            mediaPlayerBackgroundMusic.start();
            mediaPlayerBackgroundMusic.setLooping(true);
        } else {
            mediaPlayerBackgroundMusic.start();
        }
    }

    public void startMediaPlayerClick(){
        if(mediaPlayerClick == null){
            mediaPlayerClick = MediaPlayer.create(this, R.raw.key22);
            mediaPlayerClick.start();
        } else {
            mediaPlayerClick.start();
        }
    }

    public void pauseMediaPlayerClick(){
        if(mediaPlayerClick != null){
            mediaPlayerClick.pause();
        }
    }

    public void releaseMediaPlayer(){
        if(mediaPlayerBackgroundMusic != null){
            mediaPlayerBackgroundMusic.release();
            mediaPlayerBackgroundMusic = null;
        }
        if(mediaPlayerClick != null){
            mediaPlayerClick.release();
            mediaPlayerClick = null;
        }
    }

    public void pauseMediaPlayerBackgroundMusic(){
        if(mediaPlayerBackgroundMusic != null) {
            mediaPlayerBackgroundMusic.pause();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // true = open, false = closed
        outPersistentState.putBoolean(FRAGMENT_STATE, isFragmentDisplayed);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}