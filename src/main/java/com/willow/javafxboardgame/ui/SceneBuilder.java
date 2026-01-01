package com.willow.javafxboardgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.util.stream.IntStream;
/**
 * Builds the complete scene graph from loaded assets.
 * Returns an immutable SceneComponents record.
 */
public final class SceneBuilder {

    // Scene dimensions
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 640;
    private static final int BUTTON_WIDTH = 125;

    // Board configuration - 8 main boards with subboards between them
    private static final int MAIN_BOARD_COUNT = 8;
    private static final int SUB_BOARDS_PER_SEGMENT = 2;  // 2 per segment × 2 segments per edge = 4 per edge
    private static final int MAIN_BOARD_SIZE = 100;
    private static final int BOARD_HEIGHT = 10;
    private static final int SUB_BOARD_SIZE = 100;

    // Board layout - 8 main boards arranged in a square pattern
    // Corner offset = main_board/2 + 2 subboards + main_board/2 = 50 + 200 + 50 = 300
    private static final int CORNER_OFFSET = 300;
    private static final int EDGE_OFFSET = 300;

    // Center board (white area in the middle)
    private static final int CENTER_SIZE = 600;

    // Camera configuration
    private static final double CAMERA_NEAR_CLIP = 1;
    private static final double CAMERA_FAR_CLIP = 10000;

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

    // 3D SubScene configuration (perspective camera for board - full window size)
    private static final int SUBSCENE_WIDTH = SCENE_WIDTH;
    private static final int SUBSCENE_HEIGHT = SCENE_HEIGHT;
    private static final double CAMERA_FIELD_OF_VIEW = 45;

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
        var ctx = createBuildContext(assets);
        addNodesToSceneGraph(ctx.uiNodes(), ctx.textAssets(), ctx.boardSubScene());

        var scene = new Scene(ctx.uiNodes().root(), SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.BLACK);

        return createSceneComponents(scene, ctx);
    }

    private static BuildContext createBuildContext(GameAssets assets) {
        var textAssets = createTextAssets(assets);
        var uiNodes = createUINodes(assets);
        var gameBoard = createGameBoardNodes(assets);
        var boardCamera = createBoardCamera();
        var boardSubScene = createBoardSubScene(gameBoard, boardCamera);
        return new BuildContext(textAssets, uiNodes, gameBoard, boardCamera, boardSubScene);
    }

    private static SceneComponents createSceneComponents(Scene scene, BuildContext ctx) {
        return new SceneComponents(
                scene, ctx.boardSubScene(), ctx.gameBoard().gameBoard(), ctx.boardCamera(),
                ctx.uiNodes().uiLayout(), ctx.uiNodes().boardGameBackPlate(),
                ctx.uiNodes().logoLayer(), ctx.uiNodes().infoOverlay(),
                ctx.uiNodes().gameButton(), ctx.uiNodes().helpButton(),
                ctx.uiNodes().legalButton(), ctx.uiNodes().creditButton(),
                ctx.uiNodes().scoreButton(), ctx.textAssets().playText(),
                ctx.textAssets().moreText(), ctx.textAssets().helpText(),
                ctx.textAssets().cardText(), ctx.textAssets().copyrightText(),
                ctx.textAssets().creditText(), ctx.textAssets().codeText());
    }

    private record BuildContext(
            TextAssets textAssets, UINodes uiNodes, GameBoardNodes gameBoard,
            PerspectiveCamera boardCamera, SubScene boardSubScene) { }

    private static TextAssets createTextAssets(GameAssets assets) {
        Text playText = createStyledText(
                "Press the PLAY GAME Button to Start!\n",
                Color.WHITE, FontPosture.REGULAR, assets);

        Text moreText = createStyledText(
                "Use other buttons for instructions,\ncopyrights, credits and scores.\n",
                Color.WHITE, FontPosture.ITALIC, assets);

        Text helpText = createStyledText(
                "To play the game roll the dice, advance game piece\nand follow game board instruction.\n",
                Color.GREEN, FontPosture.REGULAR, assets);

        Text cardText = createStyledText(
                """
                        If you land on a square that requires you to draw a card
                        it will appear in the floating UI text area.
                        """,
                Color.GREEN, FontPosture.REGULAR, assets);

        Text copyrightText = createStyledText(
                "Copyright 2022 Omar Fernando Moreno Benito.\nAll Rights Reserved.\n",
                Color.PURPLE, FontPosture.REGULAR, assets);

        Text creditText = createStyledText(
                "Digital Imaging, 3D Modeling,\n3D Texture Mapping,\n",
                Color.BLUE, FontPosture.REGULAR, assets);

        Text codeText = createStyledText(
                "Game Design, User Interface Design,\nJava Programming by Omar Fernando Moreno Benito.",
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
                root, uiLayout, boardGameBackPlate, logoLayer,
                infoOverlay, uiContainer,
                gameButton, helpButton, legalButton, creditButton, scoreButton
        );
    }

    private static PerspectiveCamera createBoardCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setFieldOfView(CAMERA_FIELD_OF_VIEW);
        return camera;
    }

    private static SubScene createBoardSubScene(GameBoardNodes gameBoard, PerspectiveCamera camera) {
        // Add all board elements to the group
        gameBoard.gameBoard().getChildren().add(gameBoard.light());
        gameBoard.gameBoard().getChildren().add(gameBoard.centerBoard());
        gameBoard.gameBoard().getChildren().addAll(gameBoard.mainBoards());
        gameBoard.subBoards().forEach(segment ->
                gameBoard.gameBoard().getChildren().addAll(segment));

        SubScene subScene = new SubScene(
                gameBoard.gameBoard(), SUBSCENE_WIDTH, SUBSCENE_HEIGHT,
                true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.TRANSPARENT);
        subScene.setCamera(camera);
        return subScene;
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

        List<Box> mainBoards = createMainBoards(assets);
        List<List<Box>> subBoards = createAllSubBoards(assets);
        Box centerBoard = createCenterBoard();

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateY(LIGHT_Y_OFFSET);
        // Add all boards to light scope
        mainBoards.forEach(light.getScope()::add);
        subBoards.stream().flatMap(List::stream).forEach(light.getScope()::add);
        light.getScope().add(centerBoard);

        return new GameBoardNodes(gameBoard, mainBoards, subBoards, centerBoard, light);
    }

    private static List<Box> createMainBoards(GameAssets assets) {
        // 8 main boards: 4 corners + 4 edge midpoints
        // Clockwise from top-right: 0=TR, 1=R, 2=BR, 3=B, 4=BL, 5=L, 6=TL, 7=T
        int[] xPositions = {
            CORNER_OFFSET, EDGE_OFFSET, CORNER_OFFSET, 0,
            -CORNER_OFFSET, -EDGE_OFFSET, -CORNER_OFFSET, 0
        };
        int[] zPositions = {
            CORNER_OFFSET, 0, -CORNER_OFFSET, -EDGE_OFFSET,
            -CORNER_OFFSET, 0, CORNER_OFFSET, EDGE_OFFSET
        };

        return IntStream.range(0, MAIN_BOARD_COUNT)
                .mapToObj(i -> {
                    var box = new Box(MAIN_BOARD_SIZE, BOARD_HEIGHT, MAIN_BOARD_SIZE);
                    box.setMaterial(assets.shaders().get(i % assets.shaders().size()));
                    box.setTranslateX(xPositions[i]);
                    box.setTranslateZ(zPositions[i]);
                    return box;
                })
                .toList();
    }

    private static List<List<Box>> createAllSubBoards(GameAssets assets) {
        // 8 segments of subboards connecting adjacent main boards
        List<SegmentConfig> configs = List.of(
            new SegmentConfig(CORNER_OFFSET, CORNER_OFFSET, 0, -1),     // TR to R
            new SegmentConfig(CORNER_OFFSET, 0, 0, -1),                 // R to BR
            new SegmentConfig(CORNER_OFFSET, -CORNER_OFFSET, -1, 0),   // BR to B
            new SegmentConfig(0, -CORNER_OFFSET, -1, 0),                // B to BL
            new SegmentConfig(-CORNER_OFFSET, -CORNER_OFFSET, 0, 1),   // BL to L
            new SegmentConfig(-CORNER_OFFSET, 0, 0, 1),                 // L to TL
            new SegmentConfig(-CORNER_OFFSET, CORNER_OFFSET, 1, 0),    // TL to T
            new SegmentConfig(0, CORNER_OFFSET, 1, 0)                   // T to TR
        );

        return IntStream.range(0, MAIN_BOARD_COUNT)
                .mapToObj(i -> createSubBoardSegment(assets, configs.get(i), i))
                .toList();
    }

    private static List<Box> createSubBoardSegment(GameAssets assets, SegmentConfig config, int segmentIdx) {
        return IntStream.range(0, SUB_BOARDS_PER_SEGMENT)
                .mapToObj(i -> {
                    var box = new Box(SUB_BOARD_SIZE, BOARD_HEIGHT, SUB_BOARD_SIZE);
                    int shaderIndex = (segmentIdx * SUB_BOARDS_PER_SEGMENT + i) % assets.shaders().size();
                    box.setMaterial(assets.shaders().get(shaderIndex));
                    int offsetX = (i + 1) * SUB_BOARD_SIZE * config.dirX();
                    int offsetZ = (i + 1) * SUB_BOARD_SIZE * config.dirZ();
                    box.setTranslateX(config.startX() + offsetX);
                    box.setTranslateZ(config.startZ() + offsetZ);
                    return box;
                })
                .toList();
    }

    private record SegmentConfig(int startX, int startZ, int dirX, int dirZ) { }

    private static Box createCenterBoard() {
        var center = new Box(CENTER_SIZE, BOARD_HEIGHT, CENTER_SIZE);
        var whiteMaterial = new PhongMaterial(Color.WHITE);
        center.setMaterial(whiteMaterial);
        return center;
    }

    private static void addNodesToSceneGraph(UINodes ui, TextAssets text, SubScene boardSubScene) {
        // Add SubScene (3D board) first, then UI overlay on top
        ui.root().getChildren().addAll(boardSubScene, ui.uiLayout());

        ui.uiLayout().getChildren().addAll(
                ui.logoLayer(), ui.boardGameBackPlate(), ui.infoOverlay(), ui.uiContainer());
        ui.uiContainer().getChildren().addAll(
                ui.gameButton(), ui.helpButton(), ui.legalButton(), ui.creditButton(),
                ui.scoreButton());
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
            List<Box> mainBoards,
            List<List<Box>> subBoards,
            Box centerBoard,
            PointLight light
    ) { }
}
