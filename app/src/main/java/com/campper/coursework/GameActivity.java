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

import com.campper.coursework.model.Card;
import com.campper.coursework.model.Fruits;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameActivity extends Activity {
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

        startMediaPlayerBackgroundMusic();

        setupGridView();
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
        gridViewAdapter = new GridViewAdapter(this, cardBackList);
        gridView.setAdapter(gridViewAdapter);

    }

    public void setupFields(){
        cardBackList = new ArrayList<Card>();
    }

    private void setupCardList(){
        cardBackList.add(new Card(Fruits.APPLE,"apple"));
        cardBackList.add(new Card(Fruits.APPLE, "apple"));
        cardBackList.add(new Card(Fruits.LIME, "lime"));
        cardBackList.add(new Card(Fruits.LEMON, "lemon"));
        cardBackList.add(new Card(Fruits.STRAWBERRY, "strawberry"));
        cardBackList.add(new Card(Fruits.ORANGE, "orange"));


        //cardBackList.add(new Card(R.drawable.ic_card_back, Fruits.MANGO, "mango"));
    }

    // levels -> Easy || Medium || Hard

    public void easyLevel(){

        for(int i=0; i<6; i++){
            //cardBackList.add(new Card(R.drawable.ic_card_back), )
        }
    }

    public void mediumLevel(){
        for(int i=0; i<9; i++){

        }
    }

    public void hardLevel(){
        for(int i=0; i<12; i++){

        }
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
