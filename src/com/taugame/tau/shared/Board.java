package com.taugame.tau.shared;

import java.util.LinkedHashSet;
import java.util.Set;

public class Board {
    private final Set<Card> cards = new LinkedHashSet<Card>();

    public void add(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

}
