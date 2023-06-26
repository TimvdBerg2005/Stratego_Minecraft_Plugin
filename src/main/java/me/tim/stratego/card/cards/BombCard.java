package me.tim.stratego.card.cards;

import me.tim.stratego.card.FightResult;
import me.tim.stratego.card.StrategoCard;

public class BombCard implements StrategoCard {

    @Override
    public int getPower() {
        return 0;
    }

    @Override
    public String getName() {
        return "Bomb";
    }
}
