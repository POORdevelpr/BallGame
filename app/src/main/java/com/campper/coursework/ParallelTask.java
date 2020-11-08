package com.campper.coursework;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

public class ParallelTask extends AsyncTask<Button, Integer, Void> {
    private Button button;
    private Context context;

    public ParallelTask(Context context){
        this.context = context;
    };

    @Override
    protected Void doInBackground(Button... buttons) {
        button = buttons[0];

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.key22);

        button.setOnClickListener( (View v) ->{
            mediaPlayer.start();
        });
        return null;
    }
}
