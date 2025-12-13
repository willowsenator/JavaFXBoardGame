package com.willow.javafxboardgame.ui;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.paint.PhongMaterial;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Immutable record containing all loaded game assets.
 * Created by AssetLoader.load().
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP",
        justification = "Record provides read-only access to shared assets; arrays are internal implementation detail")
public record GameAssets(
        Background uiBackground,
        Image splashScreen,
        Image helpLayer,
        Image legalLayer,
        Image creditLayer,
        Image scoreLayer,
        Image alphaLogo,
        PhongMaterial[] shaders,
        DropShadow dropShadow,
        ColorAdjust colorAdjust
) { }
