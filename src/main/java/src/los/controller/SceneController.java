package src.los.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import src.los.Main;
import src.los.common.MapStages;
import src.los.common.PlayerClass;
import src.los.game.CharacterSelect;
import src.los.game.Player;
import src.los.game.mainMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SceneController {
    private static int GAME_WIDTH = 700;
    private static int GAME_HEIGHT = 500;
    private static SceneController instance = null;
    private final Stage currentStage;
    private final Scene mainMenu;
    private final Scene characterSelection;
    private Scene gameStage;

    public SceneController() throws IOException {
        currentStage = new Stage();
        currentStage.setResizable(false);
        currentStage.setWidth(GAME_WIDTH);
        currentStage.setHeight(GAME_HEIGHT);

        characterSelection = createCharacterSelection();
        mainMenu = createMainMenu();
        gameStage = createGameStage();

    }

    public static SceneController getInstance() throws IOException {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    private Scene createCharacterSelection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CharacterSelect.class.getResource("characterSelection.fxml"));
        return new Scene(fxmlLoader.load());
    }

    private Scene createMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainMenu.class.getResource("mainMenu.fxml"));
        Scene menu = new Scene(fxmlLoader.load());

        AnchorPane background = (AnchorPane) menu.lookup("#menu");

        Image image = new Image("gamebg.png");

        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO, false,true, true, false));

        Background anchorBg = new Background(backgroundImage);

        background.setBackground(anchorBg);
        return menu;
    }

    private Scene createGameStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Player.class.getResource("gameStage.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());
        AnchorPane background = (AnchorPane) gameScene.lookup("#background");

        Image image = new Image("bg1.png");

        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(GAME_WIDTH, GAME_HEIGHT, true, true, true, true));

        Background anchorBg = new Background(backgroundImage);

        background.setBackground(anchorBg);

        return gameScene;
    }

    public void showMainMenu() {
        currentStage.setScene(mainMenu);
        currentStage.show();
    }

    public void showCharacterSelect() {
        currentStage.setScene(characterSelection);
        currentStage.show();
    }

    public void showGameStage() {
        currentStage.setScene(gameStage);
        currentStage.show();
    }
}
