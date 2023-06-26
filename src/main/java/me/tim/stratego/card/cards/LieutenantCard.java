package me.tim.stratego.card.cards;

import me.tim.stratego.card.StrategoCard;

public class LieutenantCard implements StrategoCard {
    @Override
    public int getPower() {
        return 5;
    }

    @Override
    public String getName() {
        return "Lieutenant";
    }
}
