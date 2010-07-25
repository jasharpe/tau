package com.taugame.tau.client;

public class GameOverModel {
    private final TauServiceAsync tauService;
    private final StateController stateController;
    private final GameOverView view = new GameOverView(this);

    public GameOverModel(TauServiceAsync tauService, StateController stateController) {
        this.tauService = tauService;
        this.stateController = stateController;
        view.redraw();
    }

    public GameOverView getView() {
        return view;
    }
}
