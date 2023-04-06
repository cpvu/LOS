package src.los.common;

/**
 * PlayerClass is an enumeration class that represents different types of players in a game.
 *
 * It implements EnumInterface that defines the methods to retrieve the character's name, image, and ability.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public enum PlayerClass implements EnumInterface{
    NARUTO("Naruto",
            "NarutoSprite.png",
            "RasenganSprite.png",
            "NarutoDead.png",
            "narutoPortrait.png",
            "narutoGameOver.png"),
    SASUKE("Sasuke",
            "SasukeSprite.png",
            "Fireball.png",
            "SasukeDead.png",
            "sasukePortrait.png",
            "sasukeGameOver.png");

    private final String characterName;
    private final String characterBaseImage;
    private final String characterDeadImage;
    private final String characterAbility;
    private final String dialogueImage;
    private final String gameOverImage;
    PlayerClass(String characterName,
                String characterImage,
                String characterAbility,
                String characterDeadImage,
                String dialogueImage,
                String gameOverImage) {

        this.characterName = characterName;
        this.characterBaseImage = characterImage;
        this.characterAbility = characterAbility;
        this.characterDeadImage = characterDeadImage;
        this.dialogueImage = dialogueImage;
        this.gameOverImage = gameOverImage;
    }

    /**
     * Returns the character's image.
     * @return the character's image.
     */
    @Override
    public String getBaseImage() {
        return this.characterBaseImage;
    }

    /**
     * Returns the character's ability.
     * @return the character's ability.
     */
    @Override
    public String getCharacterAbility() {
        return this.characterAbility;
    }
    @Override
    public String getDeadImage() { return this.characterDeadImage;}
    @Override
    public String getDialogueImage() { return this.dialogueImage; }

    public String getGameOverImage() { return this.gameOverImage; }
}
