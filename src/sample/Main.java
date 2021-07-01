package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {
    Label cardsLeftLabel;
    Label hitLabel;
    Label playerHandLabel;
    Label computerHandLabel;
    Label playerSumLabel;
    Label computerSumLabel;
    Button hitBtn;
    Button resetBtn;
    Button standBtn;
    Button startBtn;

    Scene scene;
    VBox vBox;
    VBox playerVBox;
    VBox computerVBox;
    HBox hBox;

    ArrayList<String> deckList = new ArrayList<>();

    Hand playerHand = new Hand();
    Hand computerHand = new Hand();

    @Override
    public void start(Stage stage) throws Exception {
        deckList = loadDeck(new File("src/sample/cards.txt"));

        stage.setTitle("Blackjack");

        // Labels
        cardsLeftLabel = new Label("Cards Left: " + deckList.size());
        hitLabel = new Label("You Drew: " + "card");
        hitLabel.setVisible(false);
        playerHandLabel = new Label("Your Hand: ");
        computerHandLabel = new Label("Dealer's Hand: ");
        playerSumLabel = new Label("Sum: ");
        computerSumLabel = new Label("Sum: ");


        // Button Start Game
        startBtn = new Button("Start");
        startBtn.setOnAction(e -> {
            // player starts the game with 2 cards.
            // the cards alternate between each player (player -> computer -> player ...etc)
            Card playerCard1 = drawCard();
            playerHand.add(playerCard1);
            Card computerCard1 = drawCard();
            computerHand.add(computerCard1);
            Card playerCard2 = drawCard();
            playerHand.add(playerCard2);
            Card computerCard2 = drawCard();
            computerHand.add(computerCard2);

            playerHandLabel.setText("Your Hand: " + playerCard1.toSuit() + " " + playerCard2.toSuit() + " ");
            computerHandLabel.setText("Dealer's Hand: " + computerCard1.toSuit() + " ? ");

            startBtn.setDisable(true);
            hitLabel.setText("Dealer dealt you: " + playerCard1.toSuit() + " " + playerCard2.toSuit() + " ");
            hitLabel.setVisible(true);

            // idk why it has to be called before the label, it just updates slow?
            playerHand.getSum();
            computerHand.getSum();
            playerSumLabel.setText(playerSumLabel.getText() + playerHand.getSum());
            computerSumLabel.setText(computerSumLabel.getText() + " ? ");
            if (playerHand.getSum() == 21) {
                hitLabel.setText("Blackjack. You won!");
                hitBtn.setDisable(true);
                standBtn.setDisable(true);
                startBtn.setDisable(true);
                computerHandLabel.setText("Dealer's Hand: ");
                for (int i = 0; i < computerHand.getSize(); i++) {
                    computerHandLabel.setText(computerHandLabel.getText() + computerHand.getCards().get(i).toSuit() + " ");
                }
                computerHand.getSum();
                computerSumLabel.setText("Sum: " + computerHand.getSum());
            } else {
                startBtn.setDisable(true);
                hitBtn.setDisable(false);
                standBtn.setDisable(false);
            }
        });

        // Button draw
        hitBtn = new Button("Hit");
        hitBtn.setOnAction(e -> {
            Card card = drawCard();
            StringBuilder cardString = new StringBuilder(card.toSuit());
            updateHitLabel(cardString);
            playerHand.add(card);
            playerHandLabel.setText(playerHandLabel.getText() + cardString + " ");
            playerSumLabel.setText("Sum: " + playerHand.getSum());
            if (isBust(playerHand.getSum())) {
                hitLabel.setText("You BUSTED!");
                hitBtn.setDisable(true);
                standBtn.setDisable(true);
                startBtn.setDisable(true);
            }
        });

        // Button stand
        standBtn = new Button("Stand");
        standBtn.setOnAction(e -> {
            stand();
        });

        // Button reset deck
        resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            try {
                reset();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        // INIT
        // shuffle deck
        shuffle();
        // Disabled buttons since game did not start yet
        hitBtn.setDisable(true);
        standBtn.setDisable(true);

        // VBox for player
        playerVBox = new VBox(8);
        playerVBox.getChildren().addAll(playerHandLabel, playerSumLabel);

        // VBox for computer
        computerVBox = new VBox(8);
        computerVBox.getChildren().addAll(computerHandLabel, computerSumLabel);

        // HBox player and computer labels
        hBox = new HBox(10);
        hBox.getChildren().addAll(playerVBox, computerVBox);
        hBox.setAlignment(Pos.CENTER);

        // Vbox outer VBox
        vBox = new VBox(8);
        vBox.getChildren().addAll(cardsLeftLabel, hitLabel, startBtn, hitBtn, standBtn, resetBtn, hBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(10, 0, 20, 0));

        // Scene
        scene = new Scene(vBox, 600, 400);

        stage.setScene(scene);
        stage.show();
        System.out.println(deckList);
    }

    private boolean isBust(int sum) {
        return sum > 21;
    }

    private void stand() {
        // player keeps cards, but the other person can stand or hit
        computerHandLabel.setText("Dealer's Hand: ");
        for (int i = 0; i < computerHand.getSize(); i++) {
            computerHandLabel.setText(computerHandLabel.getText() + computerHand.getCards().get(i).toSuit() + " ");
        }
        while (computerHand.getSum() < 17) {
            Card card = drawCard();
            computerHand.add(card);
            computerHand.getSum();
            computerHandLabel.setText(computerHandLabel.getText() + card.toSuit() + " ");
            computerSumLabel.setText("Sum: " + computerHand.getSum());
        }
        if (isBust(computerHand.getSum())) {
            hitLabel.setText("Dealer BUSTED! You won!");
        } else if (playerHand.getSum() > computerHand.getSum()) {
            hitLabel.setText("You won!");
        } else if (playerHand.getSum() == computerHand.getSum()) {
            hitLabel.setText("It is a Draw.");
        } else if (computerHand.getSum() > playerHand.getSum()) {
            hitLabel.setText("You lost...");
        }
        hitBtn.setDisable(true);
        standBtn.setDisable(true);
        startBtn.setDisable(true);
    }

    private void shuffle() {
        ArrayList<String> tempDeck = new ArrayList<>();
        Random rand = new Random();
        int deckSize = deckList.size();
        for (int i = 0; i < deckSize; i++) {
            int randomNum = rand.nextInt(deckList.size());
            String card = deckList.remove(randomNum);
            tempDeck.add(card);
        }
        deckList = tempDeck;
        hitLabel.setText("Deck reset and shuffled");
    }

    private void reset() throws FileNotFoundException {
        deckList = loadDeck(new File("src/sample/cards.txt"));
        shuffle();
        cardsLeftLabel.setText("Cards Left: " + deckList.size());
        playerHandLabel.setText("Your Hand: ");
        computerHandLabel.setText("Dealer's Hand: ");
        playerSumLabel.setText("Sum: ");
        computerSumLabel.setText("Sum: ");
        playerHand.emptyCards();
        computerHand.emptyCards();
        startBtn.setDisable(false);
        hitBtn.setDisable(true);
        standBtn.setDisable(true);
        System.out.println(deckList);
    }

    private void updateHitLabel(StringBuilder card) {
        hitLabel.setText("You Drew: " + card);
        hitLabel.setVisible(true);
    }

    private ArrayList<String> loadDeck(File deckFile) throws FileNotFoundException {
        Scanner scan = new Scanner(deckFile);
        ArrayList<String> deckList = new ArrayList<>();
        while (scan.hasNext()) {
            deckList.add(scan.next());
        }
        return deckList;
    }

    private Card drawCard() {
        Card card = new Card(deckList.remove(0));
        cardsLeftLabel.setText("Cards Left: " + deckList.size());
        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
