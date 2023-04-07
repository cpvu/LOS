package src.los.common;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An enumeration class representing different stages of a game map.
 * Each map stage has a name, a number of enemies, and a list of enemy images.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public enum MapStages {
    LEVEL_ONE(10, "Forest Outskirts", new ArrayList<String>() {{
        add("Enemy1.png");
        add("Enemy2.png");
        add("Enemy3.png");
        add("Enemy4.png");
    }}),
    LEVEL_TWO (20,"Valley of the End", new ArrayList<String>() {{
        add("Enemy8.png");
        add("Enemy5.png");
        add("Enemy6.png");
        add("Enemy7.png");
    }}),
    LEVEL_THREE(1, "The Final Battle", new ArrayList<>() {{
        add("Boss.png");
    }});

    private final int numberOfEnemies;
    private final String levelName;
    private final ArrayList<String> enemyImages;
    MapStages(int numberOfEnemies, String levelName, ArrayList<String> enemyImages) {
        this.numberOfEnemies = numberOfEnemies;
        this.levelName = levelName;
        this.enemyImages = enemyImages;
    }

    /**
     * Returns the number of enemies in this stage.
     * @return the number of enemies
     */
    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    /**
     * Returns the name of this stage.
     * @return the stage name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Returns the list of enemy images in this stage.
     * @return the list of enemy images
     */
    public ArrayList<String> getEnemyImages() {
        return enemyImages;
    }
}
