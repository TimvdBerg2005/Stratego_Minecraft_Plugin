package me.tim.stratego.card.cards;

import me.tim.stratego.card.StrategoCard;

public class MajorCard implements StrategoCard {
    @Override
    public int getPower() {
        return 8;
    }

    @Override
    public String getName() {
        return "Major";
    }
}
