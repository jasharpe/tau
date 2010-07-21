package com.taugame.tau.client;

import java.util.ArrayList;
import java.util.List;

public class CometMessenger {
    private static Listener updateListener;
    
    public static void setUpdateListener(Listener listener) {
        CometMessenger.updateListener = listener;
    }
    
    public static void sendUpdate(String message) {
        updateListener.sendMessage(message);
    }
    
    public static native void exportSendUpdate()/*-{
        $wnd.u = $entry(@com.taugame.tau.client.CometMessenger::sendUpdate(Ljava/lang/String;));
    }-*/;
}
