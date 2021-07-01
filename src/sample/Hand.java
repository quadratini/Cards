package sample;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private int size;

    public Hand() {
        cards = new ArrayList<>();
        this.size = 0;
    }

    public void add(Card card) {
        cards.add(card);
        this.size++;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getSum() {
        int sum = 0;
        int numAces = 0;
        for (Card card : cards) {
            if (card.isAce()) {
                numAces++;
            }
            sum += card.getNumber();
        }

        for (int i = 0; i < numAces; i++) {
            sum = sum - 1;
            if ((sum + 11) <= 21) {
                sum += 11;
            } else {
                break;
            }
        }

        return sum;
    }

    public int getSize() {
        return this.size;
    }

    public void emptyCards() {
        this.size = 0;
        cards.clear();
    }

}
