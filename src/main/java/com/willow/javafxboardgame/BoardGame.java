package com.willow.javafxboardgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class BoardGame extends Application {
    private Group root, gameBoard;
    private Scene scene;
    private StackPane uiLayout;
    VBox uiContainer;
    Insets uiPadding;
    ImageView boardGameBackPlate;
    TextFlow infoOverlay;
    Image splashScreen, helpLayer, legalLayer, creditLayer, scoreLayer;
    Button gameButton, helpButton, legalButton, creditButton, scoreButton;
    @Override
    public void start(Stage stage) {
        createBoardGameNodes();
        addNodesToSceneGraph();
        stage.setScene(scene);
        stage.show();
    }

    private void addNodesToSceneGraph() {
        root.getChildren().add(gameBoard);
        root.getChildren().add(uiLayout);
        uiLayout.getChildren().add(uiContainer);
        uiContainer.getChildren().addAll(gameButton, helpButton,legalButton, creditButton, scoreButton);
    }

    private void createBoardGameNodes() {
        root = new Group();
        gameBoard = new Group();
        scene = new Scene(root, 640, 400);
        scene.setFill(Color.TRANSPARENT);
        uiLayout = new StackPane();
        uiLayout.setBackground(Background.EMPTY);
        uiContainer = new VBox();
        uiContainer.setAlignment(Pos.TOP_RIGHT);
        uiPadding = new Insets(0, 0, 10, 10);
        uiContainer.setPadding(uiPadding);
        gameButton = new Button();
        gameButton.setText("Start Game");
        helpButton = new Button();
        helpButton.setText("Game Rules");
        scoreButton = new Button();
        scoreButton.setText("High Scores");
        legalButton = new Button();
        legalButton.setText("Disclaimers");
        creditButton = new Button();
        creditButton.setText("Game Credits");
    }

    public static void main(String[] args) {
        launch();
    }
}