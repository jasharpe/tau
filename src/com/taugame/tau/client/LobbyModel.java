package com.taugame.tau.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.taugame.tau.client.StateController.State;

public class LobbyModel {
    private final LobbyView view = new LobbyView(this);
    private final TauServiceAsync tauService;
    private final StateController stateController;

    private boolean isReady = false;

    public LobbyModel(TauServiceAsync tauService, StateController stateController) {
        this.tauService = tauService;
        this.stateController = stateController;
        view.redraw();
    }

    public LobbyView getView() {
        return view;
    }

    public boolean isReady() {
        return isReady;
    }

    public void toggleReady() {
        isReady = !isReady;
        tauService.setReady(isReady, new AsyncCallback<Void>() {
            @Override public void onSuccess(Void result) {
                view.doneLoading();
            }

            @Override public void onFailure(Throwable caught) {
                throw new RuntimeException("failed to set ready", caught);
            }
        });
    }

    public void updateLobby(UpdateData updateData) {
        stateController.forceChangeState(State.GAME_LOBBY);
    }
}
