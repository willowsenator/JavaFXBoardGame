package com.willow.javafxboardgame.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Sealed interface for input controllers using Java 17 features.
 * Permits only known implementations for exhaustive pattern matching.
 */
public sealed interface InputController permits KeyboardController {

    /**
     * Immutable snapshot of directional input state.
     */
    record DirectionState(boolean up, boolean down, boolean left, boolean right) {
        public static final DirectionState NONE = new DirectionState(false, false, false, false);

        public boolean hasMovement() {
            return up || down || left || right;
        }

        public int horizontalAxis() {
            return (right ? 1 : 0) - (left ? 1 : 0);
        }

        public int verticalAxis() {
            return (down ? 1 : 0) - (up ? 1 : 0);
        }
    }

    /**
     * Get current directional input state.
     */
    DirectionState getDirectionState();

    /**
     * Check if any directional input is active.
     */
    default boolean hasInput() {
        return getDirectionState().hasMovement();
    }

    /**
     * Reset all input states.
     */
    void reset();

    /**
     * Get handler for key press events (if applicable).
     */
    default EventHandler<KeyEvent> getKeyPressedHandler() {
        return _ -> { };
    }

    /**
     * Get handler for key release events (if applicable).
     */
    default EventHandler<KeyEvent> getKeyReleasedHandler() {
        return _ -> { };
    }
}
