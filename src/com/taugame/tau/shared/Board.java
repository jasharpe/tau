package com.taugame.tau.shared;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Set<Card> cards = new HashSet<Card>();

    public void add(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

}
