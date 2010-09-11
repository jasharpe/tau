package com.taugame.tau.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * For debugging through "console" commands
 */
public class Logger {
    private static final int GWT_LOG = 1;
    private static final int CONSOLE_LOG = 2;

    //private static final int LOG = GWT_LOG | CONSOLE_LOG;
    private static final int LOG = 0;

    @SuppressWarnings("all")
    public static void log(String message) {
        if ((LOG & CONSOLE_LOG) != 0) {
            consoleLog(message);
        }
        if ((LOG & GWT_LOG) != 0) {
            GWT.log(message);
        }
    }

    public static void alert(String message) {
        log(message);
        Window.alert(message);
    }

    private static native void consoleLog(String msg) /*-{
        if (typeof(console) != "undefined" && console != null) {
            console.log(msg);
        }
    }-*/;
}
