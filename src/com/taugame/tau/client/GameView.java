package com.taugame.tau.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.taugame.tau.shared.Card;

public final class GameView {

    private final GameModel model;
    private final FlexTable table = new FlexTable();

    public GameView(GameModel model) {
        this.model = model;
    }

    public Widget getWidget() {
        return table;
    }

    public void redraw() {
        GWT.log("Calling redraw()");
        int columns = getNumberOfCardColumns();
        int rows = getNumberOfCardRows();

        table.removeAllRows();
        List<Card> cards = model.getCards();
        for (int row = 0; row < rows; row++) {
            table.insertRow(row);
            for (int column = 0; column < columns; column++) {
                table.addCell(row);
                Card card = cards.get(column + row * columns);
                AbsolutePanel cardPanel = new AbsolutePanel();
                cardPanel.setStyleName("card");
                cardPanel.add(new Label(card.toString()));
                table.setWidget(row, column, cardPanel);
            }
        }
    }

    private int getNumberOfCardColumns() {
        return model.getCards().size() / getNumberOfCardRows();
    }

    private int getNumberOfCardRows() {
        return 3;
    }
}
