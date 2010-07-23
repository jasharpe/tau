package com.taugame.tau.client;

import com.google.gwt.core.client.GWT;

public class GameMessageHandler {
    private final GameModel model;

    private int actionCounter = 0;

    public GameMessageHandler(GameModel model) {
        this.model = model;
    }

    public void updateBoard(String boardMessage) {
        GWT.log("boardMessage: " + boardMessage);
        UpdateData updateData = UpdateData.parseUpdateDataJSON(boardMessage);
        int counter = updateData.getCounter();

        if (counter < actionCounter) {
            GWT.log("Not updating: expected action " + actionCounter + " but got action " + counter);
            return;
        }
        actionCounter = counter;

        model.updateBoard(updateData.getCards());
    }
}
