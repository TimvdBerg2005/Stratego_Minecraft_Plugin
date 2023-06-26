package me.tim.stratego.card.cards;

import me.tim.stratego.card.StrategoCard;

public class SergeantCard implements StrategoCard {
    @Override
    public int getPower() {
        return 4;
    }

    @Override
    public String getName() {
        return "Sergeant";
    }
}
