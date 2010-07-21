package com.taugame.tau.shared;

public class Game {
    public static final int[] TIMES_TWO_MOD_THREE = new int[] {0, 2, 1, 0, 2};

    public static boolean isSet(Card card1, Card card2, Card card3) {
        for (int property = 0; property < 4; property++) {
            int sum = card1.get(property) + card2.get(property);
            if (card3.get(property) != TIMES_TWO_MOD_THREE[sum]) return false;
        }
        return true;
    }

}
