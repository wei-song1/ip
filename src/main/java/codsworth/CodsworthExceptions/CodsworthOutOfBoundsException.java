package codsworth.codsworthexceptions;

import codsworth.Ui.UiString;

/**
 * Thrown when numbers given to Codsworth is out of bounds
 */
public class CodsworthOutOfBoundsException extends RuntimeException {
    @Override
    public String toString() {
        return UiString.getOutOfBoundsMessage();
    }
}
