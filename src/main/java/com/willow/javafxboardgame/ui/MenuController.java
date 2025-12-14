package com.willow.javafxboardgame.ui;

import com.willow.javafxboardgame.input.InputController;
import com.willow.javafxboardgame.model.GameState;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.function.Consumer;

/**
 * Handles menu button events and screen transitions.
 * Decoupled from UIBoardGame via functional callback for state changes.
 */
public final class MenuController {

    // Camera positioning for game start
    private static final double CAMERA_Y_POSITION = -500;
    private static final double CAMERA_X_POSITION = -500;
    private static final double CAMERA_ROTATION = -45;

    // Info overlay positions for different screens
    private static final double INSTRUCTIONS_X = 130;
    private static final double INSTRUCTIONS_Y = 400;
    private static final double COPYRIGHTS_X = 200;
    private static final double COPYRIGHTS_Y = 430;
    private static final double CREDITS_X = 240;
    private static final double CREDITS_Y = 420;

    // Color adjustment hues for different screens
    private static final double HUE_INSTRUCTIONS = 0.4;
    private static final double HUE_COPYRIGHTS = -0.4;
    private static final double HUE_CREDITS = -0.9;

    private final SceneComponents components;
    private final GameAssets assets;
    private final Consumer<GameState> stateChanger;

    /**
     * Create a menu controller and wire up all button handlers.
     *
     * @param components scene components containing buttons and UI elements
     * @param assets game assets for screen backgrounds
     * @param inputController input controller for keyboard events
     * @param stateChanger callback to change game state
     */
    public MenuController(
            SceneComponents components,
            GameAssets assets,
            InputController inputController,
            Consumer<GameState> stateChanger) {

        this.components = components;
        this.assets = assets;
        this.stateChanger = stateChanger;

        wireEvents(inputController);
    }

    private void wireEvents(InputController inputController) {
        components.gameButton().setOnAction(_ -> showStartScreen());
        components.helpButton().setOnAction(_ -> showInstructions());
        components.legalButton().setOnAction(_ -> showCopyrights());
        components.creditButton().setOnAction(_ -> showCredits());
        components.scoreButton().setOnAction(_ -> System.out.println("High Scores"));

        components.scene().setOnKeyPressed(inputController.getKeyPressedHandler());
        components.scene().setOnKeyReleased(inputController.getKeyReleasedHandler());
    }

    private void showStartScreen() {
        stateChanger.accept(new GameState.Playing(0, 1));
        var camera = components.camera();
        camera.setTranslateZ(0);
        camera.setTranslateY(CAMERA_Y_POSITION);
        camera.setTranslateX(CAMERA_X_POSITION);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(CAMERA_ROTATION);
    }

    private void showInstructions() {
        components.infoOverlay().getChildren().clear();
        components.infoOverlay().getChildren().addAll(
                components.helpText(), components.cardText());
        components.infoOverlay().setTranslateX(INSTRUCTIONS_X);
        components.infoOverlay().setTranslateY(INSTRUCTIONS_Y);
        setWhiteBackground();
        components.boardGameBackPlate().setImage(assets.helpLayer());
        components.logoLayer().setEffect(assets.colorAdjust());
        assets.colorAdjust().setHue(HUE_INSTRUCTIONS);
    }

    private void showCopyrights() {
        components.infoOverlay().getChildren().clear();
        components.infoOverlay().getChildren().addAll(components.copyrightText());
        components.infoOverlay().setTranslateX(COPYRIGHTS_X);
        components.infoOverlay().setTranslateY(COPYRIGHTS_Y);
        setWhiteBackground();
        components.boardGameBackPlate().setImage(assets.legalLayer());
        components.logoLayer().setEffect(assets.colorAdjust());
        assets.colorAdjust().setHue(HUE_COPYRIGHTS);
    }

    private void showCredits() {
        components.infoOverlay().getChildren().clear();
        components.infoOverlay().getChildren().addAll(
                components.creditText(), components.codeText());
        components.infoOverlay().setTranslateX(CREDITS_X);
        components.infoOverlay().setTranslateY(CREDITS_Y);
        setWhiteBackground();
        components.boardGameBackPlate().setImage(assets.creditLayer());
        components.logoLayer().setEffect(assets.colorAdjust());
        assets.colorAdjust().setHue(HUE_CREDITS);
    }

    private void setWhiteBackground() {
        components.uiLayout().setBackground(
                new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
