package com.taugame.tau.server;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.logging.Logger;

import com.taugame.tau.shared.Card;

public class GameMaster {
    private static final Logger logger = Logger.getLogger("grizzly");
    private final Map<String, Integer> scores;
    private final Deck deck;
    private final LinkedHashSet<Card> board; // needs to be ordered and have fast contains method
    private int ready;

    public GameMaster() {
        scores = new HashMap<String, Integer>();
        deck = new Deck();
        board = new LinkedHashSet<Card>();
        ready = 0;
        deal();
    }

    public Iterable<Card> getBoard() {
        return board;
    }

    public boolean joinAs(String name) {
//        if (scores.containsKey(name)) {
//            return false;
//        } else {
            scores.put(name, 0);
//            ready--;
            return true;
//        }
    }

    public void setReady(String name, boolean ready) {
        if (ready) {
            this.ready++;
            if (this.ready == 0) {deal();}
        } else {
            this.ready--;
        }
    }

    public Iterable<Card> submit(String player, Card card1, Card card2, Card card3) {
        if (removeTau(card1, card2, card3)) {
            scores.put(player, scores.get(player) + 1);
            return board;
        }
        return null;
    }

    private void deal() {
        while (board.size() < 12 || !containsTau()) {
            if (deck.hasCard()) {
                board.add(deck.getCard());
                board.add(deck.getCard());
                board.add(deck.getCard());
            } else {
                break;
            }
        }
    }

    private boolean containsTau() {
        int i = 0;
        for (Card card1 : board) {
            int j = 0;
            for (Card card2 : board) {
                if (j > i) {
                    if (board.contains(completeTau(card1, card2))) {
                        logger.info("ContainsTau: " + card1 + ", " + card2 + ", " +
                                completeTau(card1, card2));
                        return true;
                    }
                } else {j++;}
            }
            i++;
        }
        return false;
    }

    private static Card completeTau(Card card1, Card card2) {
        return new Card(
                Card.TIMES_TWO_MOD_THREE[card1.get(0) + card2.get(0)],
                Card.TIMES_TWO_MOD_THREE[card1.get(1) + card2.get(1)],
                Card.TIMES_TWO_MOD_THREE[card1.get(2) + card2.get(2)],
                Card.TIMES_TWO_MOD_THREE[card1.get(3) + card2.get(3)]);
    }

    private boolean removeTau(Card card1, Card card2, Card card3) {
        if (board.contains(card1) && board.contains(card2) && board.contains(card3) &&
                Card.isTau(card1, card2, card3)) {
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
