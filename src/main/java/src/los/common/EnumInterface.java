package src.los.common;

/**
 * This interface defines the methods to be implemented by classes that represent game characters as enums.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public interface EnumInterface {
    /**
     * Returns the name of the character.
     * @return the name of the character as a String.
     */
    public String getCharacterName();

    /**
     * Returns the path of the character image.
     * @return the image path as string.
     */
    public String getImage();

    /**
     * Returns the special ability of the character.
     * @return the special ability of the character as a String.
     */
    public String getCharacterAbility();
}
