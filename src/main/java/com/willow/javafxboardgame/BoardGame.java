package com.willow.javafxboardgame;

import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class BoardGame extends Application {
    private Background uiBackground;
    private Group root;
    private Group gameBoard;
    private Scene scene;
    private StackPane uiLayout;
    private VBox uiContainer;
    private ImageView boardGameBackPlate;

    private ImageView logoLayer;
    private TextFlow infoOverlay;
    private Image splashScreen;
    private Image helpLayer;
    private Image legalLayer;
    private Image creditLayer;
    private Image scoreLayer;

    private Image alphaLogo;
    private Button gameButton;
    private Button helpButton;
    private Button legalButton;
    private Button creditButton;
    private Button scoreButton;

    private Text playText;
    private Text moreText;
    private Text helpText;
    private Text cardText;
    private Text copyrightText;
    private Text creditText;
    private Text codeText;

    @Override
    public void start(Stage stage) {
        loadImageAssets();
        createTextAssets();
        createBoardGameNodes();
        addNodesToSceneGraph();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        gameButton.setOnAction(actionEvent -> System.out.println("Starting Game"));
        helpButton.setOnAction(actionEvent -> System.out.println("Game Instructions"));
        scoreButton.setOnAction(actionEvent -> System.out.println("High Scores"));
        legalButton.setOnAction(actionEvent -> System.out.println("Copyrights"));
        creditButton.setOnAction(actionEvent -> System.out.println("Credits"));

        stage.show();
    }

    private void addNodesToSceneGraph() {
        root.getChildren().addAll(gameBoard, uiLayout);
        uiLayout.getChildren().addAll(logoLayer, boardGameBackPlate, infoOverlay, uiContainer);
        uiContainer.getChildren().addAll(gameButton, helpButton, legalButton, creditButton, scoreButton);
        infoOverlay.getChildren().addAll(playText, moreText);
    }

    private void createBoardGameNodes() {
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

    private void loadImageAssets() {

        Image backPlate = new Image(Objects.requireNonNull(getClass().getResource("/images/backplate.png"))
                .toString(), 1280, 640, true, false, true);
        splashScreen = new Image(Objects.requireNonNull(getClass().getResource("/images/welcome.png")).toString(),
                1280, 640, true, false, true);
        helpLayer = new Image(Objects.requireNonNull(getClass().getResource("/images/instructions.png")).toString(),
                1280, 640, true, false, true);
        legalLayer = new Image(Objects.requireNonNull(getClass().getResource("/images/copyrights.png")).toString(),
                1280, 640, true, false, true);
        creditLayer = new Image(Objects.requireNonNull(getClass().getResource("/images/credits.png")).toString(),
                1280, 640, true, false, true);
        scoreLayer = new Image(Objects.requireNonNull(getClass().getResource("/images/high-scores.png")).toString(),
                1280, 640, true, false, true);
        alphaLogo = new Image(Objects.requireNonNull(getClass().getResource("/images/alphalogo.png")).toString(),
                1280, 640, true, false, true);

        BackgroundImage uiBackgroundImage = new BackgroundImage(backPlate, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        uiBackground = new Background(uiBackgroundImage);
    }

    private void createTextAssets() {
        playText = new Text("Press the Start Game Button to Start!\n");
        playText.setFill(Color.WHITE);
        playText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        moreText = new Text("Use other buttons for instructions, \ncopyrights, credits and high scores.");
        moreText.setFill(Color.WHITE);
        moreText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 50));
        helpText = new Text("To play game roll dice, advance gamepiece, follow gameboard instruction.");
        cardText = new Text("If you land on square that requires card draw it will appear in UI area.");
        copyrightText = new Text("Copyright 2022 Omar Fernando Moreno Benito, All Rights Reserved. \n");
        creditText = new Text("Digital Imaging, 3D Modeling, 3D Texture Mapping, by Omar Fernando Moreno Benito. \n");
        codeText = new Text("Game Design, User Interface Design, Java Programming by Omar Fernando Moreno Benito.");
    }


    public static void main(String[] args) {
        launch();
    }
}