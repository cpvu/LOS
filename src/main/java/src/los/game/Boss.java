package src.los.game;

import java.util.ArrayList;
import java.util.Random;

public class Boss {
    private int HP;
    private int posX;
    private int posY;
    private ArrayList<Ability> abilities;

    public Boss(int HP, int posX, int posY, ArrayList<Ability> abilities) {
        this.HP = HP;
        this.posX = posX;
        this.posY = posY;
        this.abilities = abilities;
    }

    public Ability useAbility() {
        int upBound = abilities.size();
        Random rand = new Random(upBound);
        return abilities.get(rand.nextInt());
    }

    public boolean isDead() {
        if (HP <= 0) {
            return true;
        }
        return false;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}

