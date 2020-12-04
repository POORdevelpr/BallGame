package com.campper.coursework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.campper.coursework.model.Card;
import com.campper.coursework.model.Fruits;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameActivity extends Activity {
    private TextView textScore;
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
        setupCardList();

        setupGridView();
        startMediaPlayerBackgroundMusic();

    }

    @Override
    protected void onResume() {
        super.onResume();

        startMediaPlayerBackgroundMusic();
        Log.d("onREsume:","onResume GameActivtiy");
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
        Log.d("onStop:","onStop GameActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy:","onDestroy Game Activity");
    }

    public void setupGridView(){
        gridView = findViewById(R.id.activity_game__grid_view);
        gridViewAdapter = new GridViewAdapter(this, cardBackList, textScore);
        gridView.setAdapter(gridViewAdapter);

    }

    public void setupFields(){
        cardBackList = new ArrayList<Card>();
        textScore = findViewById(R.id.activity_game__txt_score);
    }

    private void setupCardList(){

        //easyLevel();
        mediumLevel();
        //hardLevel();
    }

    // levels -> Easy || Medium || Hard

    public void easyLevel(){
        Fruits fruit;
        for(int i=0; i<4; i++){
            fruit = randomEnumItem(Fruits.class);
            for(int j=0; j<2; j++) {
                cardBackList.add(new Card(fruit));
            }
        }
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

    public void hardLevel(){
        Fruits fruit;
        for(int i=0; i<8; i++){
            fruit = randomEnumItem(Fruits.class);
            for(int j=0; j<2; j++){
                cardBackList.add(new Card(fruit));
            }
        }
    }

    public static <T extends Enum<?>> T randomEnumItem(Class<T> tClass){
        SecureRandom random = new SecureRandom();
        int x = random.nextInt(tClass.getEnumConstants().length);
        return tClass.getEnumConstants()[x];
    }



    public void startMediaPlayerBackgroundMusic(){
        if(mediaPlayerBackgroundMusic == null) {
            mediaPlayerBackgroundMusic = MediaPlayer.create(this, R.raw.game_background);
            mediaPlayerBackgroundMusic.start();
            mediaPlayerBackgroundMusic.setLooping(true);
        } else {
            mediaPlayerBackgroundMusic.start();
        }
    }

    public void pauseMediaPlayerBackgroundMusic(){
        if(mediaPlayerBackgroundMusic != null) {
            mediaPlayerBackgroundMusic.pause();
        }
    }

    public void releaseMediaPlayer(){
        if(mediaPlayerBackgroundMusic != null){
            mediaPlayerBackgroundMusic.release();
            mediaPlayerBackgroundMusic = null;
        }
    }
}
