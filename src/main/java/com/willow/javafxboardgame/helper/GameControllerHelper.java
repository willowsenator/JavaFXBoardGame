package com.willow.javafxboardgame.helper;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public final class GameControllerHelper {

    private static boolean up;
    private static boolean down;
    private static boolean left;
    private static boolean right;
    public static final EventHandler<? super KeyEvent> KEY_PRESSED = keyEvent -> {
        switch (keyEvent.getCode()) {
            case UP, W -> up = true;
            case DOWN, S -> down = true;
            case LEFT, A -> left = true;
            case RIGHT, D -> right = true;
            default -> {
            }
        }
    };

    public static final EventHandler<? super KeyEvent> KEY_RELEASED = keyEvent -> {
        switch (keyEvent.getCode()) {
            case UP, W -> up = false;
            case DOWN, S -> down = false;
            case LEFT, A -> left = false;
            case RIGHT, D -> right = false;
            default -> {

            }
        }
    };

    private GameControllerHelper() {
    }
}
