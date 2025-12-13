package com.willow.javafxboardgame.ui;


import com.willow.javafxboardgame.input.InputController;
import com.willow.javafxboardgame.input.KeyboardController;
import com.willow.javafxboardgame.model.GameState;
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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "FCBL_FIELD_COULD_BE_LOCAL",
        justification = "Fields will be used when game board quadrants are implemented")
public class UIBoardGame {

    private Background uiBackground;
    private final Group root;
    private Group gameBoard;
    private final Group[] quadrants = new Group[4];
    private final Scene scene;
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

    private Image diffuseMap;
    private Image specularMap;
    private Image glowMap;
    private Image bumpMap;

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
    private DropShadow dropShadow;
    private ColorAdjust colorAdjust;

    private PerspectiveCamera camera;

    private PointLight light;
    private final PhongMaterial[] shaders = new PhongMaterial[20];
    private final InputController inputController = new KeyboardController();
    private GameState gameState = new GameState.Menu();

    private final Box[] mainBoards = new Box[4];
    private final Box[] quadrant1Squares = new Box[5];
    private final Box[] quadrant2Squares = new Box[5];
    private final Box[] quadrant3Squares = new Box[5];
    private final Box[] quadrant4Squares = new Box[5];

    public UIBoardGame() {
        createSpecialEffects();
        loadImageAssets();
        createMaterials();
        createTextAssets();
        this.root = new Group();
        createBoardGameNodes();
        createGameBoardNodes();
        addNodesToSceneGraph();
        this.scene = new Scene(root, 1280, 640);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
        addEvents();
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Scene must be exposed for JavaFX Stage")
    public Scene getScene() {
        return scene;
    }

    private void createSpecialEffects() {
        dropShadow = new DropShadow();
        dropShadow.setRadius(0.3);
        dropShadow.setRadius(0.3);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        dropShadow.setColor(Color.DARKGRAY);

        colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.4);
    }

    private void addEvents() {
        gameButton.setOnAction(_ -> showStartScreen());
        helpButton.setOnAction(_ -> showInstructions());
        legalButton.setOnAction(_ -> showCopyrights());
        creditButton.setOnAction(_ -> showCredits());
        scoreButton.setOnAction(_ -> System.out.println("High Scores"));

        scene.setOnKeyPressed(inputController.getKeyPressedHandler());
        scene.setOnKeyReleased(inputController.getKeyReleasedHandler());
    }

    /**
     * Get the input controller for game logic to query input state.
     */
    public InputController getInputController() {
        return inputController;
    }

    /**
     * Get current game state.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Transition to a new game state using Java 24 pattern matching.
     */
    @SuppressFBWarnings(value = "ITC_INHERITANCE_TYPE_CHECKING",
            justification = "Java 24 exhaustive pattern matching on sealed interface")
    public void setGameState(GameState newState) {
        this.gameState = switch (newState) {
            case GameState.Menu _ -> {
                uiLayout.setVisible(true);
                yield newState;
            }
            case GameState.Playing _ -> {
                uiLayout.setVisible(false);
                yield newState;
            }
            case GameState.Paused _, GameState.GameOver _ -> newState;
        };
    }

    private void showCredits() {
        infoOverlay.getChildren().clear();
        infoOverlay.getChildren().addAll(creditText, codeText);
        infoOverlay.setTranslateX(240);
        infoOverlay.setTranslateY(420);
        uiLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        boardGameBackPlate.setImage(creditLayer);
        logoLayer.setEffect(colorAdjust);
        colorAdjust.setHue(-0.9);
    }

    private void showCopyrights() {
        infoOverlay.getChildren().clear();
        infoOverlay.getChildren().addAll(copyrightText);
        infoOverlay.setTranslateX(200);
        infoOverlay.setTranslateY(430);
        uiLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        boardGameBackPlate.setImage(legalLayer);
        logoLayer.setEffect(colorAdjust);
        colorAdjust.setHue(-0.4);
    }

    private void showStartScreen() {
        setGameState(new GameState.Playing(0, 1));
        camera.setTranslateZ(0);
        camera.setTranslateY(-500);
        camera.setTranslateX(-500);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(-45);
        camera.setFieldOfView(1);
    }

    private void showInstructions() {
        infoOverlay.getChildren().clear();
        infoOverlay.getChildren().addAll(helpText, cardText);
        infoOverlay.setTranslateX(130);
        infoOverlay.setTranslateY(400);
        uiLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        boardGameBackPlate.setImage(helpLayer);
        logoLayer.setEffect(colorAdjust);
        colorAdjust.setHue(0.4);
    }

    private void addNodesToSceneGraph() {
        root.getChildren().addAll(gameBoard, uiLayout);
        Arrays.stream(quadrants).forEach(gameBoard.getChildren()::add);
        Arrays.stream(quadrants).forEach(group ->
                group.getChildren().add(mainBoards[Arrays.asList(quadrants).indexOf(group)]));
        quadrants[0].getChildren().addAll(quadrant1Squares);
        uiLayout.getChildren().addAll(logoLayer, boardGameBackPlate, infoOverlay, uiContainer);
        uiContainer.getChildren().addAll(gameButton, helpButton, legalButton, creditButton, scoreButton);
        infoOverlay.getChildren().addAll(playText, moreText);
    }

    private void createGameBoardNodes() {
        createMainBoard();
        createSubBoards();
        hideAdditionalBoards();
    }

    private void createMainBoard() {
        mainBoards[0] = new Box(300, 5, 300);
        mainBoards[0].setTranslateX(225);
        mainBoards[0].setTranslateZ(225);
    }

    private void createSubBoards() {
        // Board square positions: X translations for indices 0,1 and Z translations for indices 3,4
        int[] xTranslations = {300, 150, 0, 0, 0};
        int[] zTranslations = {0, 0, 0, 150, 300};

        Arrays.setAll(quadrant1Squares, i -> {
            var box = new Box(150, 5, 150);
            box.setMaterial(shaders[i]);
            box.setTranslateX(xTranslations[i]);
            box.setTranslateZ(zTranslations[i]);
            return box;
        });
    }

    private void hideAdditionalBoards() {
        for (var i = 1; i < mainBoards.length; i++) {
            mainBoards[i] = new Box(300, 5, 300);
            mainBoards[i].setVisible(false);
        }
    }

    private void createBoardGameNodes() {
        gameBoard = new Group();
        Arrays.setAll(quadrants, i -> new Group());
        camera = new PerspectiveCamera();
        camera.setTranslateZ(0);
        camera.setNearClip(0.1);
        camera.setFarClip(5000);
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

    private void loadImageAssets() {

        Image backPlate = new Image(Objects.requireNonNull(UIBoardGame.class.getResource("/images/backplate.png"))
                .toString(), 1280, 640, true, false, true);
        splashScreen = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/welcome.png")).toString(), true);
        helpLayer = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/instructions.png")).toString(), true);
        legalLayer = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/copyrights.png")).toString(), true);
        creditLayer = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/credits.png")).toString(), true);
        scoreLayer = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/high-scores.png")).toString(), true);
        diffuseMap = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/gameboardsquare.png")).toString(),
                256, 256, true, true, true);
        specularMap = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/gameboard3grayscale256px.png")).toString(),
                256, 256, true, true, true);
        glowMap = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/gameboard2grayscale256px.png")).toString(),
                256, 256, true, true, true);
        bumpMap = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/gameboard3grayscale256px.png")).toString(),
                256, 256, true, true, true);
        alphaLogo = new Image(Objects.requireNonNull(
                UIBoardGame.class.getResource("/images/alphalogo.png")).toString(), true);

        var uiBackgroundImage = new BackgroundImage(backPlate, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        uiBackground = new Background(uiBackgroundImage);
    }

    private void createMaterials() {
        Arrays.setAll(shaders, i -> {
            var material = new PhongMaterial(Color.WHITE);
            material.setDiffuseMap(diffuseMap);
            return material;
        });
    }

    private void createTextAssets() {
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
