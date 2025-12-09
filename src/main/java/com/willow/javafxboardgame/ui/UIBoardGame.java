package com.willow.javafxboardgame.ui;


import com.willow.javafxboardgame.helper.GameControllerHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
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
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;

import java.util.Arrays;
import java.util.Objects;

public class UIBoardGame {

    private static Background uiBackground;
    private static Group root;
    private static Group gameBoard;
    private static final Group[] quadrants = new Group[4];
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

    private static Image diffuseMap;
    private static Image specularMap;
    private static Image glowMap;
    private static Image bumpMap;

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

    private static PointLight light;
    private static final PhongMaterial[] shaders = new PhongMaterial[20];

    private static final Box[] mainBoards = new Box[4];
    private static final Box[] quadrant1Squares = new Box[5];
    private static final Box[] quadrant2Squares = new Box[5];
    private static final Box[] quadrant3Squares = new Box[5];
    private static final Box[] quadrant4Squares = new Box[5];

    public static Scene init() {
        createSpecialEffects();
        loadImageAssets();
        createMaterials();
        createTextAssets();
        createBoardGameNodes();
        createGameBoardNodes();
        addNodesToSceneGraph();
        addEvents();

        return scene;
    }

    private static void createSpecialEffects() {
        dropShadow = new DropShadow();
        dropShadow.setRadius(0.3);
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
        camera.setTranslateZ(0);
        camera.setTranslateY(-500);
        camera.setTranslateX(-500);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(-45);
        camera.setFieldOfView(1);
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
        Arrays.stream(quadrants).forEach(gameBoard.getChildren()::add);
        Arrays.stream(quadrants).forEach(group -> group.getChildren().add(mainBoards[Arrays.asList(quadrants).indexOf(group)]));
        quadrants[0].getChildren().addAll(quadrant1Squares);
        uiLayout.getChildren().addAll(logoLayer, boardGameBackPlate, infoOverlay, uiContainer);
        uiContainer.getChildren().addAll(gameButton, helpButton, legalButton, creditButton, scoreButton);
        infoOverlay.getChildren().addAll(playText, moreText);
    }

    private static void createGameBoardNodes() {
        createMainBoard();
        createSubBoards();
        hideAdditionalBoards();
    }

    private static void createMainBoard() {
        mainBoards[0] = new Box(300, 5, 300);
        mainBoards[0].setTranslateX(225);
        mainBoards[0].setTranslateZ(225);
    }

    private static void createSubBoards() {
        Arrays.setAll(quadrant1Squares, i -> {
            var box = new Box(150, 5, 150);
            box.setMaterial(shaders[i]);
            return box;
        });

        quadrant1Squares[0].setTranslateX(300);
        quadrant1Squares[1].setTranslateX(150);
        quadrant1Squares[3].setTranslateZ(150);
        quadrant1Squares[4].setTranslateZ(300);
    }

    private static void hideAdditionalBoards() {
        for (var i = 1; i < mainBoards.length; i++) {
            mainBoards[i] = new Box(300, 5, 300);
            mainBoards[i].setVisible(false);
        }
    }

    private static void createBoardGameNodes() {
        root = new Group();
        gameBoard = new Group();
        Arrays.setAll(quadrants, i -> new Group());
        camera = new PerspectiveCamera();
        camera.setTranslateZ(0);
        camera.setNearClip(0.1);
        camera.setFarClip(5000);
        scene = new Scene(root, 1280, 640);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
        light = new PointLight(Color.WHITE);
        light.setTranslateY(-25);
        light.getScope().add(quadrant1Squares[0]);
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
        diffuseMap = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/gameboardsquare.png")).toString(),
                256, 256, true, true, true);
        specularMap = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/gameboard3grayscale256px.png")).toString(),
                256, 256, true, true, true);
        glowMap = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/gameboard2grayscale256px.png")).toString(),
                256, 256, true, true, true);
        bumpMap = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/gameboard3grayscale256px.png")).toString(),
                256, 256, true, true, true);
        alphaLogo = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/alphalogo.png")).toString(), true);

        var uiBackgroundImage = new BackgroundImage(backPlate, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        uiBackground = new Background(uiBackgroundImage);
    }

    private static void createMaterials() {
        Arrays.setAll(shaders, i -> {
            var material = new PhongMaterial(Color.WHITE);
            material.setDiffuseMap(diffuseMap);
            return material;
        });
    }

    private static void createTextAssets() {
        playText = new Text("""
                Press the PLAY GAME Button to Start!
                """);
        playText.setFill(Color.WHITE);
        playText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        playText.setEffect(dropShadow);

        moreText = new Text("""
                Use other buttons for instructions,
                copyrights, credits and scores.
                """);
        moreText.setFill(Color.WHITE);
        moreText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
        moreText.setEffect(dropShadow);

        helpText = new Text("""
                To play game roll the dice, advance game piece
                and follow game board instruction.
                """);
        helpText.setFill(Color.GREEN);
        helpText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        helpText.setEffect(dropShadow);

        cardText = new Text("""
                If you land on square that requires you draw a card it will
                appear in the floating UI text area.
                """);
        cardText.setFill(Color.GREEN);
        cardText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        cardText.setEffect(dropShadow);

        copyrightText = new Text("""
                Copyright 2022 Omar Fernando Moreno Benito.
                All Rights Reserved.
                """);
        copyrightText.setFill(Color.PURPLE);
        copyrightText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        copyrightText.setEffect(dropShadow);

        creditText = new Text("""
                Digital Imaging, 3D Modeling, 3D Texture Mapping,
                by Omar Fernando Moreno Benito.
                """);
        creditText.setFill(Color.BLUE);
        creditText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        creditText.setEffect(dropShadow);

        codeText = new Text("""
                Game Design, User Interface Design,
                Java Programming by Omar Fernando Moreno Benito.
                """);
        codeText.setFill(Color.BLUE);
        codeText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
        codeText.setEffect(dropShadow);
    }
}
