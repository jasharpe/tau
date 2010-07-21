package com.taugame.tau.shared;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public final class Card implements Serializable /* for GWT serialization */ {
    public static final int[] TIMES_TWO_MOD_THREE = new int[] {0, 2, 1, 0, 2};
    private static final int COLOUR = 0, NUMBER = 1, FILL = 2, SHAPE = 3;
    private /* final */ int[] properties;

    public Card(int colour, int number, int fill, int shape) {
        properties = new int[4];
        properties[COLOUR] = colour;
        properties[NUMBER] = number;
        properties[FILL] = fill;
        properties[SHAPE] = shape;
    }

    public static boolean isTau(Card card1, Card card2, Card card3) {
        for (int property = 0; property < 4; property++) {
            int sum = card1.get(property) + card2.get(property);
            if (card3.get(property) != TIMES_TWO_MOD_THREE[sum]) {return false;}
        }
        return true;
    }

    public int get(int property) {
        return properties[property];
    }

    @SuppressWarnings("unused")
    private Card() {}

    @Override
    public String toString() {
        return Arrays.toString(properties);
    }
}
