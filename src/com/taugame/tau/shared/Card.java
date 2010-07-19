package com.taugame.tau.shared;

public final class Card {
    private static final int COLOUR = 0, NUMBER = 1, FILL = 2, SHAPE = 3;
    private final int[] properties;

    public Card(int colour, int number, int fill, int shape) {
        properties = new int[4];
        properties[COLOUR] = colour;
        properties[NUMBER] = number;
        properties[FILL] = fill;
        properties[SHAPE] = shape;
    }

    public static boolean isSet(Card card1, Card card2, Card card3) {
        int value;
        for (int property = 0; property < 4; property++) {
            if ((value = card1.get(property)) == card2.get(property)) {
                if (value != card3.get(property)) return false;
            }
            else {
                int sum = card1.get(property) + card2.get(property);
                switch (sum) {
                case 1: value = 2; break;
                case 2: value = 1; break;
                case 3: value = 0; break;
                }
                if (card3.get(property) != value) {
                    return false;
                }
            }
        }
        return true;
    }

    private int get(int property) {
        return properties[property];
    }

}
