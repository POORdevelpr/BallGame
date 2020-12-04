package com.campper.coursework.model;

import android.widget.ImageView;

public class Card {
    private int position;
    private String imageTag;
    private Fruits fruit;


    public Card(Fruits fruit) {
        this.fruit = fruit;
        imageTag = fruit.getFruitTag();
    }


    public int getCardFrontImageId() {
        return fruit.getImgResource();
    }

    public String getImageTag(){
        return imageTag;
    }

    public void setImageTag(String imageTag){
        this.imageTag = imageTag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
