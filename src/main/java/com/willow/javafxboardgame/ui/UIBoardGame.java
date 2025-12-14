package com.willow.javafxboardgame.ui;

import com.willow.javafxboardgame.input.InputController;
import com.willow.javafxboardgame.input.KeyboardController;
import com.willow.javafxboardgame.model.GameState;
import javafx.scene.Scene;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Main UI facade that coordinates game components.
 * Delegates to specialized classes:
 * - AssetLoader: loads images and materials
 * - SceneBuilder: constructs scene graph
 * - MenuController: handles button events
 */
@SuppressFBWarnings(value = "FCBL_FIELD_COULD_BE_LOCAL",
        justification = "MenuController held for lifecycle management")
public class UIBoardGame {

    private final Scene scene;
    private final SceneComponents components;
    private final InputController inputController;
    private final MenuController menuController;
    private GameState gameState = new GameState.Menu();

    public UIBoardGame() {
        var assets = AssetLoader.load();
        this.components = SceneBuilder.build(assets);
        this.scene = components.scene();
        this.inputController = new KeyboardController();
        this.menuController = new MenuController(components, assets, inputController, this::setGameState);
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
            justification = "Scene must be exposed for JavaFX Stage")
    public Scene getScene() {
        return scene;
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
                components.uiLayout().setVisible(true);
                yield newState;
            }
            case GameState.Playing _ -> {
                components.uiLayout().setVisible(false);
                yield newState;
            }
            case GameState.Paused _, GameState.GameOver _ -> newState;
        };
    }
}
