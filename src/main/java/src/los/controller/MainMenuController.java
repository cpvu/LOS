package src.los.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;

    public MainMenuController() throws IOException {
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

    public void quitGame(ActionEvent event) {
    }
}