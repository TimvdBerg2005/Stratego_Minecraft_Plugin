package me.tim.stratego.card.cards;

import me.tim.stratego.card.FightResult;
import me.tim.stratego.card.StrategoCard;

public class MarshalCard implements StrategoCard {
    @Override
    public int getPower() {
        return 10;
    }

    @Override
    public String getName() {
        return "Marshal";
    }

    @Override
    public FightResult canBeat(StrategoCard otherCard) {
        if (otherCard instanceof SpyCard) {
            return FightResult.LOST;
        }
        return StrategoCard.super.canBeat(otherCard);
    }
}
