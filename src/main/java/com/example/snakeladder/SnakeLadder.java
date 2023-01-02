package com.example.snakeladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.LightBase;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {
    public static final int tileSize=40, height = 10, width = 10;
    int lowerLine = tileSize*height;
    Player firstPlayer = new Player(tileSize-10, Color.BLACK, "PS");
    Player secondPlayer = new Player(tileSize-10, Color.RED, "KS");

    public int diceValue;

    Button startGameButton;
    Label rolledDiceValue;
    boolean firstPlayerTurn = true, gameStarted = false, secondPlayerTurn = false;

    Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize+100);


        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }
        //root.getChildren().add(new Tile((tileSize)));

        Image img = new Image("C:\\Users\\Prathmesh\\IdeaProjects\\SnakeLadder\\src\\main\\SnakeLadderBoard12Nov.jpg");
        ImageView boardImage = new ImageView();
        boardImage.setImage(img);
        boardImage.setFitWidth(tileSize*width);
        boardImage.setFitHeight(tileSize*height);

        startGameButton = new Button("Start");
        startGameButton.setTranslateX(170);
        startGameButton.setTranslateY(lowerLine+50);
        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted =true;
                startGameButton.setText("Ongoing game");
                startGameButton.setDisable(true);
            }
        });

        Button playerOneButton = new Button("Player One");
        playerOneButton.setTranslateX(30);
        playerOneButton.setTranslateY(lowerLine+20);
        Button playerTwoButton = new Button("Player Two");
        playerTwoButton.setTranslateX(300);
        playerTwoButton.setTranslateY(lowerLine+20);

        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
             if(gameStarted){
                 if(firstPlayerTurn){

                setDiceValue();
             firstPlayer.movePlayer(diceValue);
             if(firstPlayer.playerWon() != null){
                 rolledDiceValue.setText(firstPlayer.playerWon());
                firstPlayerTurn =false;
                secondPlayerTurn = true;
                gameStarted = false;
                startGameButton.setDisable(false);
                 startGameButton.setText("Start game");
             }
             firstPlayerTurn = false;
             secondPlayerTurn = true;
            }
        }}});
        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    if(secondPlayerTurn){

                        setDiceValue();
                        secondPlayer.movePlayer(diceValue);
                        if(secondPlayer.playerWon() != null){
                            rolledDiceValue.setText(secondPlayer.playerWon());
                            firstPlayerTurn =true;
                            secondPlayerTurn = false;
                            gameStarted = false;
                            startGameButton.setDisable(false);
                            startGameButton.setText("Start game");
                        }
                        firstPlayerTurn = true;
                        secondPlayerTurn = false    ;
                    }
                }}});

        rolledDiceValue = new Label("Start the game");
        rolledDiceValue.setTranslateY(lowerLine+20);
        rolledDiceValue.setTranslateX(160);
        root.getChildren().addAll(boardImage, playerOneButton,playerTwoButton,
                firstPlayer.getCoin(),
                secondPlayer.getCoin(),rolledDiceValue,
                startGameButton

        );
        return root;
    }
    private void setDiceValue(){
        diceValue = (int)(Math.random()*6+1);
        rolledDiceValue.setText("Dice Value: " +diceValue);
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(SnakeLadder.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}