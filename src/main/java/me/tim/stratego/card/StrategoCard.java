package me.tim.stratego.card;

import me.tim.stratego.card.cards.BombCard;

public interface StrategoCard {
    int getPower();
    String getName();
    default FightResult canBeat(StrategoCard otherCard) {
        if (otherCard instanceof BombCard) {
            return FightResult.LOST;
        }
        if (this.getPower() == otherCard.getPower()) {
            return FightResult.DRAW;
        }
        if (this.getPower() > otherCard.getPower()) {
            return FightResult.WON;
        } else {
            return FightResult.LOST;
        }
    }
}
