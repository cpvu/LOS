package src.los.game;

public abstract class Ability {
    String title;
    int power;

    public Ability (String title, int power) {
        this.title = title;
        this.power = power;
    }

    //heal and attack child class methods??
}
