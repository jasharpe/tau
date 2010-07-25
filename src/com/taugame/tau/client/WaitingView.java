package com.taugame.tau.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class WaitingView implements View {
    private final Panel panel = new AbsolutePanel();

    public WaitingView() {
        panel.add(new Image("/images/throbber.gif"));
    }

    public Widget getWidget() {
        return panel;
    }
}
