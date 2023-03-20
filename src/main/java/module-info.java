module src.los {
    requires javafx.controls;
    requires javafx.fxml;

    exports src.los;
    opens src.los to javafx.fxml;
    exports src.los.game;
    opens src.los.game to javafx.fxml;
    exports src.los.controller;
    opens src.los.controller to javafx.fxml;
}