package com.willow.javafxboardgame.ui;

import com.willow.javafxboardgame.controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;


public class UIBoardGame {

    private static Background uiBackground;
    private static Group root;
    private static Group gameBoard;
    private static Scene scene;
    private static StackPane uiLayout;
    private static VBox uiContainer;
    private static ImageView boardGameBackPlate;

    private static ImageView logoLayer;
    private static TextFlow infoOverlay;
    private static Image splashScreen;
    private static Image helpLayer;
    private static Image legalLayer;
    private static Image creditLayer;
    private static Image scoreLayer;

    private static Image alphaLogo;

    private static Button gameButton;
    private static Button helpButton;
    private static Button legalButton;
    private static Button creditButton;
    private static Button scoreButton;

    private static Text playText;
    private static Text moreText;
    private static Text helpText;
    private static Text cardText;
    private static Text copyrightText;
    private static Text creditText;
    private static Text codeText;

    private static GameController controller;
    public static Scene init(){
        controller = new GameController();
        loadImageAssets();
        createTextAssets();
        createBoardGameNodes();
        addNodesToSceneGraph();
        gameButton.setOnAction(actionEvent -> controller.playGame());
        helpButton.setOnAction(actionEvent -> controller.gameInstructions());
        scoreButton.setOnAction(actionEvent -> controller.highScores());
        legalButton.setOnAction(actionEvent -> controller.copyrights());
        creditButton.setOnAction(actionEvent -> controller.credits());

        return scene;
    }
    private static void addNodesToSceneGraph() {
        root.getChildren().addAll(gameBoard, uiLayout);
        uiLayout.getChildren().addAll(logoLayer, boardGameBackPlate, infoOverlay, uiContainer);
        uiContainer.getChildren().addAll(gameButton, helpButton, legalButton, creditButton, scoreButton);
        infoOverlay.getChildren().addAll(playText, moreText);
    }

    private static void createBoardGameNodes() {
        root = new Group();
        gameBoard = new Group();
        scene = new Scene(root, 1280, 640);
        scene.setFill(Color.TRANSPARENT);
        uiLayout = new StackPane();
        uiLayout.setPrefWidth(1280);
        uiLayout.setPrefHeight(640);
        uiLayout.setBackground(Background.EMPTY);
        uiLayout.setBackground(uiBackground);
        boardGameBackPlate = new ImageView();
        boardGameBackPlate.setImage(splashScreen);
        logoLayer = new ImageView();
        logoLayer.setImage(alphaLogo);
        logoLayer.setScaleX(0.8);
        logoLayer.setScaleY(0.8);
        logoLayer.setTranslateX(-75);
        logoLayer.setTranslateY(-170);
        infoOverlay = new TextFlow();
        infoOverlay.setTranslateX(240);
        infoOverlay.setTranslateY(420);
        uiContainer = new VBox(10);
        uiContainer.setAlignment(Pos.TOP_RIGHT);
        var uiPadding = new Insets(16);
        uiContainer.setPadding(uiPadding);
        gameButton = new Button();
        gameButton.setText("Start Game");
        gameButton.setMaxWidth(125);
        helpButton = new Button();
        helpButton.setText("Game Rules");
        helpButton.setMaxWidth(125);
        scoreButton = new Button();
        scoreButton.setText("High Scores");
        scoreButton.setMaxWidth(125);
        legalButton = new Button();
        legalButton.setText("Disclaimers");
        legalButton.setMaxWidth(125);
        creditButton = new Button();
        creditButton.setText("Game Credits");
        creditButton.setMaxWidth(125);
    }

    private static void loadImageAssets() {

        Image backPlate = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/backplate.png"))
                .toString(), 1280, 640, true, false, true);
        splashScreen = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/welcome.png")).toString(),
                1280, 640, true, false, true);
        helpLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/instructions.png")).toString(),
                1280, 640, true, false, true);
        legalLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/copyrights.png")).toString(),
                1280, 640, true, false, true);
        creditLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/credits.png")).toString(),
                1280, 640, true, false, true);
        scoreLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/high-scores.png")).toString(),
                1280, 640, true, false, true);
        alphaLogo = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/alphalogo.png")).toString(),
                1280, 640, true, false, true);

        BackgroundImage uiBackgroundImage = new BackgroundImage(backPlate, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        uiBackground = new Background(uiBackgroundImage);
    }

    private static void createTextAssets() {
        playText = new Text("Press the PLAY GAME Button to Start!\n");
        playText.setFill(Color.WHITE);
        playText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        moreText = new Text("Use other buttons for instructions, \ncopyrights, credits and scores.");
        moreText.setFill(Color.WHITE);
        moreText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 50));
        helpText = new Text("To play game roll the dice, advance\ngame piece and follow game board\ninstruction.");
        helpText.setFill(Color.GREEN);
        helpText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        cardText = new Text("If you land on square\nthat requires you draw a card it will\nappear in the floating UI text area.");
        cardText.setFill(Color.GREEN);
        cardText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        copyrightText = new Text("Copyright 2022 Omar Fernando Moreno Benito.\nAll Rights Reserved. \n");
        copyrightText.setFill(Color.PURPLE);
        copyrightText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        creditText = new Text("Digital Imaging, 3D Modeling, 3D\nTexture Mapping, by Omar Fernando Moreno Benito. \n");
        creditText.setFill(Color.BLUE);
        creditText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        codeText = new Text("Game Design, User Interface Design, \nJava Programming by Omar Fernando Moreno Benito.");
        codeText.setFill(Color.BLUE);
        codeText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
    }
}
