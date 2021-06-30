package sample;

public class Card {
    String cardString;

    // Card is in the form (number)(suit) eg. Ace of Diamonds = ad.
    public Card(String card) {
        this.cardString = card;
    }

    /*
    Instead of creating this method, I could have just copied and pasted the
    symbol of the suits into my cards.txt file. Converting the suits into the
    icons/emojis would have been avoided.
     */
    public StringBuilder toSuit() {
        // Position 1 of the string is the first letter of the suit.
        String number;
        char suit;

        // Check for numbers bigger than 1 digit, eg. 10, 11, 12, etc.
        if (cardString.length() > 2) {
            number = cardString.substring(0,2);
            suit = cardString.charAt(2);
        } else {
            number = Character.toString(cardString.charAt(0));
            suit = cardString.charAt(1);
        }

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
