package com.taugame.tau.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.taugame.tau.shared.Card;

public final class Deck {
    private static final List<Card> cards;
    private final Iterator<Card> deck;

    static {
        cards = new ArrayList<Card>();
        for (int colour = 0; colour < 3; colour++) {
            for (int number = 0; number < 3; number++) {
                for (int fill = 0; fill < 3; fill++) {
                    for (int shape = 0; shape < 3; shape++) {
                        cards.add(new Card(colour, number, fill, shape));
                    }
                }
            }
        }
    }

    public Deck() {
        List<Card> shuffle = new ArrayList<Card>(cards);
        Collections.shuffle(shuffle);
        deck = shuffle.iterator();
    }

    public Card getCard() {
        return deck.hasNext() ? deck.next() : null;
    }

    public boolean hasCard() {
        return deck.hasNext();
    }

}
