package com.kodilla;

import javafx.scene.Parent;

public class Card extends Parent {

    private Suit SuitValue;
    private Rank rankValue;
    private Image cardImage;

    public Card(Suit suitValue, Rank rankValue, Image cardImage) {
        SuitValue = suitValue;
        this.rankValue = rankValue;
        this.cardImage = cardImage;
    }
}
