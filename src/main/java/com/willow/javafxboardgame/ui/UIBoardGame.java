package com.willow.javafxboardgame.ui;


import com.willow.javafxboardgame.helper.GameControllerHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private static DropShadow dropShadow;
    private static ColorAdjust colorAdjust;

    private static PerspectiveCamera camera;

    public static Scene init() {
        createSpecialEffects();
        loadImageAssets();
        createTextAssets();
        createBoardGameNodes();
        addNodesToSceneGraph();
        addEvents();

        return scene;
    }

    private static void createSpecialEffects() {
        dropShadow = new DropShadow();
        dropShadow.setRadius(0.3);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        dropShadow.setColor(Color.DARKGRAY);

        colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.4);
    }

    private static void addEvents() {
        gameButton.setOnAction(actionEvent -> showStartScreen());
        helpButton.setOnAction(actionEvent -> showInstructions());
        legalButton.setOnAction(actionEvent -> showCopyrights());
        creditButton.setOnAction(actionEvent -> showCredits());
        scoreButton.setOnAction(actionEvent -> System.out.println("High Scores"));

        scene.setOnKeyPressed(GameControllerHelper.keyPressed);
        scene.setOnKeyReleased(GameControllerHelper.keyReleased);
    }

    private static void showCredits() {
        infoOverlay.getChildren().clear();
        infoOverlay.getChildren().addAll(creditText, codeText);
        infoOverlay.setTranslateX(240);
        infoOverlay.setTranslateY(420);
        uiLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        boardGameBackPlate.setImage(creditLayer);
        logoLayer.setEffect(colorAdjust);
        colorAdjust.setHue(-0.9);
    }

    private static void showCopyrights() {
        infoOverlay.getChildren().clear();
        infoOverlay.getChildren().addAll(copyrightText);
        infoOverlay.setTranslateX(200);
        infoOverlay.setTranslateY(430);
        uiLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        boardGameBackPlate.setImage(legalLayer);
        logoLayer.setEffect(colorAdjust);
        colorAdjust.setHue(-0.4);
    }

    private static void showStartScreen() {
        uiLayout.setVisible(false);
        camera.setTranslateZ(-1000);
    }

    private static void showInstructions() {
        infoOverlay.getChildren().clear();
        infoOverlay.getChildren().addAll(helpText, cardText);
        infoOverlay.setTranslateX(130);
        infoOverlay.setTranslateY(400);
        uiLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        boardGameBackPlate.setImage(helpLayer);
        logoLayer.setEffect(colorAdjust);
        colorAdjust.setHue(0.4);
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
        camera = new PerspectiveCamera();
        camera.setTranslateZ(0);
        camera.setNearClip(0.1);
        camera.setFarClip(5000);
        scene = new Scene(root, 1280, 640);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
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
                true);
        helpLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/instructions.png")).toString(), true);
        legalLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/copyrights.png")).toString(), true);
        creditLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/credits.png")).toString(), true);
        scoreLayer = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/high-scores.png")).toString(), true);
        alphaLogo = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/alphalogo.png")).toString(), true);

        BackgroundImage uiBackgroundImage = new BackgroundImage(backPlate, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        uiBackground = new Background(uiBackgroundImage);
    }

    private static void createTextAssets() {
        playText = new Text("Press the PLAY GAME Button to Start!\n");
        playText.setFill(Color.WHITE);
        playText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        playText.setEffect(dropShadow);

        moreText = new Text("Use other buttons for instructions, \ncopyrights, credits and scores.");
        moreText.setFill(Color.WHITE);
        moreText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
        moreText.setEffect(dropShadow);

        helpText = new Text("To play game roll the dice, advance game piece\n and follow game board instruction. ");
        helpText.setFill(Color.GREEN);
        helpText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        helpText.setEffect(dropShadow);

        cardText = new Text("If you land\non square that requires you draw a card it will \nappear in the floating UI text area.");
        cardText.setFill(Color.GREEN);
        cardText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        cardText.setEffect(dropShadow);

        copyrightText = new Text("Copyright 2022 Omar Fernando Moreno Benito.\nAll Rights Reserved. \n");
        copyrightText.setFill(Color.PURPLE);
        copyrightText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        copyrightText.setEffect(dropShadow);

        creditText = new Text("Digital Imaging, 3D Modeling, 3D Texture Mapping,\n by Omar Fernando Moreno Benito. \n");
        creditText.setFill(Color.BLUE);
        creditText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        creditText.setEffect(dropShadow);

        codeText = new Text("Game Design, User Interface Design, \nJava Programming by Omar Fernando Moreno Benito.");
        codeText.setFill(Color.BLUE);
        codeText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        codeText.setEffect(dropShadow);
    }
}
