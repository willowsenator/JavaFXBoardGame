package com.willow.javafxboardgame.ui;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Immutable record containing all scene graph components.
 * Created by SceneBuilder.build().
 */
@SuppressFBWarnings(
        value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
        justification = "Record provides scene components for event wiring; JavaFX nodes are mutable")
public record SceneComponents(
        Scene scene,
        SubScene boardSubScene,
        Group gameBoard,
        PerspectiveCamera boardCamera,
        StackPane uiLayout,
        ImageView boardGameBackPlate,
        ImageView logoLayer,
        TextFlow infoOverlay,
        Button gameButton,
        Button helpButton,
        Button legalButton,
        Button creditButton,
        Button scoreButton,
        Text playText,
        Text moreText,
        Text helpText,
        Text cardText,
        Text copyrightText,
        Text creditText,
        Text codeText
) { }
