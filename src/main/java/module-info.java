module com.willow.javafxboardgame {
    requires javafx.controls;

    opens com.willow.javafxboardgame to javafx.fxml;
    exports com.willow.javafxboardgame;
    exports com.willow.javafxboardgame.ui;
    opens com.willow.javafxboardgame.ui to javafx.fxml;
}