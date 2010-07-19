
package com.taugame.tau.shared;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Deck deck;
    private final Set<Card> cards;

    public Board(Deck deck) {
        this.deck = deck;
        this.cards = new HashSet<Card>();
        for (int i = 0; i < 12; i++) {
            cards.add(deck.getCard());
        }
        deal();
    }

    private void deal() {
        while (!containsSet() || cards.size() < 12) {
            if (deck.hasCard()) {
                cards.add(deck.getCard());
                cards.add(deck.getCard());
                cards.add(deck.getCard());
            }
            else {
                return;
            }
        }
    }

    private boolean containsSet() {
        return true;
    }

    public boolean removeSet(Card card1, Card card2, Card card3) {
        return false;
    }

}
