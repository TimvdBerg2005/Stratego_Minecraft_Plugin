package me.tim.stratego.card.cards;

import me.tim.stratego.card.FightResult;
import me.tim.stratego.card.StrategoCard;

public class SpyCard implements StrategoCard {
    @Override
    public int getPower() {
        return 1;
    }

    @Override
    public String getName() {
        return "Spy";
    }

    @Override
    public FightResult canBeat(StrategoCard otherCard) {
        if (otherCard instanceof MarshalCard) {
            return FightResult.WON;
        }
        return StrategoCard.super.canBeat(otherCard);
    }
}
