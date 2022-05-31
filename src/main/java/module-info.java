module com.willow.javafxboardgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.willow.javafxboardgame to javafx.fxml;
    exports com.willow.javafxboardgame;
}