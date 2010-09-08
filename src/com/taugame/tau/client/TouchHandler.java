package com.taugame.tau.client;

import com.google.gwt.user.client.Event;

public interface TouchHandler {
    void onTouchStart(Event event);
    void onTouchMove(Event event);
    void onTouchEnd(Event event);
}