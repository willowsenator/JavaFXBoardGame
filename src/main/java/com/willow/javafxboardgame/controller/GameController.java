package com.willow.javafxboardgame.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameController {

    private static boolean up;
    private static boolean down;
    private static boolean left;
    private static boolean right;
    public final static EventHandler<? super KeyEvent> keyPressed = keyEvent -> {
        switch (keyEvent.getCode()) {
            case UP, W -> up = true;
            case DOWN, S -> down = true;
            case LEFT, A -> left = true;
            case RIGHT, D -> right = true;
        }
    };

    public final static EventHandler<? super KeyEvent> keyReleased = keyEvent -> {
        switch (keyEvent.getCode()) {
            case UP, W -> up = false;
            case DOWN, S -> down = false;
            case LEFT, A -> left = false;
            case RIGHT, D -> right = false;
        }
    };

    private GameController() {
    }
}
