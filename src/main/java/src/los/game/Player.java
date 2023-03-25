package src.los.game;

import javafx.scene.image.Image;

public class Player {
    int posX, posY, size;
    boolean isDie;
    Image playerImg;

    public Player(int posX, int posY, int size, Image img) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.playerImg = img;
    }


}
