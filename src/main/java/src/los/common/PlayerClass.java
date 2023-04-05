package src.los.common;

/**
 * PlayerClass is an enumeration class that represents different types of players in a game.
 *
 * It implements EnumInterface that defines the methods to retrieve the character's name, image, and ability.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public enum PlayerClass implements EnumInterface{
    NARUTO("Naruto", "NarutoSprite.png", "RasenganSprite.png"),
    SASUKE("Sasuke", "SasukeSprite.png", "Fireball.png");

    private final String characterName;
    private final String characterImage;
    private final String characterAbility;
    PlayerClass(String characterName, String characterImage, String characterAbility) {
        this.characterName = characterName;
        this.characterImage= characterImage;
        this.characterAbility = characterAbility;
    }

    /**
     * Returns the character's name.
     * @return the character's name.
     */
    @Override
    public String getCharacterName() {
        return this.characterName;
    }

    /**
     * Returns the character's image.
     * @return the character's image.
     */
    @Override
    public String getImage() {
        return this.characterImage;
    }

    /**
     * Returns the character's ability.
     * @return the character's ability.
     */
    @Override
    public String getCharacterAbility() {
        return this.characterAbility;
    }
}
