package com.taugame.tau.client;

import com.google.gwt.event.shared.HandlerRegistration;

public interface HasTouchHandlers {
    HandlerRegistration addTouchHandler(TouchHandler handler);
}
