package com.taugame.tau.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.taugame.tau.client.StateController.State;

public class NameModel {
    private final TauServiceAsync tauService;
    private final StateController stateController;
    private final NameView view = new NameView(this);

    private String attempt;

    public NameModel(TauServiceAsync tauService, StateController stateController) {
        this.tauService = tauService;
        this.stateController = stateController;
        attempt = "";
        view.redraw();
    }

    public NameView getView() {
        return view;
    }

    public void submitName(String name) {
        attempt = name;
        tauService.joinAs(name, new AsyncCallback<Boolean>() {
            @Override public void onSuccess(Boolean result) {
                if (result) {
                    stateController.changeState(State.ENTER_NAME,
                            State.GAME_STATE_UNKNOWN);
                } else {
                    // TODO
                }
            }

            @Override public void onFailure(Throwable caught) {
                throw new RuntimeException("failed to submit name", caught);
            }
        });
    }
}
