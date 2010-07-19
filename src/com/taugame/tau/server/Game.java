package com.taugame.tau.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.taugame.tau.shared.Board;
import com.taugame.tau.shared.Card;

public class Game {
    private static final int[] TIMES_TWO_MOD_THREE = new int[] {0, 2, 1, 0, 2};
    private final ConcurrentMap<String, AtomicInteger> scores;
    private final Deck deck;
    private final Board board;

    public Game() {
        scores = new ConcurrentHashMap<String, AtomicInteger>();
        deck = new Deck();
        board = new Board();
    }

    public boolean join(String player) {
        return (scores.putIfAbsent(player, new AtomicInteger(0)) == null);
    }

    public Board start() {
        return board;
    }

    public Board submit(String player, Card card1, Card card2, Card card3) {
        if (removeSet(card1, card2, card3)) {
            scores.get(player).incrementAndGet();
            return board;
        }
        return null;
    }

    private static boolean isSet(Card card1, Card card2, Card card3) {
        for (int property = 0; property < 4; property++) {
            int sum = card1.get(property) + card2.get(property);
            if (card3.get(property) != TIMES_TWO_MOD_THREE[sum]) return false;
        }
        return true;
    }

    private static Card completeSet(Card card1, Card card2) {
        return null;
    }

    private void deal() {
        while (!containsSet() || board.size() < 12) {
            if (deck.hasCard()) {
                board.add(deck.getCard());
                board.add(deck.getCard());
                board.add(deck.getCard());
            }
            else {
                return;
            }
        }
    }

    private boolean containsSet() {
        return true;
    }

    private boolean removeSet(Card card1, Card card2, Card card3) {
        return false;
    }

}
