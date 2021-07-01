package sample;

import static java.lang.Character.isDigit;

public class Card {
    String cardString;
    String number;
    char suit;
    boolean isAce;

    private boolean faceCard;

    // Card is in the form (number)(suit) eg. Ace of Diamonds = ad.
    public Card(String cardString) {
        this.cardString = cardString;
        this.faceCard = false;
        this.isAce = false;
        // Position 1 of the string is the first letter of the suit.
        // Check for numbers bigger than 1 digit, eg. 10, 11, 12, etc.
        if (cardString.length() > 2) {
            number = cardString.substring(0,2);
            suit = cardString.charAt(2);
        } else {
            number = Character.toString(cardString.charAt(0));
            suit = cardString.charAt(1);
        }

        isFaceCard();
    }

    private void isFaceCard() {
        if (number.charAt(0) != 'A' && !isDigit(number.charAt(0))) {
            faceCard = true;
        }
    }

    public int getNumber() {
        if (faceCard) {
            return 10;
        }
        if (number.charAt(0) == 'A') {
            isAce = true;
            return 1;
        }
        return Integer.parseInt(number);
    }

    public boolean isAce() {
        return isAce;
    }

    public StringBuilder toSuit() {
        StringBuilder trueCard = new StringBuilder();
        if (suit == 'd') {
            trueCard.append(number).append(new String(Character.toChars(0x2666)));
        } else if (suit == 'c') {
            trueCard.append(number).append(new String(Character.toChars(0x2663)));
        } else if (suit == 'h') {
            trueCard.append(number).append(new String(Character.toChars(0x2665)));
        } else {
            trueCard.append(number).append(new String(Character.toChars(0x2660)));
        }
        return trueCard;
    }
}
