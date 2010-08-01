package com.taugame.tau.client;

import com.google.gwt.user.client.Event;

public class NativeEventDispatcher {

    private static NativeEventHandler handler = null;

    public static void setNativeEventHandler(NativeEventHandler handler) {
        NativeEventDispatcher.handler = handler;
    }

    public static void bodyKeyPressHandler(Event event) {
        if (handler != null) {
            handler.onEvent(event);
        }
    }

    public static native void exportBodyKeyPressHandler() /*-{
        $wnd.bodyKeyPressHandler = $entry(@com.taugame.tau.client.NativeEventDispatcher::bodyKeyPressHandler(Lcom/google/gwt/user/client/Event;));
    }-*/;
}
