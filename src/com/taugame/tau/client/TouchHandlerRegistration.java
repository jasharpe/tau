package com.taugame.tau.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;

public class TouchHandlerRegistration implements HandlerRegistration {

    private final Element element;

    public TouchHandlerRegistration(Element element) {
        this.element = element;
    }

    @Override
    public native void removeHandler() /*-{
        var elt = this.@com.taugame.tau.client.TouchHandlerRegistration::getElement()();
        elt.ontouchstart = null;
        elt.ontouchmove = null;
        elt.ontouchend = null;
    }-*/;

    private Element getElement() {
        return element;
    }
}
