package src.los.common;

public enum PlayerClass implements EnumInterface{
    NARUTO("Naruto", "imageURL", "Rasengan"),
    SASUKE("Sasuke", "imageURL", "Chidori");

    private final String characterName;
    private final String characterImage;
    private final String characterAbility;
    PlayerClass(String characterName, String characterImage, String characterAbility) {
        this.characterName = characterName;
        this.characterImage= characterImage;
        this.characterAbility = characterAbility;
    }
    @Override
    public String getCharacterName() {
        return this.characterName;
    }
    @Override
    public String getImage() {
        return this.characterImage;
    }
    @Override
    public String getCharacterAbility() {
        return this.characterAbility;
    }
}
