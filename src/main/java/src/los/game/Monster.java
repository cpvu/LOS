package src.los.game;

import java.util.Random;

public class Monster {
    private int HP;
    private int level;
    private int exp;
    private int posX;
    private int posY;

    public Monster(int HP, int level, int posX, int posY, int exp) {
        this.HP = HP;
        this.level = level;
        this.posX = posX;
        this.posY = posY;
        this.exp = exp;
    }

    public Monster generateLvl1 (int posX, int posY) {
        Monster monster = new Monster(100, 1, posX, posY, 50);
        return monster;
    }

    public Monster generateLvl2 (int posX, int posY) {
        Monster monster = new Monster(200, 2, posX, posY, 100);
        return monster;
    }

    public Monster generateLvl3 (int posX, int posY) {
        Monster monster = new Monster(300, 3, posX, posY, 300);
        return monster;
    }

    public void rotate() {
        Random rand = new Random();
        if(rand.nextInt(10) > 5) {
            this.posX = rand.nextInt(1) + 1;
        } else {
            this.posY = rand.nextInt(1) + 1;
        }
    }

    public void rotate(int upBound) {
        Random rand = new Random();
        if(rand.nextInt(10) > 5) {
            this.posX = rand.nextInt(upBound) + 1;
        } else {
            this.posY = rand.nextInt(upBound) + 1;
        }
    }

    public void HpClear() {
        this.HP = 0;
    }
}
