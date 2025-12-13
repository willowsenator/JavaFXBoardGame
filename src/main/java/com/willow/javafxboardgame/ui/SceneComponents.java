package com.willow.javafxboardgame.ui;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
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
        value = "EI_EXPOSE_REP",
        justification = "Record provides access to scene components for event wiring; JavaFX nodes are mutable")
public record SceneComponents(
        Scene scene,
        StackPane uiLayout,
        ImageView boardGameBackPlate,
        ImageView logoLayer,
        TextFlow infoOverlay,
        PerspectiveCamera camera,
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
