package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    Button hitBtn;
    Button resetBtn;
    Button standBtn;
    Button startBtn;

    Scene scene;
    VBox vBox;
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
        computerHandLabel = new Label("Computer's Hand: ");

        // Button Start Game
        startBtn = new Button("Start");
        startBtn.setOnAction(e -> {

        });

        // Button draw
        hitBtn = new Button("Hit");
        hitBtn.setOnAction(e -> {
            Card card = drawCard();
            StringBuilder cardString = new StringBuilder(card.toSuit());
            updateDrawLabel(cardString);
            playerHand.add(card);
            playerHandLabel.setText(playerHandLabel.getText() + cardString + " ");
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

        // shuffle deck
        shuffle();

        // HBox player and AI labels
        hBox = new HBox(10);
        hBox.getChildren().addAll(playerHandLabel, computerHandLabel);
        hBox.setAlignment(Pos.CENTER);

        // Vbox outer VBox
        vBox = new VBox(8);
        vBox.getChildren().addAll(cardsLeftLabel, hitLabel, hitBtn, standBtn, resetBtn, hBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(10, 0, 20, 0));

        // Scene
        scene = new Scene(vBox, 600, 200);

        stage.setScene(scene);
        stage.show();
        System.out.println(deckList);
    }

    private void stand() {
        // player keeps cards, but the other person can stand or hit
        while (computerHand.getSum() < playerHand.getSum()) {
            Card card = drawCard();
            computerHand.add(card);
            computerHandLabel.setText(computerHandLabel.getText() + card.toSuit() + " ");
        }
    }

    private void checkWinner() {

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
        computerHandLabel.setText("Computer's Hand: ");
        // need to reset the players hands too the arrays
    }

    private void updateDrawLabel(StringBuilder card) {
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
