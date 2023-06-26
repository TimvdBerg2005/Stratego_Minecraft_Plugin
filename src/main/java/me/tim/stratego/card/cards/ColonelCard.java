package me.tim.stratego.card.cards;

import me.tim.stratego.card.StrategoCard;

public class ColonelCard implements StrategoCard {
    @Override
    public int getPower() {
        return 7;
    }

    @Override
    public String getName() {
        return "Colonel";
    }
}
