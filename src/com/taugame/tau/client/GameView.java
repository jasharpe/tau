package com.taugame.tau.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.taugame.tau.shared.Card;

public final class GameView implements View, ClickHandler, NativeEventHandler {

    private final GameModel model;
    private final VerticalPanel panel = new VerticalPanel();
    private final Button restartButton;
    private final FlexTable table = new FlexTable();
    private Map<Character, Integer> keyMap;
    private Map<Integer, CardPanel> cardPanelMap = new HashMap<Integer, CardPanel>();

    public GameView(GameModel model) {
        this.model = model;
        restartButton = new Button("restart", this);
        keyMap = new HashMap<Character, Integer>();
        keyMap.put('q', 0);
        keyMap.put('a', 1);
        keyMap.put('z', 2);
        keyMap.put('w', 3);
        keyMap.put('s', 4);
        keyMap.put('x', 5);
        keyMap.put('e', 6);
        keyMap.put('d', 7);
        keyMap.put('c', 8);
        keyMap.put('r', 9);
        keyMap.put('f', 10);
        keyMap.put('v', 11);
        keyMap.put('t', 12);
        keyMap.put('g', 13);
        keyMap.put('b', 14);
        keyMap.put('y', 15);
        keyMap.put('h', 16);
        keyMap.put('n', 17);
        keyMap.put('u', 18);
        keyMap.put('j', 19);
        keyMap.put('m', 20);
    }

    public Widget getWidget() {
        return panel;
    }

    @Override
    public void onClick(ClickEvent event) {
        event.preventDefault();
        restartButton.setEnabled(false);
        model.restart();
    }

    public void redraw() {
        int columns = getNumberOfCardColumns();
        int rows = getNumberOfCardRows();

        panel.clear();
        if (model.getIsOver()) {
            Logger.log("Rendering end game table");
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
        cardPanelMap.clear();
        for (int row = 0; row < rows; row++) {
            table.insertRow(row);
            for (int column = 0; column < columns; column++) {
                final int cardPosition = row + column * rows;
                table.addCell(row);
                Card card = cards.get(cardPosition);
                final CardPanel cardPanel = new CardPanel();
                if (card != null) {
                    cardPanelMap.put(cardPosition, cardPanel);
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
                    cardPanel.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            selectCard(cardPosition);
                        }
                    });
                    cardPanel.addMouseDownHandler(new MouseDownHandler() {
                        @Override
                        public void onMouseDown(MouseDownEvent event) {
                            event.preventDefault();
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

    private void selectCard(int cardPosition) {
        CardPanel cardPanelChanged = cardPanelMap.get(cardPosition);
        if (cardPanelChanged == null || model.getIsOver()) {
            return;
        }

        int deselected = model.selectCard(cardPosition);
        if (deselected != -1) {
            CardPanel deselectedCardPanel = cardPanelMap.get(deselected);
            deselectedCardPanel.removeStyleName("selectedCard");
            deselectedCardPanel.addStyleName("unselectedCard");
        }

        if (model.isSelected(cardPosition)) {
            cardPanelChanged.removeStyleName("unselectedCard");
            cardPanelChanged.addStyleName("selectedCard");
        } else {
            cardPanelChanged.removeStyleName("selectedCard");
            cardPanelChanged.addStyleName("unselectedCard");
        }
    }

    private void selectNone() {
        List<Integer> selectedCards =
            new ArrayList<Integer>(model.getSelectedCards());
        for (int position : selectedCards) {
            selectCard(position);
        }
    }

    private int getNumberOfCardColumns() {
        return model.getCards().size() / getNumberOfCardRows();
    }

    private int getNumberOfCardRows() {
        return 3;
    }

    /**
     * key press
     */
    @Override public void onEvent(Event event) {
        char character = Character.toLowerCase((char) event.getKeyCode());
        Integer position = keyMap.get(character);
        if (position != null) {
            selectCard(position);
            return;
        }
        if (event.getKeyCode() == '`' || event.getKeyCode() == '\\'
            || event.getKeyCode() == ' ') {
            selectNone();
        }
    }
}
