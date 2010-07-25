package com.taugame.tau.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NameView implements View {

    private final NameModel model;
    private final Panel panel = new AbsolutePanel();
    final TextBox nameBox;

    public NameView(NameModel model) {
        this.model = model;
        nameBox = new TextBox();
        final NameModel thisModel = this.model;
        nameBox.addKeyPressHandler(new KeyPressHandler() {
            @Override public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == '\r') {
                    thisModel.submitName(nameBox.getText());
                }
            }
        });
    }

    public Widget getWidget() {
        return panel;
    }

    public void redraw() {
        panel.clear();
        nameBox.setText("");
        panel.add(nameBox);
    }

    public void resetName() {

    }
}
