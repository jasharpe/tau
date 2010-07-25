package com.taugame.tau.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class GameOverView implements View {
    private final GameOverModel model;
    private final Panel panel = new AbsolutePanel();

    public GameOverView(GameOverModel model) {
        this.model = model;
    }

    public Widget getWidget() {
        return panel;
    }

    public void redraw() {
        panel.clear();
        panel.add(new Label("Hello game over view!"));
    }
}
