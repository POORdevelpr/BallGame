package com.campper.coursework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.util.Log;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.campper.coursework.model.Card;
import com.campper.coursework.model.Fruits;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameActivity extends Activity {
    private CountDownTimer countDownTimer;

    private static final String KEY_BEST_SCORE = "best_score";
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private int bestScore;
    private TextView textScore;

    private static final long START_DURATION_PROGRESS_BAR = 10_000; // Ten seconds
    private static final long ADDITIONAL_TIME_PROGRESS = 5000;
    private long currentTime;

    private ProgressBar progressBar;
    private AtomicInteger atomicIntLevel = new AtomicInteger(0);
    private AtomicBoolean easy = new AtomicBoolean(false);
    private AtomicBoolean medium = new AtomicBoolean(false);
    private AtomicBoolean hard = new AtomicBoolean(false);

    private MediaPlayer mediaPlayerBackgroundMusic;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<Card> cardBackList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);


        setupFields();
        setupSharedPref();
        setupEditPref();
        setupCardList();

        setupTimer();
        setupGridView();
        startMediaPlayerBackgroundMusic();

        if(atomicIntLevel.get() == 4){
            
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startMediaPlayerBackgroundMusic();
        Log.d("onREsume:", "onResume GameActivtiy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        pauseMediaPlayerBackgroundMusic();
        Log.d("onPause:", "onPause Game Activite");
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
        Log.d("onStop:", "onStop GameActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy:", "onDestroy Game Activity");
    }

    public void setupGridView() {
        gridView = findViewById(R.id.activity_game__grid_view);
        gridViewAdapter = new GridViewAdapter(this, cardBackList, textScore, bestScore, atomicIntLevel);
        gridView.setAdapter(gridViewAdapter);

    }


    public void setupFields() {
        cardBackList = new ArrayList<Card>();
        textScore = findViewById(R.id.activity_game__txt_score);
        progressBar = findViewById(R.id.activity_game__progress_bar);
        progressBar.setInterpolator(new LinearInterpolator());

    }

    private void setupCardList() {

        easyLevel();
        //mediumLevel();
        //hardLevel();
    }

    // levels -> Easy || Medium || Hard

    public void easyLevel() {
        Fruits fruit;
        for (int i = 0; i < 4; i++) {
            fruit = randomEnumItem(Fruits.class);
            for (int j = 0; j < 2; j++) {
                cardBackList.add(new Card(fruit));
            }
        }

        Collections.shuffle(cardBackList);
    }

    public void mediumLevel() {
        Fruits fruit;
        for (int i = 0; i < 6; i++) {
            fruit = randomEnumItem(Fruits.class);
            for (int j = 0; j < 2; j++) {
                cardBackList.add(new Card(fruit));
            }
        }
    }

    public void hardLevel() {
        Fruits fruit;
        for (int i = 0; i < 8; i++) {
            fruit = randomEnumItem(Fruits.class);
            for (int j = 0; j < 2; j++) {
                cardBackList.add(new Card(fruit));
            }
        }
    }

    public static <T extends Enum<?>> T randomEnumItem(Class<T> tClass) {
        SecureRandom random = new SecureRandom();
        int x = random.nextInt(tClass.getEnumConstants().length);
        return tClass.getEnumConstants()[x];
    }


    public void startMediaPlayerBackgroundMusic() {
        if (mediaPlayerBackgroundMusic == null) {
            mediaPlayerBackgroundMusic = MediaPlayer.create(this, R.raw.game_background);
            mediaPlayerBackgroundMusic.start();
            mediaPlayerBackgroundMusic.setLooping(true);
        } else {
            mediaPlayerBackgroundMusic.start();
        }
    }

    public void pauseMediaPlayerBackgroundMusic() {
        if (mediaPlayerBackgroundMusic != null) {
            mediaPlayerBackgroundMusic.pause();
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayerBackgroundMusic != null) {
            mediaPlayerBackgroundMusic.release();
            mediaPlayerBackgroundMusic = null;
        }
    }

    public void setMaxScore(int score) {
        editor.putInt(KEY_BEST_SCORE, score);
        editor.commit();
    }

    public int getMaxScore() {
        return sharedPreferences.getInt(KEY_BEST_SCORE, 0); // 0 default
    }

    public void setupSharedPref() {
        sharedPreferences = this
                .getSharedPreferences(KEY_BEST_SCORE, Context.MODE_PRIVATE);
    }

    public void setupEditPref() {
        editor = sharedPreferences.edit();
    }

    public void setupTimer() {
        int tick = 10;
        countDownTimer = new CountDownTimer(START_DURATION_PROGRESS_BAR, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressBar.getProgress() - 10, true);
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };

        countDownTimer.start();
    }

    public void gameOver() {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }
}
