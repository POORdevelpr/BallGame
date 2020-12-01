package com.campper.coursework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.campper.coursework.model.Card;

import java.util.ArrayList;

public class GameActivity extends Activity {
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

        setupGridView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void setupGridView(){
        gridView = findViewById(R.id.activity_game__grid_view);
        gridViewAdapter = new GridViewAdapter(this, cardBackList);
        gridView.setAdapter(gridViewAdapter);

    }

    public void setupFields(){
        cardBackList = new ArrayList<Card>();
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.STRAWBERRY));
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.APPLE));
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.BERRY));
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.LEMON));
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.ORANGE));
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.MANGO));
        cardBackList.add(new Card(R.drawable.ic_card_back, Card.GRAPES));
    };

}
