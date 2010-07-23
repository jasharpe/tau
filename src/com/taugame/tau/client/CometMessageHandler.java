package com.taugame.tau.client;

import com.google.gwt.user.client.DOM;


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
    public static void updateBoard(String boardMessage) {
        gameMessageHandler.updateBoard(boardMessage);
    }

    public static native void exportSendBoardUpdate()/*-{
        $wnd.u = $entry(@com.taugame.tau.client.CometMessageHandler::updateBoard(Ljava/lang/String;));
    }-*/;

    static int count = 0;
    public static void listen() {
        DOM.getElementById("comet-frame").setAttribute("src", "/game?count=" + count);
        count++;
    }

    public static native void exportListen()/*-{
        $wnd.l = $entry(@com.taugame.tau.client.CometMessageHandler::listen());
    }-*/;
}
