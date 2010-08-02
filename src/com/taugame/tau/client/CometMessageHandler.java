package com.taugame.tau.client;

/**
 * Small static wrapper to pass messages along to a GameModel from a pure
 * JS callback.
 */
public class CometMessageHandler {
    private static GameMessageHandler gameMessageHandler = null;
    private static Initializer initializer = null;

    public static void setInitializer(Initializer initializer) {
        CometMessageHandler.initializer = initializer;
    }

    public static void initialize() {
        if (initializer != null) {
            initializer.initialize();
        }
    }

    public static native void exportInitialize()/*-{
        $wnd.initialize = $entry(@com.taugame.tau.client.CometMessageHandler::initialize());
    }-*/;

    public static void setUpdateListener(GameMessageHandler gameMessageHandler) {
        CometMessageHandler.gameMessageHandler = gameMessageHandler;
    }

    public static void updateBoard(UpdateData updateData) {
        if (gameMessageHandler != null) {
            gameMessageHandler.routeMessage(updateData);
        }
    }

    public static native void exportSendBoardUpdate()/*-{
        $wnd.u = $entry(@com.taugame.tau.client.CometMessageHandler::updateBoard(Lcom/taugame/tau/client/UpdateData;));
    }-*/;
}
