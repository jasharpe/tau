package com.taugame.tau.server;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.taugame.tau.shared.Card;
import com.taugame.tau.shared.Game;

public class GameMaster {
    private final Map<String, Integer> scores;
    private final Deck deck;
    private final LinkedHashSet<Card> board; // needs to be ordered and have fast contains method

    public GameMaster() {
        scores = new HashMap<String, Integer>();
        deck = new Deck();
        board = new LinkedHashSet<Card>();
    }

    public boolean join(String player) {
        if (scores.containsKey(player)) {
            return false;
        } else {
            scores.put(player, 0);
            return true;
        }
    }

    public Iterable<Card> submit(String player, Card card1, Card card2, Card card3) {
        if (removeSet(card1, card2, card3)) {
            scores.put(player, scores.get(player) + 1);
            return board;
        }
        return null;
    }

    private static Card completeSet(Card card1, Card card2) {
        return Deck.getCard(
                Game.TIMES_TWO_MOD_THREE[card1.get(0) + card2.get(0)],
                Game.TIMES_TWO_MOD_THREE[card1.get(1) + card2.get(1)],
                Game.TIMES_TWO_MOD_THREE[card1.get(2) + card2.get(2)],
                Game.TIMES_TWO_MOD_THREE[card1.get(3) + card2.get(3)]);
    }

    private void deal() {
        while (board.size() < 12 || !containsSet()) {
            if (deck.hasCard()) {
                board.add(deck.getCard());
                board.add(deck.getCard());
                board.add(deck.getCard());
            } else {
                return;
            }
        }
    }

    private boolean containsSet() {
        int i = 0;
        for (Card card1 : board) {
            int j = 0;
            for (Card card2 : board) {
                if (j > i) {
                    if (board.contains(completeSet(card1, card2))) {return true;}
                } else {j++;}
            }
            i++;
        }
        return false;
    }

    private boolean removeSet(Card card1, Card card2, Card card3) {
        if (board.contains(card1) && board.contains(card2) && board.contains(card3) &&
                Game.isSet(card1, card2, card3)) {
            board.remove(card1);
            board.remove(card2);
            board.remove(card3);
            deal();
            return true;
        } else {
            return false;
        }
    }

}
