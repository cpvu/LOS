package src.los.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import src.los.game.mainMenu;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class mainMenuController {
    private Stage stage;

    public mainMenuController() throws IOException {
    }

    public void startButton(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance().showCharacterSelect();
//        FXMLLoader fxmlLoader = new FXMLLoader(mainMenu.class.getResource("characterSelection.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.setTitle("Shinobi Invaders");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
    }
}