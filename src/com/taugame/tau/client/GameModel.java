package com.taugame.tau.client;

import com.google.gwt.core.client.GWT;
import com.taugame.tau.shared.Card;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Listener {

    private final GameMaster master;

    private List<Card> cards = new ArrayList<Card>();
    private final Selection selection = new Selection();
    private int actionCounter = 0;

    public GameModel(GameMaster master) {
        this.master = master;
    }

    /**
     * Expects to be given a JSON string
     */
    @Override public void sendMessage(String message) {
        UpdateData updateData = UpdateData.parseUpdateDataJSON(message);
        int counter = updateData.getCounter();

        if (counter < actionCounter) {
            GWT.log("Not updating: expected action " + actionCounter + " but got action " + counter);
            return;
        }
        actionCounter = counter;

        cards = updateData.getCards();
        // TODO persist selections that make sense
        selection.wipeout();
    }

    class Selection {
        private static final int MAX_SELECTION = 3;
        private List<Integer> positionsSelected = new ArrayList<Integer>(3);

        public boolean isSelected(int cardPosition) {
            return positionsSelected.indexOf(cardPosition) != -1;
        }

        /**
         * Updates the selection given that the card in position {@code position} is clicked.
         */
        private void select(int cardPosition) {
            int selectedPosition = positionsSelected.indexOf(cardPosition);
            if (selectedPosition == -1) {
                if (positionsSelected.size() == MAX_SELECTION) {
                    positionsSelected.set(MAX_SELECTION, cardPosition);
                } else {
                    positionsSelected.add(cardPosition);
                }
            } else {
                positionsSelected.remove(selectedPosition);
            }
        }

        private void wipeout() {
            positionsSelected.clear();
        }
    }
}
