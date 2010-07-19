package com.taugame.tau.server;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.taugame.tau.shared.Board;
import com.taugame.tau.shared.Card;
import com.taugame.tau.shared.Deck;

public class Game {
    private final ConcurrentMap<String, AtomicInteger> scores;
    private final Board board;

    public Game() {
        scores = new ConcurrentHashMap<String, AtomicInteger>();
        board = new Board(new Deck());
    }

    public boolean join(String player) {
        return (scores.putIfAbsent(player, new AtomicInteger(0)) == null);
    }

    public Board start() {
        return board;
    }

    public Board submit(String player, Card card1, Card card2, Card card3) {
        if (board.removeSet(card1, card2, card3)) {
            scores.get(player).incrementAndGet();
            return board;
        }
        return null;
    }

}
