package com.willow.javafxboardgame.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Sealed interface for game states using Java 24 sealed classes.
 * Provides exhaustive pattern matching in switch expressions.
 */
public sealed interface GameState permits
        GameState.Menu,
        GameState.Playing,
        GameState.Paused,
        GameState.GameOver {

    /**
     * Menu state - showing main menu.
     */
    record Menu() implements GameState { }

    /**
     * Playing state - active gameplay.
     */
    record Playing(int currentPlayer, int turnNumber) implements GameState {
        public Playing {
            if (currentPlayer < 0) {
                throw new IllegalArgumentException("currentPlayer must be >= 0");
            }
            if (turnNumber < 1) {
                throw new IllegalArgumentException("turnNumber must be >= 1");
            }
        }

        public Playing nextTurn(int nextPlayer) {
            return new Playing(nextPlayer, turnNumber + 1);
        }
    }

    /**
     * Paused state - game temporarily halted.
     */
    record Paused(Playing previousState) implements GameState {
        public Playing resume() {
            return previousState;
        }
    }

    /**
     * Game over state - game finished.
     */
    record GameOver(int winnerPlayer, int totalTurns) implements GameState {
        public GameOver {
            if (winnerPlayer < 0) {
                throw new IllegalArgumentException("winnerPlayer must be >= 0");
            }
        }
    }

    /**
     * Pattern match on game state (Java 24 style).
     */
    @SuppressFBWarnings(value = {"DLS_DEAD_LOCAL_STORE", "EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"},
            justification = "Java 24 pattern matching with record deconstruction - SpotBugs false positive")
    default String getDisplayName() {
        return switch (this) {
            case Menu _ -> "Main Menu";
            case Playing(int player, int turn) -> "Player %d - Turn %d".formatted(player + 1, turn);
            case Paused _ -> "Paused";
            case GameOver(int winner, _) -> "Game Over - Player %d Wins!".formatted(winner + 1);
        };
    }

    /**
     * Check if game accepts input.
     */
    @SuppressFBWarnings(value = "ITC_INHERITANCE_TYPE_CHECKING",
            justification = "Java 24 exhaustive pattern matching on sealed interface")
    default boolean acceptsInput() {
        return switch (this) {
            case Playing _ -> true;
            case Menu _, Paused _, GameOver _ -> false;
        };
    }
}
