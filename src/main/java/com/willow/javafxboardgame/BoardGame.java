package com.willow.javafxboardgame;

import com.willow.javafxboardgame.ui.UIBoardGame;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BoardGame extends Application {


    @Override
    public void start(Stage stage) {
        var scene = UIBoardGame.init();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("BoardGame (JavaFX Game)");
        stage.setScene(scene);

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}