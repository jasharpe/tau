package com.taugame.tau.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import com.taugame.tau.shared.Card;

public class Board implements Iterable<Card> {
    private static final Logger logger = Logger.getLogger("grizzly");
    private final ArrayList<Card> board;
    private final HashMap<Card, Integer> cardToIndex;

    public Board() {
        board = new ArrayList<Card>(21);
        cardToIndex = new HashMap<Card, Integer>(21);
    }

    public Map<Card, Integer> getIndex() {
        return cardToIndex;
    }

    public int size() {
        return board.size();
    }

    @Override
    public Iterator<Card> iterator() {
        return board.iterator();
    }

    public Card getCard(int index) {
        return board.get(index);
    }

    public void setCard(int index, Card card) {
        board.set(index, card);
        cardToIndex.put(card, index);
    }

    public Integer getIndex(Card card) {
        return cardToIndex.get(card);
    }

    public void move(int from, int to) {
        Card card = board.remove(from);
        board.set(to, card);
        cardToIndex.put(card, to);
    }

    public boolean contains(Card card) {
        return getIndex(card) != null;
    }

    public void add(Card card) {
        int index = board.size();
        board.add(card);
        cardToIndex.put(card, index);
    }

    public void remove(int index) {
        cardToIndex.remove(board.get(index));
        board.remove(index);
    }

    public void setNull(int index) {
        cardToIndex.remove(board.get(index));
        board.set(index, null);
    }

    @Override
    public String toString() {
        return board.toString();
    }

}
