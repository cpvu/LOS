package src.los.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class representing a shot fired by a player in a game.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class Shot {

    public boolean toRemove;

    int posX, posY, speed = 10;
    static final int size = 6;
    private int score = 0;

    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Updates the position of this shot based on its speed.
     */
    public void update() {
        posY-=speed;
    }

    /**
     * Draws this shot on a canvas.
     */
    public void draw() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        if (score >=50 && score<=70 || score>=120) {
            gc.setFill(Color.YELLOWGREEN);
            speed = 50;
            gc.fillRect(posX-5, posY-10, size+10, size+30);
        } else {
            gc.fillOval(posX, posY, size, size);
        }
    }

    /**
     * Computes the distance between two points.
     * @param x1 the x-coordinate of the first point
     * @param y1 the y-coordinate of the first point
     * @param x2 the x-coordinate of the second point
     * @param y2 the y-coordinate of the second point
     * @return the distance between the two points
     */
    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    /**
     * Determines whether this shot collides with a player.
     * @param player the player to check for collision
     * @return true if this shot collides with the player, false otherwise
     */
    public boolean colide(Player player) {
        int distance = distance(this.posX + size / 2, this.posY + size / 2,
                player.posX + player.size / 2, player.posY + player.size / 2);
        return distance  < player.size / 2 + size / 2;
    }
}