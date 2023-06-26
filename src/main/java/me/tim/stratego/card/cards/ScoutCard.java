package me.tim.stratego.card.cards;

import me.tim.stratego.card.StrategoCard;

public class ScoutCard implements StrategoCard {
    @Override
    public int getPower() {
        return 2;
    }

    @Override
    public String getName() {
        return "Scout";
    }
}
