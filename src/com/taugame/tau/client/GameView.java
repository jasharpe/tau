package com.taugame.tau.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.taugame.tau.shared.Card;

public final class GameView implements View, ClickHandler {

    private final GameModel model;
    private final VerticalPanel panel = new VerticalPanel();
    private final Button restartButton;
    private final FlexTable table = new FlexTable();

    public GameView(GameModel model) {
        this.model = model;
        restartButton = new Button("restart", this);
    }

    public Widget getWidget() {
        return panel;
    }

    @Override
    public void onClick(ClickEvent event) {
        restartButton.setEnabled(false);
        model.restart();
    }

    public void redraw() {
        int columns = getNumberOfCardColumns();
        int rows = getNumberOfCardRows();

        panel.clear();
        if (model.getOver()) {
            GWT.log("Rendering end game table");
            List<SimpleImmutablePair<String, Integer>> scores = model.getScores();
            Grid grid = new Grid(scores.size(), 2);
            int row = 0;
            for (SimpleImmutablePair<String, Integer> entry : scores) {
                grid.setWidget(row, 0, new Label(entry.getFirst()));
                grid.setWidget(row, 1, new Label(String.valueOf(entry.getSecond())));
                row++;
            }
            panel.add(grid);
            panel.add(restartButton);
        }

        table.removeAllRows();
        List<Card> cards = model.getCards();
        final Map<Integer, CardPanel> cardPanelMap = new HashMap<Integer, CardPanel>();
        for (int row = 0; row < rows; row++) {
            table.insertRow(row);
            for (int column = 0; column < columns; column++) {
                final int cardPosition = row + column * rows;
                table.addCell(row);
                Card card = cards.get(cardPosition);
                final CardPanel cardPanel = new CardPanel();
                cardPanelMap.put(cardPosition, cardPanel);
                if (card != null) {
                    cardPanel.addStyleName("realCard");
                    if (model.isSelected(cardPosition)) {
                        cardPanel.addStyleName("selectedCard");
                    } else {
                        cardPanel.addStyleName("unselectedCard");
                    }
                    int cardNumber = card.get(0) + 3 * card.get(1) + 9 * card.get(2) + 27 * card.get(3);
                    int offset = cardNumber * 80;
                    cardPanel.getElement().setAttribute("style",
                            "background-position: -" + offset + "px 0;");
                    //cardPanel.add(new Label(card.toString()));
                    cardPanel.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            int deselected = model.selectCard(cardPosition);
                            if (deselected != -1) {
                                CardPanel deselectedCardPanel = cardPanelMap.get(deselected);
                                deselectedCardPanel.removeStyleName("selectedCard");
                                deselectedCardPanel.addStyleName("unselectedCard");
                            }
                            if (model.isSelected(cardPosition)) {
                                cardPanel.removeStyleName("unselectedCard");
                                cardPanel.addStyleName("selectedCard");
                            } else {
                                cardPanel.removeStyleName("selectedCard");
                                cardPanel.addStyleName("unselectedCard");
                            }
                        }
                    });
                } else {
                    cardPanel.addStyleName("fakeCard");
                }
                table.setWidget(row, column, cardPanel);
            }
        }

        panel.insert(table, 0);
    }

    private int getNumberOfCardColumns() {
        return model.getCards().size() / getNumberOfCardRows();
    }

    private int getNumberOfCardRows() {
        return 3;
    }
}
