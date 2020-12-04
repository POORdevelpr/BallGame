package com.campper.coursework.model;

import com.campper.coursework.R;

public enum Fruits {
    STRAWBERRY(R.drawable.card_front_strawberry, "strawberry"),
    GRAPES(R.drawable.card_front_grapes, "grapes"),
    APPLE(R.drawable.card_front_apple, "apple"),
    MANGO(R.drawable.card_front_mango, "mango"),
    ORANGE(R.drawable.card_front_orange, "orange"),
    LEMON(R.drawable.card_front_lemon, "lemon"),
    BERRY(R.drawable.card_front_berry, "berry"),
    LIME(R.drawable.card_front_lime, "lime");

    private int imgResource;
    private String fruitTag;

    Fruits(int imgResource, String tag){
        this.imgResource = imgResource;
        this.fruitTag = tag;
    }

    public int getImgResource(){
        return imgResource;
    }

    public static Fruits getRandomFruit(){
        return values()[(int)Math.random() * values().length];
    }

    public String getFruitTag() {
        return fruitTag;
    }
}
