package src.los.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


public class Player {
    static final int EXPLOSION_STEPS = 10;
    static final Image DEAD_IMG = new Image("dead.png");
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_W = 3;

    int posX, posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionStep = 0;

    public Player(int posX, int posY, int size,  Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        img = image;
    }

    public Shot shoot() {
        return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
    }

    public void update() {
        if(exploding) explosionStep++;
        destroyed = explosionStep > EXPLOSION_STEPS;
    }

    public void draw() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(exploding) {
            gc.drawImage(DEAD_IMG, explosionStep % EXPLOSION_COL * EXPLOSION_W, (explosionStep / EXPLOSION_COL) * EXPLOSION_W + 1,
                    128, 128,
                    posX, posY, size, size);
        }
        else {
            gc.drawImage(img, posX, posY, size, size);
        }
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public boolean colide(Player other) {
        int d = distance(this.posX + size / 2, this.posY + size /2,
                other.posX + other.size / 2, other.posY + other.size / 2);
        return d < other.size / 2 + this.size / 2 ;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }

}