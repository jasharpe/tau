package com.taugame.tau.client;

import com.google.gwt.core.client.GWT;

public class GameMessageHandler {
    private final LobbyModel lobbyModel;
    private final GameModel gameModel;

    private int actionCounter = 0;

    public GameMessageHandler(LobbyModel lobbyModel, GameModel gameModel) {
        this.lobbyModel = lobbyModel;
        this.gameModel = gameModel;
    }

    public void routeMessage(UpdateData updateData) {
        GWT.log("EVENT TYPE: " + updateData.getType());
        int counter = updateData.getCounter();

        if (!updateData.isEndUpdate()) {
            if (counter < actionCounter) {
                GWT.log("Not updating: expected action " + actionCounter + " but got action " + counter);
                return;
            }
            actionCounter = counter;
        }

        if (updateData.isLobbyUpdate()) {
            AndroidConsole.log("Lobby update");
            GWT.log("Lobby update");
            lobbyModel.updateLobby(updateData);
        } else if (updateData.isBoardUpdate()) {
            AndroidConsole.log("Board update " + updateData.getCards());
            GWT.log("Board update " + updateData.getCards());
            gameModel.updateBoard(updateData.getCards());
        } else if (updateData.isEndUpdate()) {
            AndroidConsole.log("End update " + updateData.getScoreList());
            GWT.log("End update " + updateData.getScoreList());
            gameModel.endGame(updateData.getScoreList());
        }
    }
}
