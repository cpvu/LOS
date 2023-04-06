package src.los.common;

import java.util.ArrayList;
import java.util.Iterator;

public enum MapStages {
    LEVEL_ONE(10, "Forest Outskirts"),
    LEVEL_TWO (30,"Valley of the End") ,
    LEVEL_THREE(1, "The Final Battle");

    private final int numberOfEnemies;
    private final String levelName;
    MapStages(int numberOfEnemies, String levelName) {
        this.numberOfEnemies = numberOfEnemies;
        this.levelName = levelName;
    }

    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    public String getLevelName() {
        return levelName;
    }
}
