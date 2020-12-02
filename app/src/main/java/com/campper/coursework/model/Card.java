package com.campper.coursework.model;

import com.campper.coursework.R;

public class Card {
    private Fruits fruit;

    private int cardBackImageId;



    public Card(int backImageResource, Fruits fruit) {
        this.cardBackImageId = backImageResource;
        this.fruit = fruit;
    }

    public int getCardBackImageId() {
        return cardBackImageId;
    }

    public int getCardFrontImageId() {
        return fruit.getImgResource();
    }
}
