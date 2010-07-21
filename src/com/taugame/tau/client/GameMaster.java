package com.taugame.tau.client;

public class GameMaster {
    private final GameModel model;
    private final GameView view;

    public GameMaster() {
        model = new GameModel(this);
        view = new GameView(this);
    }

    public final GameModel getModel() {
        return model;
    }
}
