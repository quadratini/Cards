package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    Label cardsLeftLabel;
    Label drawLabel;
    Button drawBtn;

    Scene scene;
    VBox vBox;

    ArrayList<String> deckList = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        deckList = loadDeck(new File("src/sample/cards.txt"));

        stage.setTitle("Deck Machine");

        // Labels
        cardsLeftLabel = new Label("Cards Left: " + deckList.size());
        drawLabel = new Label("You Drew: " + "card");
        drawLabel.setVisible(false);

        // Button draw
        drawBtn = new Button("Draw");
        drawBtn.setOnAction(e -> {
            System.out.println(drawCard().toSuit());
            // updateDrawLabel();
        });

        // Vbox
        vBox = new VBox(8);
        vBox.getChildren().addAll(cardsLeftLabel, drawLabel, drawBtn);
        vBox.setAlignment(Pos.CENTER);

        // Scene
        scene = new Scene(vBox, 300, 275);

        stage.setScene(scene);
        stage.show();
        System.out.println(deckList);
    }

    private void updateDrawLabel(Card card) {
        drawLabel.setText("You Drew: " + card);
        drawLabel.setVisible(true);
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
