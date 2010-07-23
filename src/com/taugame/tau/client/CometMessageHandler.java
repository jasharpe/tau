package com.taugame.tau.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;


/**
 * Small static wrapper to pass messages along to a GameModel from a pure
 * JS callback.
 */
public class CometMessageHandler {
    private static GameMessageHandler gameMessageHandler;

    public static void setUpdateListener(GameMessageHandler gameMessageHandler) {
        CometMessageHandler.gameMessageHandler = gameMessageHandler;
    }

    private static int actionCounter = 0;
    public static void updateBoard(UpdateData updateData) {
        gameMessageHandler.updateBoard(updateData);
    }

    public static native void exportSendBoardUpdate()/*-{
        $wnd.u = $entry(@com.taugame.tau.client.CometMessageHandler::updateBoard(Lcom/taugame/tau/client/UpdateData;));
    }-*/;

    public static void listen() {
        DOM.getElementById("comet-frame").setAttribute("src", "/game?count=" + Random.nextInt());
    }

    public static native void exportListen()/*-{
        $wnd.l = $entry(@com.taugame.tau.client.CometMessageHandler::listen());
    }-*/;
}
