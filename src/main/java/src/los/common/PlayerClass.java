package src.los.common;

public enum PlayerClass implements EnumInterface{
    NARUTO("Naruto", "NarutoSprite.png", "RasenganSprite.png", "NarutoDead.png"),
    SASUKE("Sasuke", "SasukeSprite.png", "Fireball.png", "SasukeDead.png");

    private final String characterName;
    private final String characterBaseImage;
    private final String characterDeadImage;
    private final String characterAbility;
    PlayerClass(String characterName, String characterImage, String characterAbility, String characterDeadImage) {
        this.characterName = characterName;
        this.characterBaseImage = characterImage;
        this.characterAbility = characterAbility;
        this.characterDeadImage = characterDeadImage;
    }
    @Override
    public String getCharacterName() {
        return this.characterName;
    }
    @Override
    public String getBaseImage() {
        return this.characterBaseImage;
    }
    @Override
    public String getCharacterAbility() {
        return this.characterAbility;
    }

    @Override
    public String getDeadImage() { return this.characterDeadImage;}
}
