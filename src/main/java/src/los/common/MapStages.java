package src.los.common;

import java.util.ArrayList;
import java.util.Iterator;

public enum MapStages {
    LEVEL_ONE(10), LEVEL_TWO (30), LEVEL_THREE(1);

    private int numberOfEnemies;
    MapStages(int numberOfEnemies) {
        this.numberOfEnemies = numberOfEnemies;
    }

    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }
}
