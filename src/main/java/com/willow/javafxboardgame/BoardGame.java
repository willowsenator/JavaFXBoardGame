package com.willow.javafxboardgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class BoardGame extends Application {
    private Group root;
    private Group gameBoard;
    private Scene scene;
    private StackPane uiLayout;
    private VBox uiContainer;
    private ImageView boardGameBackPlate;
    private TextFlow infoOverlay;
    private Image splashScreen;
    private Image helpLayer;
    private Image legalLayer;
    private Image creditLayer;
    private Image scoreLayer;
    private Button gameButton;
    private Button helpButton;
    private Button legalButton;
    private Button creditButton;
    private Button scoreButton;
    @Override
    public void start(Stage stage) {
        loadImageAssets();
        createTextAssets();
        createBoardGameNodes();
        createBoardGameNodes();
        addNodesToSceneGraph();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        gameButton.setOnAction(actionEvent-> System.out.println("Starting Game"));
        helpButton.setOnAction(actionEvent -> System.out.println("Game Instructions"));
        scoreButton.setOnAction(actionEvent -> System.out.println("High Scores"));
        legalButton.setOnAction(actionEvent -> System.out.println("Copyrights"));
        creditButton.setOnAction(actionEvent -> System.out.println("Credits"));

        stage.show();
    }

    private void addNodesToSceneGraph() {
        root.getChildren().add(gameBoard);
        root.getChildren().add(uiLayout);
        uiLayout.getChildren().add(boardGameBackPlate);
        uiLayout.getChildren().add(infoOverlay);
        uiLayout.getChildren().add(uiContainer);
        uiContainer.getChildren().addAll(gameButton, helpButton, legalButton, creditButton, scoreButton);
    }

    private void createBoardGameNodes() {
        root = new Group();
        gameBoard = new Group();
        scene = new Scene(root, 1280, 640);
        scene.setFill(Color.TRANSPARENT);
        uiLayout = new StackPane();
        uiLayout.setBackground(Background.EMPTY);
        boardGameBackPlate = new ImageView();
        boardGameBackPlate.setImage(splashScreen);
        infoOverlay = new TextFlow();
        uiContainer = new VBox(10);
        uiContainer.setAlignment(Pos.TOP_RIGHT);
        var uiPadding = new Insets(16);
        uiContainer.setPadding(uiPadding);
        gameButton = new Button();
        gameButton.setText("Start Game");
        gameButton.setMaxWidth(125);
        helpButton = new Button();
        helpButton.setText("Game Rules");
        helpButton.setMaxWidth(125);
        scoreButton = new Button();
        scoreButton.setText("High Scores");
        scoreButton.setMaxWidth(125);
        legalButton = new Button();
        legalButton.setText("Disclaimers");
        legalButton.setMaxWidth(125);
        creditButton = new Button();
        creditButton.setText("Game Credits");
        creditButton.setMaxWidth(125);
    }

    private void loadImageAssets() {

        splashScreen = new Image(Objects.requireNonNull(getClass().getResource("/images/welcome.png")).toString(),
                1280, 640, true, false, true);
    }

    private void createTextAssets() {
    }



    public static void main(String[] args) {
        launch();
    }
}