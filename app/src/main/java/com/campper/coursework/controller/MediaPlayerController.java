package com.campper.coursework.controller;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.campper.coursework.R;

import java.io.FileDescriptor;

public class MediaPlayerController extends MediaPlayer implements MediaPlayer.OnPreparedListener{
    private Resources resources;
    private MediaPlayer mediaPlayer = null;
    Context context;



    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void startPlaying(){
        mediaPlayer = MediaPlayer.create(context, R.raw.background);

    }
}
