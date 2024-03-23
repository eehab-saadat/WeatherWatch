module org.algoavengers.weatherwatch {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;

    opens org.algoavengers.weatherwatch to javafx.fxml;
    exports org.algoavengers.weatherwatch;
    exports org.algoavengers.weatherwatch.utils;
    opens org.algoavengers.weatherwatch.utils to javafx.fxml;
}