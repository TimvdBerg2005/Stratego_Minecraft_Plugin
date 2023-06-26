package me.tim.stratego.card.cards;

import me.tim.stratego.card.FightResult;
import me.tim.stratego.card.StrategoCard;

public class MinerCard implements StrategoCard {
    @Override
    public int getPower() {
        return 3;
    }

    @Override
    public String getName() {
        return "Miner";
    }

    @Override
    public FightResult canBeat(StrategoCard otherCard) {
        if (otherCard instanceof BombCard) {
            return FightResult.WON;
        }
        return StrategoCard.super.canBeat(otherCard);
    }

}
