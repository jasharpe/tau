package com.taugame.tau.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class LobbyView implements View, ClickHandler {
    private final LobbyModel model;
    private final Panel panel = new AbsolutePanel();
    private final Button readyButton;

    public LobbyView(LobbyModel model) {
        this.model = model;
        final LobbyModel thisModel = this.model;
        readyButton = new Button("I'm ready", this);
    }

    public Widget getWidget() {
        return panel;
    }

    public void redraw() {
        panel.clear();
        panel.add(readyButton);
    }

    public void doneLoading() {
        if (model.isReady()) {
            readyButton.setText("I'm not ready");
        } else {
            readyButton.setText("I'm ready");
        }
        readyButton.setEnabled(true);
    }

    @Override
    public void onClick(ClickEvent event) {
        readyButton.setEnabled(false);
        model.toggleReady();
    }

    @Override
    public void setFocus() {
        readyButton.setFocus(true);
    }
}
