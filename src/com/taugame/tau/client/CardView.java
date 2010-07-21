package com.taugame.tau.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.taugame.tau.shared.Card;

public class CardView {
    private Card card = null;
    
    public Widget getWidget() {
        return new AbsolutePanel();
    }
    
    public void setCard(Card card) {
        this.card = card;
    }
}
