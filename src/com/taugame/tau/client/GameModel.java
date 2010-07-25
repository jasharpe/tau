package com.taugame.tau.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.taugame.tau.shared.Card;

public class GameModel {

    private static final AsyncCallback<Void> NO_OP_CALLBACK =
        new AsyncCallback<Void>() {
            @Override public void onFailure(Throwable caught) {}
            @Override public void onSuccess(Void result) {}
        };

    private final Selection selection = new Selection();
    private final GameView gameView = new GameView(this);
    private final TauServiceAsync tauService;

    private List<Card> cards = new ArrayList<Card>();

    public GameModel(TauServiceAsync tauService) {
        this.tauService = tauService;
        gameView.redraw();
    }

    public GameView getView() {
        return gameView;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void updateBoard(List<Card> cards) {
        this.cards = cards;
        // TODO persist selections that make sense
        selection.clear();
        gameView.redraw();
    }

    public int selectCard(int cardPosition) {
        int deselected = selection.select(cardPosition);

        if (selection.getNumberSelected() == 3) {
            List<Card> selectedCards = selection.getSelectedCards();
            if (Card.isTau(selectedCards.get(0), selectedCards.get(1),
                    selectedCards.get(2))) {
                // TODO keep track of attempted taus while waiting for
                // server's response.

                // Submits the tau to the server. The result of this will be
                // known when the server sends the next update, so no callback
                // is required.
                tauService.submit(selectedCards.get(0), selectedCards.get(1),
                        selectedCards.get(2), NO_OP_CALLBACK);
            }
        }

        return deselected;
    }

    public boolean isSelected(int cardPosition) {
        return selection.isSelected(cardPosition);
    }

    class Selection {
        private static final int MAX_SELECTION_INDEX = 2;
        private List<Integer> positionsSelected = new ArrayList<Integer>();

        public int getNumberSelected() {
            return positionsSelected.size();
        }

        public List<Card> getSelectedCards() {
            List<Card> selectedCards = new ArrayList<Card>();
            for (int cardPosition : positionsSelected) {
                selectedCards.add(cards.get(cardPosition));
            }
            return selectedCards;
        }

        public boolean isSelected(int cardPosition) {
            return positionsSelected.indexOf(cardPosition) != -1;
        }

        /**
         * Updates the selection given that the card in position
         * {@code position} is clicked.
         */
        private int select(int cardPosition) {
            if (cardPosition < 0 || cardPosition >= cards.size()) {
                throw new RuntimeException("Card position "
                        + cardPosition + " is invalid");
            }
            int selectedPosition = positionsSelected.indexOf(cardPosition);
            if (selectedPosition == -1) {
                if (positionsSelected.size() > MAX_SELECTION_INDEX) {
                    return positionsSelected.set(MAX_SELECTION_INDEX, cardPosition);
                } else {
                    positionsSelected.add(cardPosition);
                    return -1;
                }
            } else {
                positionsSelected.remove(selectedPosition);
                return -1;
            }
        }

        private void clear() {
            positionsSelected.clear();
        }
    }
}
