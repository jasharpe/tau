package com.taugame.tau.client;

/**
 * Small static wrapper to pass messages along to a GameModel from a pure
 * JS callback.
 */
public class CometMessageHandler {
    private static GameMessageHandler gameMessageHandler;
    private static GameMessageHandler endGameMessageHandler;

    public static void setUpdateListener(GameMessageHandler gameMessageHandler) {
        CometMessageHandler.gameMessageHandler = gameMessageHandler;
    }

    public static void setEndGameListener(GameMessageHandler gameMessageHandler) {
        CometMessageHandler.endGameMessageHandler = gameMessageHandler;
    }

    private static int actionCounter = 0;

    public static void updateBoard(UpdateData updateData) {
        gameMessageHandler.routeMessage(updateData);
    }

    public static native void exportSendBoardUpdate()/*-{
        $wnd.u = $entry(@com.taugame.tau.client.CometMessageHandler::updateBoard(Lcom/taugame/tau/client/UpdateData;));
    }-*/;
}
