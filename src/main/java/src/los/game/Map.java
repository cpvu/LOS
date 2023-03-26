package src.los.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Map {
    private String name;
    private int weight, height;

    public Map(String name, int weight, int height) {
        this.height = height;
        this.weight = weight;
        this.name = name;
    }

    public void setMapSpace(Color color) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
    }

    public void setImg (Image img) {
        //placeholder;
    }
}
