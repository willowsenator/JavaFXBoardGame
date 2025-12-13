package com.willow.javafxboardgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Arrays;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Builds the complete scene graph from loaded assets.
 * Returns an immutable SceneComponents record.
 */
@SuppressFBWarnings(value = "FCBL_FIELD_COULD_BE_LOCAL",
        justification = "Fields will be used when game board quadrants are fully implemented")
public final class SceneBuilder {

    // Scene dimensions
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 640;
    private static final int BUTTON_WIDTH = 125;

    // Board configuration
    private static final int QUADRANT_COUNT = 4;
    private static final int SQUARES_PER_QUADRANT = 5;
    private static final int MAIN_BOARD_SIZE = 300;
    private static final int MAIN_BOARD_HEIGHT = 5;
    private static final int MAIN_BOARD_OFFSET = 225;
    private static final int SUB_BOARD_SIZE = 150;

    // Camera configuration
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 5000;

    // Logo positioning
    private static final double LOGO_SCALE = 0.8;
    private static final double LOGO_X_OFFSET = -75;
    private static final double LOGO_Y_OFFSET = -170;

    // Info overlay positioning
    private static final double INFO_X_OFFSET = 240;
    private static final double INFO_Y_OFFSET = 420;

    // UI container
    private static final int CONTAINER_SPACING = 10;
    private static final int CONTAINER_PADDING = 16;

    // Text styling
    private static final int FONT_SIZE = 40;

    // Light positioning
    private static final double LIGHT_Y_OFFSET = -25;

    private SceneBuilder() {
        // Static utility class
    }

    /**
     * Build the complete scene graph.
     *
     * @param assets loaded game assets
     * @return immutable record containing scene components
     */
    public static SceneComponents build(GameAssets assets) {
        var textAssets = createTextAssets(assets);
        var uiNodes = createUINodes(assets);
        var gameBoard = createGameBoardNodes(assets);

        addNodesToSceneGraph(uiNodes, gameBoard, textAssets);

        var scene = new Scene(uiNodes.root(), SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.BLACK);
        scene.setCamera(uiNodes.camera());

        return new SceneComponents(
                scene,
                uiNodes.uiLayout(),
                uiNodes.boardGameBackPlate(),
                uiNodes.logoLayer(),
                uiNodes.infoOverlay(),
                uiNodes.camera(),
                uiNodes.gameButton(),
                uiNodes.helpButton(),
                uiNodes.legalButton(),
                uiNodes.creditButton(),
                uiNodes.scoreButton(),
                textAssets.playText(),
                textAssets.moreText(),
                textAssets.helpText(),
                textAssets.cardText(),
                textAssets.copyrightText(),
                textAssets.creditText(),
                textAssets.codeText()
        );
    }

    private static TextAssets createTextAssets(GameAssets assets) {
        Text playText = createStyledText(
                "Press the PLAY GAME Button to Start!\n",
                Color.WHITE, FontPosture.REGULAR, assets);

        Text moreText = createStyledText(
                "Use other buttons for instructions,\ncopyrights, credits and scores.\n",
                Color.WHITE, FontPosture.ITALIC, assets);

        Text helpText = createStyledText(
                "To play game roll the dice, advance game piece\nand follow game board instruction.\n",
                Color.GREEN, FontPosture.REGULAR, assets);

        Text cardText = createStyledText(
                "If you land on square that requires you draw a card\n"
                        + "it will appear in the floating UI text area.\n",
                Color.GREEN, FontPosture.REGULAR, assets);

        Text copyrightText = createStyledText(
                "Copyright 2022 Omar Fernando Moreno Benito.\nAll Rights Reserved.\n",
                Color.PURPLE, FontPosture.REGULAR, assets);

        Text creditText = createStyledText(
                "Digital Imaging, 3D Modeling,\n3D Texture Mapping,\n",
                Color.BLUE, FontPosture.REGULAR, assets);

        Text codeText = createStyledText(
                "Game Design, User Interface Design\n Omar Fernando Moreno Benito.\n",
                Color.BLUE, FontPosture.REGULAR, assets);

        return new TextAssets(playText, moreText, helpText, cardText, copyrightText, creditText, codeText);
    }

    private static Text createStyledText(String content, Color color, FontPosture posture, GameAssets assets) {
        Text text = new Text(content);
        text.setFill(color);
        text.setFont(Font.font("Helvetica", posture, FONT_SIZE));
        text.setEffect(assets.dropShadow());
        return text;
    }

    private static UINodes createUINodes(GameAssets assets) {
        Group root = new Group();
        PerspectiveCamera camera = createCamera();
        StackPane uiLayout = createUILayout(assets);
        ImageView boardGameBackPlate = createBackPlate(assets);
        ImageView logoLayer = createLogoLayer(assets);
        TextFlow infoOverlay = createInfoOverlay();
        VBox uiContainer = createUIContainer();

        Button gameButton = createButton("Start Game");
        Button helpButton = createButton("Game Rules");
        Button legalButton = createButton("Disclaimers");
        Button creditButton = createButton("Game Credits");
        Button scoreButton = createButton("High Scores");

        return new UINodes(
                root, camera, uiLayout, boardGameBackPlate, logoLayer,
                infoOverlay, uiContainer,
                gameButton, helpButton, legalButton, creditButton, scoreButton
        );
    }

    private static PerspectiveCamera createCamera() {
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setTranslateZ(0);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        return camera;
    }

    private static StackPane createUILayout(GameAssets assets) {
        StackPane uiLayout = new StackPane();
        uiLayout.setPrefWidth(SCENE_WIDTH);
        uiLayout.setPrefHeight(SCENE_HEIGHT);
        uiLayout.setBackground(Background.EMPTY);
        uiLayout.setBackground(assets.uiBackground());
        return uiLayout;
    }

    private static ImageView createBackPlate(GameAssets assets) {
        ImageView backPlate = new ImageView();
        backPlate.setImage(assets.splashScreen());
        return backPlate;
    }

    private static ImageView createLogoLayer(GameAssets assets) {
        ImageView logoLayer = new ImageView();
        logoLayer.setImage(assets.alphaLogo());
        logoLayer.setScaleX(LOGO_SCALE);
        logoLayer.setScaleY(LOGO_SCALE);
        logoLayer.setTranslateX(LOGO_X_OFFSET);
        logoLayer.setTranslateY(LOGO_Y_OFFSET);
        return logoLayer;
    }

    private static TextFlow createInfoOverlay() {
        TextFlow infoOverlay = new TextFlow();
        infoOverlay.setTranslateX(INFO_X_OFFSET);
        infoOverlay.setTranslateY(INFO_Y_OFFSET);
        return infoOverlay;
    }

    private static VBox createUIContainer() {
        VBox uiContainer = new VBox(CONTAINER_SPACING);
        uiContainer.setAlignment(Pos.TOP_RIGHT);
        uiContainer.setPadding(new Insets(CONTAINER_PADDING));
        return uiContainer;
    }

    private static Button createButton(String text) {
        Button button = new Button();
        button.setText(text);
        button.setMaxWidth(BUTTON_WIDTH);
        return button;
    }

    private static GameBoardNodes createGameBoardNodes(GameAssets assets) {
        Group gameBoard = new Group();
        Group[] quadrants = new Group[QUADRANT_COUNT];
        Arrays.setAll(quadrants, _ -> new Group());

        Box[] mainBoards = createMainBoards();
        Box[] quadrant1Squares = createSubBoards(assets);

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateY(LIGHT_Y_OFFSET);
        light.getScope().add(quadrant1Squares[0]);

        return new GameBoardNodes(gameBoard, quadrants, mainBoards, quadrant1Squares, light);
    }

    private static Box[] createMainBoards() {
        Box[] mainBoards = new Box[QUADRANT_COUNT];
        mainBoards[0] = new Box(MAIN_BOARD_SIZE, MAIN_BOARD_HEIGHT, MAIN_BOARD_SIZE);
        mainBoards[0].setTranslateX(MAIN_BOARD_OFFSET);
        mainBoards[0].setTranslateZ(MAIN_BOARD_OFFSET);

        // Hide additional boards (for future implementation)
        for (int i = 1; i < mainBoards.length; i++) {
            mainBoards[i] = new Box(MAIN_BOARD_SIZE, MAIN_BOARD_HEIGHT, MAIN_BOARD_SIZE);
            mainBoards[i].setVisible(false);
        }
        return mainBoards;
    }

    private static Box[] createSubBoards(GameAssets assets) {
        int[] xTranslations = {MAIN_BOARD_SIZE, SUB_BOARD_SIZE, 0, 0, 0};
        int[] zTranslations = {0, 0, 0, SUB_BOARD_SIZE, MAIN_BOARD_SIZE};

        Box[] squares = new Box[SQUARES_PER_QUADRANT];
        Arrays.setAll(squares, i -> {
            var box = new Box(SUB_BOARD_SIZE, MAIN_BOARD_HEIGHT, SUB_BOARD_SIZE);
            box.setMaterial(assets.shaders()[i]);
            box.setTranslateX(xTranslations[i]);
            box.setTranslateZ(zTranslations[i]);
            return box;
        });
        return squares;
    }

    private static void addNodesToSceneGraph(UINodes ui, GameBoardNodes board, TextAssets text) {
        ui.root().getChildren().addAll(board.gameBoard(), ui.uiLayout());

        Arrays.stream(board.quadrants()).forEach(board.gameBoard().getChildren()::add);
        for (int i = 0; i < board.quadrants().length; i++) {
            board.quadrants()[i].getChildren().add(board.mainBoards()[i]);
        }
        board.quadrants()[0].getChildren().addAll(board.quadrant1Squares());

        ui.uiLayout().getChildren().addAll(
                ui.logoLayer(), ui.boardGameBackPlate(), ui.infoOverlay(), ui.uiContainer());
        ui.uiContainer().getChildren().addAll(
                ui.gameButton(), ui.helpButton(), ui.legalButton(), ui.creditButton(), ui.scoreButton());
        ui.infoOverlay().getChildren().addAll(text.playText(), text.moreText());
    }

    /**
     * Internal record for text assets during building.
     */
    private record TextAssets(
            Text playText,
            Text moreText,
            Text helpText,
            Text cardText,
            Text copyrightText,
            Text creditText,
            Text codeText
    ) { }

    /**
     * Internal record for UI nodes during building.
     */
    private record UINodes(
            Group root,
            PerspectiveCamera camera,
            StackPane uiLayout,
            ImageView boardGameBackPlate,
            ImageView logoLayer,
            TextFlow infoOverlay,
            VBox uiContainer,
            Button gameButton,
            Button helpButton,
            Button legalButton,
            Button creditButton,
            Button scoreButton
    ) { }

    /**
     * Internal record for game board nodes during building.
     */
    private record GameBoardNodes(
            Group gameBoard,
            Group[] quadrants,
            Box[] mainBoards,
            Box[] quadrant1Squares,
            PointLight light
    ) { }
}
