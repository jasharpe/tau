package com.taugame.tau.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.taugame.tau.shared.Card;

public class GameMaster {
    private static final Logger logger = Logger.getLogger("grizzly");
    private final GameListener listener;
    private final Map<String, Integer> scores;
    private final Deck deck;
    private final Board board;
    private boolean started;
    private boolean ended;
    private int ready;

    public GameMaster(GameListener listener) {
        this.listener = listener;
        scores = new HashMap<String, Integer>();
        deck = new Deck();
        board = new Board();
        deal();
        started = false;
        ended = false;
        ready = 0;
    }

    public void joinAs(String name) {
        if (!scores.containsKey(name)) {
            if (started) {
                scores.put(name, 0);
            } else {
                scores.put(name, -1);
                ready--;
            }
        }
        if (started) {
            sendUpdateEvent();
            if (ended) {
                sendEndEvent();
            }
        } else {
            sendStatusEvent();
        }
    }

    public void setReady(String name, boolean ready) {
        if (scores.containsKey(name)) {
            if (ready) {
                if (scores.get(name) == -1) {
                    scores.put(name, 0);
                    this.ready++;
                    if (this.ready == 0) {
                        started = true;
                        sendUpdateEvent();
                    } else {
                        sendStatusEvent();
                    }
                }
            } else {
                if (scores.get(name) == 0) {
                    scores.put(name, -1);
                    this.ready--;
                    sendStatusEvent();
                }
            }
        }
    }

    public void submit(String player, Card card1, Card card2, Card card3) {
        if (removeTau(card1, card2, card3)) {
            scores.put(player, scores.get(player) + 1);
            sendUpdateEvent();
            if (ended) {
                sendEndEvent();
            }
        }
    }

    public boolean isEnded() {
        return ended;
    }

    private void sendStatusEvent() {
        listener.statusChanged();
    }

    private void sendUpdateEvent() {
        listener.boardChanged(board);
    }

    private void sendEndEvent() {
        Set<Entry<String, Integer>> set = scores.entrySet();
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort(list, new Comparator<Entry<String, Integer>>(){

            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }

        });
        listener.gameEnded(list);
    }

    private void deal() {
        while (board.size() < 12 || !containsTau()) {
            if (deck.hasCard()) {
                board.add(deck.getCard());
                board.add(deck.getCard());
                board.add(deck.getCard());
            } else {
                ended = true;
                return;
            }
        }
    }

    private boolean containsTau() {
        for (int i = 0; i < board.size(); i++) {
            Card card1 = board.getCard(i);
            if (card1 != null) {
                for (int j = i + 1; j < board.size(); j++) {
                    Card card2 = board.getCard(j);
                    if (card2 != null) {
                        if (board.contains(completeTau(card1, card2))) {
                            return true;
                        }
                    }
                }
            }
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
        Integer index1 = board.getIndex(card1);
        if (index1 != null) {
            Integer index2 = board.getIndex(card2);
            if (index2 != null) {
                Integer index3 = board.getIndex(card3);
                if (index3 != null) {
                    if (Card.isTau(card1, card2, card3)) {
                        removeCards(index1, index2, index3);
                        deal();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void removeCards(int index1, int index2, int index3) {
        board.setNull(index1);
        board.setNull(index2);
        board.setNull(index3);
        if (!(board.size() > 12)) {
            if (deck.hasCard()) {
                index1 = getNextNull(-1);
                index2 = getNextNull(index1);
                index3 = getNextNull(index2);
                board.setCard(index1, deck.getCard());
                board.setCard(index2, deck.getCard());
                board.setCard(index3, deck.getCard());
            }
        } else {
            moveLastColumn();
        }
    }

    private int getNextNull(int index) {
        while (++index < board.size()) {
            if (board.getCard(index) == null) {
                return index;
            }
        }
        return -1;
    }

    private void moveLastColumn() {
        int from = board.size() - 3;
        int to = -1;
        for (int i = 0; i < 3; i++) {
            if (board.getCard(from) != null) {
                to = getNextNull(to);
                board.move(from, to);
            } else {
                board.remove(from);
            }
        }
    }

}
