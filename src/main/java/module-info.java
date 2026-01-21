module com.willow.javafxboardgame {
    requires javafx.controls;
    requires javafx.graphics;
    requires static com.github.spotbugs.annotations;

    exports com.willow.javafxboardgame;
    exports com.willow.javafxboardgame.ui;
    exports com.willow.javafxboardgame.model;
    exports com.willow.javafxboardgame.input;
    exports com.willow.javafxboardgame.helper;
}