package com.taugame.tau.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tau implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Tau service.
     */
    private final TauServiceAsync tauService = GWT.create(TauService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        GameMaster gameMaster = new GameMaster();
        CometMessenger.setUpdateListener(gameMaster.getModel());
        CometMessenger.exportSendUpdate();
    }
}
