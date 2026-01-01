package com.willow.javafxboardgame.helper;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread-safe input controller for game keyboard events.
 * Uses AtomicBoolean for lock-free thread-safe state management.
 */
public final class GameControllerHelper {

    /**
     * Immutable snapshot of current input state (Java 16 record).
     */
    public record InputState(boolean up, boolean down, boolean left, boolean right) {
        public boolean hasMovement() {
            return up || down || left || right;
        }
    }

    private final AtomicBoolean up = new AtomicBoolean(false);
    private final AtomicBoolean down = new AtomicBoolean(false);
    private final AtomicBoolean left = new AtomicBoolean(false);
    private final AtomicBoolean right = new AtomicBoolean(false);

    private final EventHandler<KeyEvent> keyPressedHandler = this::handleKeyPressed;
    private final EventHandler<KeyEvent> keyReleasedHandler = this::handleKeyReleased;

    private void handleKeyPressed(KeyEvent event) {
        setDirectionState(event.getCode(), true);
    }

    private void handleKeyReleased(KeyEvent event) {
        setDirectionState(event.getCode(), false);
    }

    private void setDirectionState(KeyCode code, boolean pressed) {
        // Java 21 pattern matching for switch (JEP 441)
        switch (code) {
            case UP, W -> up.set(pressed);
            case DOWN, S -> down.set(pressed);
            case LEFT, A -> left.set(pressed);
            case RIGHT, D -> right.set(pressed);
            default -> { } // Default case with empty block - ignore other keys
        }
    }

    public EventHandler<KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    public EventHandler<KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

    /**
     * Get immutable snapshot of current input state.
     * Thread-safe: captures consistent state at moment of call.
     */
    public InputState getInputState() {
        return new InputState(up.get(), down.get(), left.get(), right.get());
    }

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

    /**
     * Reset all direction states to false.
     */
    public void reset() {
        up.set(false);
        down.set(false);
        left.set(false);
        right.set(false);
    }
}
