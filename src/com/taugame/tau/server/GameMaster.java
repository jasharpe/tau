package com.taugame.tau.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.taugame.tau.shared.Card;

public class GameMaster {
    private static final Logger logger = Logger.getLogger("grizzly");
    private final GameListener listener;
    private final Map<String, Player> players;
    private final Deck deck;
    private final Board board;
    private boolean started;
    private boolean ended;
    private int ready;
    private int unready;

    public GameMaster(GameListener listener) {
        this.listener = listener;
        players = new HashMap<String, Player>();
        deck = new Deck();
        board = new Board();
        deal();
        started = false;
        ended = false;
        ready = 0;
        unready = 0;
    }

    public void joinAs(String name) {
        if (!players.containsKey(name)) {
            Player player = new Player();
            players.put(name, player);
            if (started) {
                player.isReady = true;
            } else {
                unready++;
            }
        }
        // Someone already in the game could leave and come back and call joinAs.
        // They would need to receive updates.
        if (started) {
            sendUpdateEvent();
            if (ended) {
                sendEndEvent();
            }
        } else {
            sendStatusEvent();
        }
    }

    public void setReady(String name, boolean isReady) {
        Player player = players.get(name);
        if (player != null) {
            if (isReady && !player.isReady) {
                player.isReady = true;
                ready++;
                unready--;
                sendStatusEvent();
                startIfReady();
            } else if (!isReady && player.isReady) {
                player.isReady = false;
                ready--;
                unready++;
                sendStatusEvent();
            }
        }
    }

    public void submit(String name, Card card1, Card card2, Card card3) {
        if (removeTau(card1, card2, card3)) {
            Player player = players.get(name);
            player.score++;
            sendUpdateEvent();
            if (ended) {
                sendEndEvent();
            }
        }
    }

    public void leave(String name) {
        Player player = players.get(name);
        if (!started && player != null) {
            if (player.isReady) {
                player.isReady = false;
                ready--;
                sendStatusEvent();
            } else {
                unready--;
                sendStatusEvent();
                startIfReady();
            }
        }
    }

    public void reenter(String name) {
        Player player = players.get(name);
        if (!started && player != null) {
            if (player.isReady) {
                player.isReady = false;
            }
            unready++;
            sendStatusEvent();
        }
    }

    public boolean isEnded() {
        return ended;
    }

    private void startIfReady() {
        if (ready > 0 && unready == 0) {
            started = true;
            sendUpdateEvent();
        }
    }

    private void sendStatusEvent() {
        listener.statusChanged();
    }

    private void sendUpdateEvent() {
        listener.boardChanged(board);
    }

    private void sendEndEvent() {
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>();
        for (Entry<String, Player> playerEntry : players.entrySet()) {
            list.add(new SimpleEntry<String, Integer>(playerEntry.getKey(), playerEntry.getValue().score));
        }
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
