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

    private static void initialize() {
        if (initializer != null) {
            initializer.initialize();
        }
    }

    public static void restartIntentionally() {
        setIntentionalRestartTrue();
        restart();
        setIntentionalRestartFalse();
    }

    private static native void setIntentionalRestartTrue() /*-{
        $wnd.intentionalRestart = true;
    }-*/;

    private static native void setIntentionalRestartFalse() /*-{
        $wnd.intentionalRestart = false;
    }-*/;

    public static void restart() {
        Logger.log("restarting");
        initializer.reinitialize();
    }

    public static native void exportInitialize()/*-{
        $wnd.initialize = $entry(@com.taugame.tau.client.CometMessageHandler::initialize());
    }-*/;

    public static native void exportRestart()/*-{
        $wnd.restart = $entry(@com.taugame.tau.client.CometMessageHandler::restart());
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
