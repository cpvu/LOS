package src.los.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.los.common.MapStages;
import src.los.common.PlayerClass;
import src.los.game.SpaceDriver;
import java.io.IOException;

public class SceneController {
    private static final int GAME_WIDTH = 700;
    private static final int GAME_HEIGHT = 500;
    private static SceneController instance = null;
    private final Stage currentStage;
    private final Scene mainMenu;
    private final Scene characterSelection;
    public Scene gameStage;

    public Scene dialogueScene;
    public static PlayerClass chosenCharacter;
    public SceneController() throws IOException {
        currentStage = new Stage();
        currentStage.setTitle("Legend of Shinobi");
        currentStage.setResizable(false);
        currentStage.setWidth(GAME_WIDTH);
        currentStage.setHeight(GAME_HEIGHT);

        characterSelection = createCharacterSelection();
        mainMenu = createMainMenu();
    }

    public static SceneController getInstance() throws IOException {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    public void createBackground(AnchorPane sceneBackground, String sceneType) {
        String imageString;

        switch (sceneType) {
            case "game":
                imageString = "gameStageBgOne.png";
                break;
            case "menu":
                imageString = "mainMenuBg.png";
                break;
            case "LEVEL_ONE":
                imageString = "gameStageBgOne.png";
                break;
            case "LEVEL_TWO":
                imageString = "bgLevelTwo.png";
                break;
            case "LEVEL_THREE":
                imageString = "bgLevelTwo.png";
                break;
            default:
                throw new RuntimeException("No background image found");
        }

        Image image = new Image(imageString);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(GAME_WIDTH, GAME_HEIGHT, true, true, true, true));

        sceneBackground.setBackground(new Background(backgroundImage));
    }

    private Scene createCharacterSelection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("characterSelection.fxml"));
        return new Scene(fxmlLoader.load());
    }
    private Scene createMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("mainMenu.fxml"));
        Scene menu = new Scene(fxmlLoader.load());

        AnchorPane sceneBackground = (AnchorPane) menu.lookup("#menu");
        createBackground(sceneBackground, "menu");

        return menu;
    }

    public void createGameStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("gameStage.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());

        AnchorPane sceneBackground = (AnchorPane) gameScene.lookup("#background");
        createBackground(sceneBackground, "game");

        this.gameStage = gameScene;
    }

    public void createDialogue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("dialogue.fxml"));
        Scene dialogueScene = new Scene(fxmlLoader.load());

        ImageView characterPortrait = (ImageView) gameStage.getRoot().lookup("#characterPortrait");
        characterPortrait.setImage(new Image(SpaceDriver.chosenCharacter.getDialogueImage()));

        this.dialogueScene = dialogueScene;
    }

   private String generateDialogue() {
        if (SpaceDriver.chosenCharacter == PlayerClass.NARUTO) {
            return "Dattebayo! That was tough, I need to find Sasuke!";
        }
        return "That was nothing.";
    }

    public void showDialogue(MapStages currentLevel) throws IOException {
        Text dialogueText = (Text) dialogueScene.getRoot().lookup("#textDialogue");
        dialogueText.setText(generateDialogue());

        Label text = (Label) gameStage.getRoot().lookup("#level");
        text.setText(String.valueOf(SpaceDriver.currentLevel.getLevelName()));


        currentStage.setScene(dialogueScene);
        currentStage.show();
    }

    public void loadGameBg(MapStages currentLevel) {
        AnchorPane sceneBackground = (AnchorPane) gameStage.lookup("#background");
        createBackground(sceneBackground, String.valueOf(currentLevel));
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
