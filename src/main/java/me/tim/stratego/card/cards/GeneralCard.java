package me.tim.stratego.card.cards;

import me.tim.stratego.card.FightResult;
import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.card.cards.BombCard;

public class GeneralCard implements StrategoCard {
    @Override
    public int getPower() {
        return 9;
    }

    @Override
    public String getName() {
        return "General";
    }
}
