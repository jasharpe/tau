package com.taugame.tau.server;

import java.util.List;
import java.util.Map.Entry;

public interface GameListener {

    void boardChanged(Board board);
    void gameEnded(List<Entry<String, Integer>> rankings);

}
