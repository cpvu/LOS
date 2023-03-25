package src.los;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import src.los.game.HelloApplication;

import java.io.IOException;

public class main extends Application {
    public static void main(String[] args) {
        //launch is a static method belongs to Application class
        //main extends Application, that's why we can use here.
        //We can also write as Application.launch()
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Image icon = new Image("img.png");
        stage.getIcons().add(icon);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        //stage.setResizable(false);
        //stage.setFullScreen(true);

        //We need stage.show() to show the stage, otherwise nothing will show
        stage.show();
    }
}
