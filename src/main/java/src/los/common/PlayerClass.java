package src.los.common;

public enum PlayerClass implements EnumInterface{
    NARUTO("Naruto",
            "NarutoSprite.png",
            "RasenganSprite.png",
            "NarutoDead.png",
            "narutoPortrait.png"),
    SASUKE("Sasuke",
            "SasukeSprite.png",
            "Fireball.png",
            "SasukeDead.png",
            "sasukePortrait.png");

    private final String characterName;
    private final String characterBaseImage;
    private final String characterDeadImage;
    private final String characterAbility;
    private final String dialogueImage;
    PlayerClass(String characterName, String characterImage, String characterAbility, String characterDeadImage, String dialogueImage) {
        this.characterName = characterName;
        this.characterBaseImage = characterImage;
        this.characterAbility = characterAbility;
        this.characterDeadImage = characterDeadImage;
        this.dialogueImage = dialogueImage;
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
    @Override
    public String getDialogueImage() { return this.dialogueImage; }
}
