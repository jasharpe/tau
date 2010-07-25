package com.taugame.tau.client;

import com.google.gwt.core.client.JavaScriptObject;

public final class ScoreData extends JavaScriptObject {
    protected ScoreData() {}

    public native String getName() /*-{
        return this[0];
    }-*/;

    public native int getScore() /*-{
        return this[1];
    }-*/;
}
