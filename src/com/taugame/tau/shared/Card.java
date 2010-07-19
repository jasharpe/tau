package com.taugame.tau.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class Card implements Serializable /* for GWT serialization */ {
    private static final int COLOUR = 0, NUMBER = 1, FILL = 2, SHAPE = 3;
    private /* final */ int[] properties;

    public Card(int colour, int number, int fill, int shape) {
        properties = new int[4];
        properties[COLOUR] = colour;
        properties[NUMBER] = number;
        properties[FILL] = fill;
        properties[SHAPE] = shape;
    }

    public int get(int property) {
        return properties[property];
    }

    @SuppressWarnings("unused")
    private Card() {}
}
