package com.willow.javafxboardgame.ui;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Loads and manages all image and material assets for the game.
 * Returns an immutable GameAssets record containing all loaded resources.
 */
public final class AssetLoader {

    private static final int SHADER_COUNT = 20;
    private static final int BACKPLATE_WIDTH = 1280;
    private static final int BACKPLATE_HEIGHT = 640;
    private static final int TEXTURE_SIZE = 256;
    private static final double SHADOW_RADIUS = 0.3;
    private static final double SHADOW_OFFSET = 3;
    private static final double DEFAULT_HUE = 0.4;

    private AssetLoader() {
        // Static utility class
    }

    /**
     * Load all game assets.
     *
     * @return immutable record containing all loaded assets
     */
    public static GameAssets load() {
        var effects = createSpecialEffects();
        var images = loadImageAssets();
        var shaders = createMaterials(images.diffuseMap());

        return new GameAssets(
                images.uiBackground(),
                images.splashScreen(),
                images.helpLayer(),
                images.legalLayer(),
                images.creditLayer(),
                images.scoreLayer(),
                images.alphaLogo(),
                shaders,
                effects.dropShadow(),
                effects.colorAdjust()
        );
    }

    private static Effects createSpecialEffects() {
        var dropShadow = new DropShadow();
        dropShadow.setRadius(SHADOW_RADIUS);
        dropShadow.setOffsetX(SHADOW_OFFSET);
        dropShadow.setOffsetY(SHADOW_OFFSET);
        dropShadow.setColor(Color.DARKGRAY);

        var colorAdjust = new ColorAdjust();
        colorAdjust.setHue(DEFAULT_HUE);

        return new Effects(dropShadow, colorAdjust);
    }

    private static ImageAssets loadImageAssets() {
        Image backPlate = loadBackPlate();
        Image splashScreen = loadImage("/images/welcome.png");
        Image helpLayer = loadImage("/images/instructions.png");
        Image legalLayer = loadImage("/images/copyrights.png");
        Image creditLayer = loadImage("/images/credits.png");
        Image scoreLayer = loadImage("/images/high-scores.png");
        Image diffuseMap = loadTexture("/images/gameboardsquare.png");
        Image alphaLogo = loadImage("/images/alphalogo.png");

        var uiBackgroundImage = new BackgroundImage(
                backPlate,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        Background uiBackground = new Background(uiBackgroundImage);

        return new ImageAssets(
                uiBackground, splashScreen, helpLayer, legalLayer,
                creditLayer, scoreLayer, alphaLogo, diffuseMap
        );
    }

    private static Image loadBackPlate() {
        return new Image(
                Objects.requireNonNull(AssetLoader.class.getResource("/images/backplate.png")).toString(),
                BACKPLATE_WIDTH, BACKPLATE_HEIGHT, true, false, true);
    }

    private static Image loadImage(String path) {
        return new Image(
                Objects.requireNonNull(AssetLoader.class.getResource(path)).toString(), true);
    }

    private static Image loadTexture(String path) {
        return new Image(
                Objects.requireNonNull(AssetLoader.class.getResource(path)).toString(),
                TEXTURE_SIZE, TEXTURE_SIZE, true, true, true);
    }

    private static List<PhongMaterial> createMaterials(Image diffuseMap) {
        return IntStream.range(0, SHADER_COUNT)
                .mapToObj(_ -> {
                    var material = new PhongMaterial(Color.WHITE);
                    material.setDiffuseMap(diffuseMap);
                    return material;
                })
                .toList();
    }

    /**
     * Internal record for effects during loading.
     */
    private record Effects(DropShadow dropShadow, ColorAdjust colorAdjust) { }

    /**
     * Internal record for image assets during loading.
     */
    private record ImageAssets(
            Background uiBackground,
            Image splashScreen,
            Image helpLayer,
            Image legalLayer,
            Image creditLayer,
            Image scoreLayer,
            Image alphaLogo,
            Image diffuseMap
    ) { }
}
