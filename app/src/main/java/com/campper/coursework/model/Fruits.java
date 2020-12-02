package com.campper.coursework.model;

import com.campper.coursework.R;

public enum Fruits {
    STRAWBERRY(R.drawable.card_front_strawberry),
    GRAPES(R.drawable.card_front_grapes),
    APPLE(R.drawable.card_front_apple),
    MANGO(R.drawable.card_front_mango),
    ORANGE(R.drawable.card_front_orange),
    LEMON(R.drawable.card_front_lemon),
    BERRY(R.drawable.card_front_berry),
    LIME(R.drawable.card_front_lime);

    private int imgResource;

    Fruits(int imgResource){
        this.imgResource = imgResource;
    }

    public int getImgResource(){
        return imgResource;
    }

}
