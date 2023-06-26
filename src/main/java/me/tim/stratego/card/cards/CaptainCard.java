package me.tim.stratego.card.cards;

import me.tim.stratego.card.StrategoCard;

public class CaptainCard implements StrategoCard {
    @Override
    public int getPower() {
        return 6;
    }

    @Override
    public String getName() {
        return "Captain";
    }
}
