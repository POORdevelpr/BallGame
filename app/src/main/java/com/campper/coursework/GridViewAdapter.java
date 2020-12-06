package com.campper.coursework;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.campper.coursework.model.Card;
import com.campper.coursework.model.Fruits;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class GridViewAdapter implements ListAdapter {
    private GridView gridView;
    private CountDownTimer countDownTimer;
    private int bestScore;

    private AtomicInteger atomicIntScore = new AtomicInteger();
    private AtomicInteger atomicIntLevel;
    private AtomicInteger atomicHardLevelRepeat = new AtomicInteger(0);
    private AtomicBoolean easy = new AtomicBoolean(false);
    private AtomicBoolean medium = new AtomicBoolean(false);
    private AtomicBoolean hard = new AtomicBoolean(false);


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

    public GridViewAdapter(Context context, ArrayList<Card> cardsArrayList,
                           TextView textScore, int bestScore, AtomicInteger atomicIntLevel, GridView gridView) {
        this.context = context;
        this.cardsArrayList = cardsArrayList;
        this.textScore = textScore;
        this.bestScore = bestScore;
        this.atomicIntLevel = atomicIntLevel;
        atomicIntScore.set(atomicIntLevel.get() * 2);
        this.gridView = gridView;
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
                        atomicIntLevel.incrementAndGet();

                        textScore.setText("Score: " + atomicIntScore.get());
                        animationClickSuccessCards(twoCardsImage.get(0), twoCardsImage.get(1));

                        twoCardsImage.removeAll(twoCardsImage);

                        atomicHardLevelRepeat.incrementAndGet();
                        // Hard level
                        if( (atomicIntLevel.get() == 10)){
                            Log.d("hard", "true");
                            hard.set(true);
                            easy.set(false);
                            medium.set(false);
                            atomicHardLevelRepeat.set(8);
                        }
                        repeatHardLevel(); // After case Hard level

                        // Medium level
                        if(atomicIntLevel.get() == 4){
                            easy.set(false);
                            hard.set(false);
                            medium.set(true);
                            resetView();
                        }


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

    private void repeatHardLevel() {
        if (atomicHardLevelRepeat.get() % 8 == 0) {
            atomicHardLevelRepeat.set(0);
            gridView.setAdapter(null);
            cardsArrayList.clear();
            atomicIntScore.get();

            hardLevel();
            gridView.setAdapter(new GridViewAdapter(context, cardsArrayList, textScore, bestScore, atomicIntLevel, gridView));
        }


    }

    public void resetView(){
        gridView.setAdapter(null);
        cardsArrayList.clear();
        atomicIntScore.get();
        // If hard level true else medium
        if(hard.get()) {
            hardLevel();
            gridView.setAdapter(new GridViewAdapter(context, cardsArrayList, textScore, bestScore, atomicIntLevel, gridView));
        } else if(medium.get()){
            mediumLevel();
            gridView.setAdapter(new GridViewAdapter(context, cardsArrayList, textScore, bestScore, atomicIntLevel, gridView));
        }
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


    public void easyLevel() {
        Fruits fruit;
        for (int i = 0; i < 4; i++) {
            fruit = randomEnumItem(Fruits.class);
            for (int j = 0; j < 2; j++) {
                cardsArrayList.add(new Card(fruit));
            }
        }

        Collections.shuffle(cardsArrayList);
    }

    public void mediumLevel() {
        Fruits fruit;
        for (int i = 0; i < 6; i++) {
            fruit = randomEnumItem(Fruits.class);
            for (int j = 0; j < 2; j++) {
                cardsArrayList.add(new Card(fruit));
            }
        }
        Collections.shuffle(cardsArrayList);
    }

    public void hardLevel() {
        Fruits fruit;
        for (int i = 0; i < 8; i++) {
            fruit = randomEnumItem(Fruits.class);
            for (int j = 0; j < 2; j++) {
                cardsArrayList.add(new Card(fruit));
            }
        }

        Collections.shuffle(cardsArrayList);
    }

    public static <T extends Enum<?>> T randomEnumItem(Class<T> tClass) {
        SecureRandom random = new SecureRandom();
        int x = random.nextInt(tClass.getEnumConstants().length);
        return tClass.getEnumConstants()[x];
    }
}
