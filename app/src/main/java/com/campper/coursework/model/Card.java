package com.campper.coursework.model;

import com.campper.coursework.R;

public class Card {
    public static final int STRAWBERRY = R.drawable.card_front_strawberry;
    public static final int GRAPES = R.drawable.card_front_grapes;
    public static final int APPLE = R.drawable.card_front_apple;
    public static final int MANGO = R.drawable.card_front_mango;
    public static final int ORANGE = R.drawable.card_front_orange;
    public static final int LEMON = R.drawable.card_front_lemon;
    public static final int BERRY = R.drawable.card_front_berry;
    public static final int LIME = R.drawable.card_front_lime;

    private int resId;

    private int cardFrontImageId; //

    public Card(int resourceId, int cardFrontImageId) {
        this.cardFrontImageId = cardFrontImageId;
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public int getCardFrontImageId() {
        return cardFrontImageId;
    }
}
