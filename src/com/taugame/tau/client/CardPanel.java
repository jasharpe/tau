package com.taugame.tau.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class CardPanel extends AbsolutePanel implements HasClickHandlers, HasMouseDownHandlers, HasTouchHandlers {
    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
        return addDomHandler(handler, MouseDownEvent.getType());
    }

    @Override
    public HandlerRegistration addTouchHandler(TouchHandler handler) {
        registerTouchHandler(handler);
        return new TouchHandlerRegistration(this.getElement());
    }

    private native void registerTouchHandler(TouchHandler handler) /*-{
        var element = this.@com.taugame.tau.client.CardPanel::getElement()();

        element.ontouchstart = function(e){
            handler.@com.taugame.tau.client.TouchHandler::onTouchStart(Lcom/google/gwt/user/client/Event;)(e);
        };

        element.ontouchmove = function(e){
            handler.@com.taugame.tau.client.TouchHandler::onTouchMove(Lcom/google/gwt/user/client/Event;)(e);
        };

        element.ontouchend = function(e){
            handler.@com.taugame.tau.client.TouchHandler::onTouchEnd(Lcom/google/gwt/user/client/Event;)(e);
        };
    }-*/;
}
