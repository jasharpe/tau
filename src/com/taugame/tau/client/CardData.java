package com.taugame.tau.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.taugame.tau.shared.Card;

public final class CardData extends JavaScriptObject {
    protected CardData() {}

    private native boolean isEmpty() /*-{
        return this.length < 1;
    }-*/;

    private native int getProperty(int property) /*-{
        return this[property];
    }-*/;

    public Card toCard() {
        if (isEmpty()) {
            return null;
        } else {
            return new Card(getProperty(0), getProperty(1), getProperty(2), getProperty(3));
        }
    }
}
