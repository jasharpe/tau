package com.taugame.tau.client;


public class GameMessageHandler {
    private final LobbyModel lobbyModel;
    private final GameModel gameModel;

    private int actionCounter = 0;

    public GameMessageHandler(LobbyModel lobbyModel, GameModel gameModel) {
        this.lobbyModel = lobbyModel;
        this.gameModel = gameModel;
    }

    public void routeMessage(UpdateData updateData) {
        Logger.log("EVENT TYPE: " + updateData.getType());
        int counter = updateData.getCounter();

        if (!updateData.isEndUpdate()) {
            if (counter < actionCounter) {
                Logger.log("Not updating: expected action " + actionCounter + " but got action " + counter);
                return;
            }
            Logger.log("counter: " + counter);
            actionCounter = counter;
        }

        if (updateData.isLobbyUpdate()) {
            Logger.log("Lobby update");
            lobbyModel.updateLobby(updateData);
        } else if (updateData.isBoardUpdate()) {
            Logger.log("Board update " + updateData.getCards());
            gameModel.updateBoard(updateData.getCards());
        } else if (updateData.isEndUpdate()) {
            Logger.log("End update " + updateData.getScoreList());
            gameModel.endGame(updateData.getScoreList());
        }
    }
}
