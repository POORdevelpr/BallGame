package com.campper.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    private Button btnMenu;
    private Button btnRetry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        setupButtons();
        buttonListeners();
    }

    public void setupButtons(){
        btnMenu = findViewById(R.id.activity_game_over__btn_menu);
        btnRetry = findViewById(R.id.activity_game_over__btn_retry);
    };

    public void buttonListeners(){
        btnMenu.setOnClickListener(v -> onClickMenuBtn());
        btnRetry.setOnClickListener(v -> onClickRetryBtn());
    }

    private void onClickRetryBtn() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void onClickMenuBtn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
