package src.los.common;

import java.util.ArrayList;
import java.util.Iterator;

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

    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    public String getLevelName() {
        return levelName;
    }

    public ArrayList<String> getEnemyImages() {
        return enemyImages;
    }
}
