package com.campper.coursework;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.campper.coursework.model.Card;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class GridViewAdapter implements ListAdapter {
    private AtomicInteger atomicIntScore = new AtomicInteger(0);
    private TextView textScore;
    private SpringAnimation springAnimationWrongPrevious;
    private SpringAnimation springAnimationWrongCurrent;
    private SpringAnimation animationSuccessPrevious;
    private SpringAnimation animationSuccessCurrent;
    private SpringAnimation springAnimationClick;

    private Context context;

    private ArrayList<ImageView> twoCardsImage = new ArrayList<>(2);
    private MediaPlayer mediaPlayerSuccess;

    private ArrayList<Card> cardsArrayList;
    private ArrayList<Card> twoCardsList = new ArrayList<>(2);

    public GridViewAdapter(Context context, ArrayList<Card> cardsArrayList, TextView textScore) {
        this.context = context;
        this.cardsArrayList = cardsArrayList;
        this.textScore = textScore;
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

            animationCardClick(imageView);

            twoCardsList.add(cardsArrayList.get(position));
            twoCardsImage.add(imageView);

            // Check two cards
            if(twoCardsList.size() > 1) {
                // Check position if not same card
                if (twoCardsList.get(0).getPosition() != twoCardsList.get(1).getPosition()) {

                    // Check tag if success and Check visibility
                    if (twoCardsList.get(0).getImageTag()
                            .equals(twoCardsList.get(1).getImageTag()) && (twoCardsImage.get(0).getVisibility() != View.INVISIBLE
                            && twoCardsImage.get(1).getVisibility() != View.INVISIBLE)) {

                        startMediaPlayerSuccess();
                        atomicIntScore.addAndGet(2);
                        textScore.setText("Score: " + atomicIntScore.get());
                        animationClickSuccessCards(twoCardsImage.get(0), twoCardsImage.get(1));

                        twoCardsImage.removeAll(twoCardsImage);
                    } else {
                        // If cards are wrong
                        animationClickWrongCards(twoCardsImage.get(0), twoCardsImage.get(1));

                        twoCardsImage.removeAll(twoCardsImage);
                    }
                }
                // If double click on same card - release all data

                if(twoCardsList.get(0).getPosition() == twoCardsList.get(1).getPosition()){
                    imageView.setImageResource(R.drawable.ic_card_back);
                }

                twoCardsImage.removeAll(twoCardsImage);
                twoCardsList.removeAll(twoCardsList);

            }

        });

        return convertView;
    }

    public void animationCardClick(ImageView imageView){
         springAnimationClick = new SpringAnimation(imageView,
                DynamicAnimation.ROTATION_Y, 0)
                .setStartVelocity(1500);

        springAnimationClick.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        springAnimationClick.start();
    }

    public void animationClickSuccessCards(ImageView cardPrevious, ImageView cardCurrent){
        animationSuccessPrevious = new SpringAnimation(cardCurrent,
                DynamicAnimation.Y, 0)
                .setStartVelocity(1500);
        animationSuccessPrevious.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        animationSuccessPrevious.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                cardPrevious.setVisibility(View.INVISIBLE);
            }
        });

      animationSuccessCurrent = new SpringAnimation(cardCurrent,
              DynamicAnimation.Y, 0)
              .setStartVelocity(1500);
      animationSuccessCurrent.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
      animationSuccessCurrent.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
          @Override
          public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
              cardCurrent.setVisibility(View.INVISIBLE);
          }
      });

      springAnimationClick.cancel();
      animationSuccessPrevious.start();
      animationSuccessCurrent.start();

    }

    public void animationClickWrongCards(ImageView cardPrevious, ImageView cardCurrent) {

        springAnimationWrongPrevious = new SpringAnimation(cardPrevious,
                DynamicAnimation.ROTATION_Y, 0)
        .setStartVelocity(1500);
        springAnimationWrongPrevious.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

        springAnimationWrongPrevious.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                // To simultaneously image change, previous card should change faster
                animation.setStartVelocity(5500);
                cardPrevious.setImageResource(R.drawable.ic_card_back);
            }
        });

        springAnimationWrongCurrent = new SpringAnimation(cardCurrent,
                DynamicAnimation.ROTATION_Y, 0)
                .setStartVelocity(1500);
        springAnimationWrongCurrent.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

        springAnimationClick.cancel();  // Cancel Click animation

        springAnimationWrongCurrent.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                animation.setStartVelocity(4000);
                cardCurrent.setImageResource(R.drawable.ic_card_back);
            }
        });

        synchronized (this) {
            springAnimationWrongPrevious.start();
            springAnimationWrongCurrent.start();
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
