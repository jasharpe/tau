package com.taugame.tau.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tau implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    @SuppressWarnings("unused")
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Tau service.
     */
    private final TauServiceAsync tauService = GWT.create(TauService.class);

    public void onModuleLoad() {
        CometMessageHandler.exportListen();
        CometMessageHandler.listen();

        tauService.joinAs("asdf", new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                initializeGame();
            }

            @Override
            public void onFailure(Throwable caught) {
                initializeGame();
                //throw new RuntimeException("Failed to joinAs", caught);
            }
        });
    }

    private void initializeGame() {
        GameModel gameModel = new GameModel(tauService);
        CometMessageHandler.setUpdateListener(new GameMessageHandler(gameModel));
        CometMessageHandler.exportSendBoardUpdate();

        RootPanel.get("game").add(gameModel.getView().getWidget());
    }
}
