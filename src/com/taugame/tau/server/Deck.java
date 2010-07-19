package com.taugame.tau.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.taugame.tau.shared.Card;

public final class Deck {
    private static final Card[] CARDS;
    private final Iterator<Card> deck;

    static {
        List<Card> cardList = new ArrayList<Card>();
        for (int colour = 0; colour < 3; colour++) {
            for (int number = 0; number < 3; number++) {
                for (int fill = 0; fill < 3; fill++) {
                    for (int shape = 0; shape < 3; shape++) {
                        cardList.add(new Card(colour, number, fill, shape));
                    }
                }
            }
        }
        CARDS = new Card[cardList.size()];
        cardList.toArray(CARDS);
    }

    public Deck() {
        Card[] copyDeck = Arrays.copyOf(CARDS, CARDS.length);
        List<Card> shuffle = Arrays.asList(copyDeck);
        Collections.shuffle(shuffle);
        deck = shuffle.iterator();
    }

    public Card getCard() {
        return deck.hasNext() ? deck.next() : null;
    }

    public boolean hasCard() {
        return deck.hasNext();
    }

    public static Card getCard(int colour, int number, int fill, int shape) {
        return CARDS[getIndex(colour, number, fill, shape)];
    }

    private static int getIndex(int colour, int number, int fill, int shape) {
        return (0
                + colour * 3^0
                + number * 3^1
                +   fill * 3^2
                +  shape * 3^3);
    }

}
