package sample;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int getSum() {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getNumber();
        }
        return sum;
    }
}
