package src.los.common;

/**
 * This interface defines the methods to be implemented by classes that represent game characters as enums.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public interface EnumInterface {
    public String getBaseImage();
    /**
     * Returns the special ability of the character.
     * @return the special ability of the character as a String.
     */
    public String getCharacterAbility();
    public String getDeadImage();
    public String getDialogueImage();
}
