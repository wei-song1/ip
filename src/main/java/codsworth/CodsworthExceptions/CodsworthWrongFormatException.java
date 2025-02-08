package codsworth.codsworthexceptions;

import codsworth.ui.UiString;

/**
 * Thrown when input given to Codsworth is invalid or in the wrong format
 */
public class CodsworthWrongFormatException extends Exception {
    @Override
    public String toString() {
        return UiString.getInvalidFormatMessage();
    }
}
