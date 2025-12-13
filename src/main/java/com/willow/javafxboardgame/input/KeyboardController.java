package com.willow.javafxboardgame.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread-safe keyboard input controller.
 * Final class in the sealed InputController hierarchy.
 */
public final class KeyboardController implements InputController {

    private final AtomicBoolean up = new AtomicBoolean(false);
    private final AtomicBoolean down = new AtomicBoolean(false);
    private final AtomicBoolean left = new AtomicBoolean(false);
    private final AtomicBoolean right = new AtomicBoolean(false);

    private final EventHandler<KeyEvent> keyPressedHandler = this::handleKeyPressed;
    private final EventHandler<KeyEvent> keyReleasedHandler = this::handleKeyReleased;

    private void handleKeyPressed(KeyEvent event) {
        updateDirectionState(event.getCode(), true);
    }

    private void handleKeyReleased(KeyEvent event) {
        updateDirectionState(event.getCode(), false);
    }

    private void updateDirectionState(KeyCode code, boolean pressed) {
        switch (code) {
            case UP, W -> up.set(pressed);
            case DOWN, S -> down.set(pressed);
            case LEFT, A -> left.set(pressed);
            case RIGHT, D -> right.set(pressed);
            default -> { }
        }
    }

    @Override
    public DirectionState getDirectionState() {
        return new DirectionState(up.get(), down.get(), left.get(), right.get());
    }

    @Override
    public void reset() {
        up.set(false);
        down.set(false);
        left.set(false);
        right.set(false);
    }

    @Override
    public EventHandler<KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    @Override
    public EventHandler<KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

    // Convenience methods for direct access
    public boolean isUp() {
        return up.get();
    }

    public boolean isDown() {
        return down.get();
    }

    public boolean isLeft() {
        return left.get();
    }

    public boolean isRight() {
        return right.get();
    }
}
