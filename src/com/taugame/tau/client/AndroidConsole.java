package com.taugame.tau.client;

/**
 * For Android debugging through "console" commands
 */
public class AndroidConsole {
    public static native void log(String msg) /*-{
        if (console != null) {
            console.log(msg);
        }
    }-*/;
}
