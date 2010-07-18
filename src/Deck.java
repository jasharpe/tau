import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Deck {

    private final Iterator<Card> deck;

    public Deck() {
        List<Card> cards = new ArrayList<Card>(81);
        for (int colour = 0; colour < 3; colour++) {
            for (int number = 0; number < 3; number++) {
                for (int fill = 0; fill < 3; fill++) {
                    for (int shape = 0; shape < 3; shape++) {
                        cards.add(new Card(colour, number, fill, shape));
                    }
                }
            }
        }
        Collections.shuffle(cards);
        deck = cards.iterator();
    }

    public Card getCard() {
        return deck.hasNext() ? deck.next() : null;
    }

}
