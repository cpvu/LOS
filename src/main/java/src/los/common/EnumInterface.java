package src.los.common;

/**
 * This interface defines the methods to be implemented by classes that represent game characters as enums.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public interface EnumInterface {
    /**
     * Returns the base image name.
     * @return the base image name as a String.
     */
    public String getBaseImage();

    /**
     * Returns the special ability of the character.
     * @return the special ability of the character as a String.
     */
    public String getCharacterAbility();

    /**
     * Returns the dead image.
     * @return the the dead image name as a String.
     */
    public String getDeadImage();

    /**
     * Returns the dialogue image.
     * @return the dialogue image name as a String.
     */
    public String getDialogueImage();
}
