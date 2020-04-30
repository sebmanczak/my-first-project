package com.kodilla;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Blackjack extends Application {
    private final Deck deck = new Deck(1);
    private final Hand hand = new Hand();
    private final Hand dealer = new Hand();

    private boolean busted;
    private boolean playerTurn;

    private FlowPane cards = new FlowPane(Orientation.VERTICAL);
    private FlowPane dealerCards = new FlowPane(Orientation.VERTICAL);
    private Label totalLabel = new Label();
    private Label totalLabelDealer = new Label();

    private Label dealerLbl = new Label("Karty krupiera");
    private Label playerLbl = new Label("Twoje Karty");

    private Label status = new Label();
    private Image imageback = new Image("greentable.png");

    private Player player;
    private Label playerNameLabel = new Label();
    private Label playersMoneyLabel = new Label();

    private Croupier croupier;
    private Label croupierNameLabel = new Label();
    private Label croupiersMoneyLabel = new Label();

    private Label betCommand = new Label();
    private TextField playerBet = new TextField();

    public void drawCard(Hand hand, FlowPane pane, Label l) {
        try {
            Card card = deck.dealCard();
            ImageView img = new ImageView(card.getCardImage());
            pane.getChildren().add(img);
            hand.addCard(card);

            int handTotal = hand.evaluateHand();

            StringBuilder total = new StringBuilder();
            if (hand.getSoft() > 0) {
                total.append(handTotal - 10).append("/");
            }
            total.append(handTotal);
            l.setText(total.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void newDeck() {
        deck.restoreDeck();
        deck.shuffle();
        System.out.println("Talia przetasowana");
    }

    public void newHand() {
        // check if we should shuffle
        if (deck.getNumberOfCardsRemaining() <= deck.getSizeOfDeck() * 0.2) {
            newDeck();
        }

        //clear everything
        hand.discardHand();
        dealer.discardHand();
        cards.getChildren().removeAll(cards.getChildren());
        dealerCards.getChildren().removeAll(dealerCards.getChildren());
        totalLabel.setText("");
        totalLabelDealer.setText("");

        busted = false;
        playerTurn = true;

        //draw cards for the initial hands, player gets 2, dealer 1
        drawCard(hand, cards, totalLabel);
        drawCard(dealer, dealerCards, totalLabelDealer);
        drawCard(hand, cards, totalLabel);

        status.setText("Twoj ruch");
    }

    @Override
    public void start(Stage primaryStage) {
        player = new Player("Seb", 100.0);

        croupier = new Croupier(1000.0);



        // Update all text colors and fonts
        totalLabel.setFont(new Font("Arial", 24));
        totalLabel.setTextFill(Color.web("#FFF"));

        totalLabelDealer.setFont(new Font("Arial", 24));
        totalLabelDealer.setTextFill(Color.web("#FFF"));

        status.setTextFill(Color.web("#FFF"));
        status.setFont(new Font("Arial", 24));

        dealerLbl.setTextFill(Color.web("#FFF"));
        dealerLbl.setFont(new Font("Ariel", 24));



        playerLbl.setTextFill(Color.web("#FFF"));
        playerLbl.setFont(new Font("Arial", 24));

        playerNameLabel.setTextFill(Color.web("#FFF"));
        playerNameLabel.setFont(new Font("Arial", 24));

        croupierNameLabel.setTextFill(Color.web("#FFF"));
        croupierNameLabel.setFont(new Font("Arial", 24));

        playersMoneyLabel.setTextFill(Color.web("#FFF"));
        playersMoneyLabel.setFont(new Font("Arial", 24));

        betCommand.setTextFill(Color.web("#FFF"));
        betCommand.setFont(new Font("Arial", 24));

        croupiersMoneyLabel.setTextFill(Color.web("#FFF"));
        croupiersMoneyLabel.setFont(new Font("Arial", 24));



        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Button drawbtn = new Button();
        drawbtn.setText("Hit");
        drawbtn.setOnAction((e) -> {
            if (playerTurn && !busted) {
                drawCard(hand, cards, totalLabel);

                if (hand.evaluateHand() > 21) {
                    // you are busted
                    System.out.println("Przegrales");
                    busted = true;
                    playerTurn = false;
                    status.setText("Przegrales");
                }
            }
        });

        Button standbtn = new Button();
        standbtn.setText("Stand");
        standbtn.setOnAction((e) -> {
            if (playerTurn && !busted) {
                playerTurn = false;
                while (dealer.evaluateHand() < 17) {
                    drawCard(dealer, dealerCards, totalLabelDealer);
                }

                int playerTotal = hand.evaluateHand();
                int dealerTotal = dealer.evaluateHand();

                System.out.println("Karty gracza: " + hand);
                System.out.println("Karty krupiera: " + dealer);

                if (playerTotal == 21) {
                    System.out.println("Wygrales");
                    status.setText("Wygrales");
                } else if (dealerTotal <= 21 && playerTotal == dealerTotal) {
                    System.out.println("Remis, przegrales");
                    status.setText("Remis, przegrales");
                } else if (dealerTotal <= 21 && playerTotal <= dealerTotal) {
                    System.out.println("Przegrales");
                    status.setText("Przegrales");
                } else {
                    System.out.println("Wygrales");
                    status.setText("Wygrales");
                }
            }
        });

        Button newbtn = new Button();
        newbtn.setText("Nowe rozdanie");
        newbtn.setOnAction((e) -> {
            newHand();
        });

        playerNameLabel.setText("Pieniadze gracza: " + player.getName());
        playersMoneyLabel.setText("" + player.getPlayerMoney());

        betCommand.setText("Wprowadz wartosc do obstawienia: ");

        croupierNameLabel.setText("Pieniadze krupiera: ");
        croupiersMoneyLabel.setText("" + croupier.getCroupierMoney());


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(8);
        grid.setVgap(8);

        grid.add(dealerCards, 0, 0, 1, 1);
        grid.add(dealerLbl, 0, 1);
        grid.add(totalLabelDealer, 1, 1, 2, 1);

        Pane p = new Pane();
        p.setPrefSize(550, 200);
        grid.add(p, 0, 2);

        grid.add(cards, 0, 3, 1,1);
        grid.add(playerLbl, 0, 4);
        grid.add(totalLabel, 1,4, 2, 1);
        grid.add(drawbtn, 0,5);
        grid.add(standbtn, 1, 5);
        grid.add(newbtn, 2,5);
        grid.add(status, 0,6, 3, 1);
        grid.add(playerNameLabel, 0, 7, 1,1);
        grid.add(playersMoneyLabel, 1, 7, 1, 1);
        grid.add(betCommand, 0, 8, 1, 1);
        grid.add(playerBet, 1, 8, 1, 1);
        grid.add(croupierNameLabel, 0, 9, 1, 1);
        grid.add(croupiersMoneyLabel, 1, 9, 1, 1);

        grid.setBackground(background);

        grid.setGridLinesVisible(true);
        Scene scene = new Scene(grid, 1600, 900);

        primaryStage.setTitle("BlackJack");
        primaryStage.setScene(scene);
        primaryStage.show();

        newDeck();
        newHand();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

