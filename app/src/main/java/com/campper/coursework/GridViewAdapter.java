package com.campper.coursework;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.campper.coursework.model.Card;

import java.util.ArrayList;
import java.util.HashMap;



public class GridViewAdapter implements ListAdapter {
    private Context context;

    private ArrayList<ImageView> twoCardsImage = new ArrayList<>(2);
    private MediaPlayer mediaPlayerSuccess;

    private ArrayList<Card> cardsArrayList;
    private ArrayList<Card> twoCardsList = new ArrayList<>(2);

    public GridViewAdapter(Context context, ArrayList<Card> cardsArrayList) {
        this.context = context;
        this.cardsArrayList = cardsArrayList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return cardsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardsArrayList.get(position).getCardFrontImageId();
    }

    @Override
    public long getItemId(int position) {
        return cardsArrayList.get(position).getCardFrontImageId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item,
                    parent, false);

        }

        // Set image, position and tag
        cardsArrayList.get(position).setPosition(position);

        final ImageView imageView = convertView.findViewById(R.id.grid_view_item_img);

        convertView.setOnClickListener((View v) -> {

            imageView.setImageResource(cardsArrayList.get(position).getCardFrontImageId());


            twoCardsList.add(cardsArrayList.get(position));
            twoCardsImage.add(imageView);

            // Check two cards
            if(twoCardsList.size() > 1){
                // Check position if not same card
                if(twoCardsList.get(0).getPosition() != twoCardsList.get(1).getPosition()){

                    // Check tag if success
                    if ( twoCardsList.get(0).getImageTag()
                            .equals(twoCardsList.get(1).getImageTag()) ){
                        startMediaPlayerSuccess();

                        // Animation if success
                        //twoCardsImage.get(0).setAnimation();

                    }

                }

                twoCardsList.removeAll(twoCardsList);
            }

        });

        return convertView;
    }

    public void checkTwoCards() {
        if(twoCardsList.size() > 1){
            // Check position if not same card
            if(twoCardsList.get(0).getPosition() != twoCardsList.get(1).getPosition()){

                // Check tag if success
                if ( twoCardsList.get(0).getImageTag()
                .equals(twoCardsList.get(1).getImageTag()) ){
                    startMediaPlayerSuccess();

                }

            }

            twoCardsList.removeAll(twoCardsList);
        }
    }


    public void startMediaPlayerSuccess() {
        if (mediaPlayerSuccess == null) {
            mediaPlayerSuccess = MediaPlayer.create(context, R.raw.succesess);
            mediaPlayerSuccess.start();
        }
        mediaPlayerSuccess.start();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return cardsArrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return cardsArrayList.isEmpty();
    }

}
