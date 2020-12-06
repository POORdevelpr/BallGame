package com.campper.coursework;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.atomic.AtomicBoolean;

public class FragmentGame extends Fragment {
    private AtomicBoolean isMediaPlayerPlay;

    private Button btnEasyLevel;
    private Button btnMediumLevel;
    private Button btnHardLevel;
    private Button btnMuteMusic;
    private Button btnPlayMusic;

    private View anotherView;

    public FragmentGame(){}

    public FragmentGame(AtomicBoolean isMediaPlayerPlay){
        this.isMediaPlayerPlay = isMediaPlayerPlay;
    }

    public static FragmentGame newInstance(){
        return new FragmentGame();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game, container, false);

        anotherView = root;
        setupFragmentButtons(root);
        return root;
    }

    public View getAnotherView(){
        return anotherView;
    }

    private void setupFragmentButtons(View view) {
        btnEasyLevel = view.findViewById(R.id.fragment_game__btn_easy_lvl);
        btnMediumLevel = view.findViewById(R.id.fragment_game__btn_medium_lvl);
        btnHardLevel = view.findViewById(R.id.fragment_game__btn_hard_lvl);
        btnMuteMusic = view.findViewById(R.id.fragment_game__btn_mute);
        btnPlayMusic = view.findViewById(R.id.fragment_game__btn_music_play);

        setupFragmentButtonListeners();
    }

    private void setupFragmentButtonListeners() {
        btnMuteMusic.setOnClickListener(v -> onClickButtonMute());
        //btnPlayMusic.setOnClickListener(v -> onClickButtonPlayMusic());
        //btnEasyLevel.setOnClickListener(v -> onClickButtonEasy());
    }

//    private void onClickButtonEasy() {
//        setupCardList();
//        easyLevel();
//
//        setupGridView();
//
//        closeFragment();
//    }

    private void onClickButtonPlayMusic() {

    }

    private void onClickButtonMute() {
        Log.d("View",""+anotherView.findViewById(R.id.fragment_game__btn_mute));
        isMediaPlayerPlay.set(false);
    }
}
