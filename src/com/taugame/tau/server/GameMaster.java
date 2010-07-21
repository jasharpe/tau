package com.taugame.tau.server;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.taugame.tau.shared.Card;
import com.taugame.tau.shared.Game;

public class GameMaster {
    private final Map<String, Integer> scores;
    private final Deck deck;
    private final Set<Card> board;

    public GameMaster() {
        scores = new HashMap<String, Integer>();
        deck = new Deck();
        board = new LinkedHashSet<Card>();
    }

    public boolean join(String player) {
        if (scores.containsKey(player)) {
            return false;
        }
        else {
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
        int i = 0;
        for (Card card1 : board) {
            int j = 0;
            for (Card card2 : board) {
                if (j >= i) {if (board.contains(completeSet(card1, card2))) {return true;}}
                else {j++;}
            }
            i++;
        }
        return false;
    }

    private boolean removeSet(Card card1, Card card2, Card card3) {
        return false;
    }

}
