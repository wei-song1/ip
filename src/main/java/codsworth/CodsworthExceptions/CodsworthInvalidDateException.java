package codsworth.codsworthexceptions;

import codsworth.ui.UiString;

/**
 * Thrown when invalid date is given to Codsworth
 */
public class CodsworthInvalidDateException extends RuntimeException {
    @Override
    public String toString() {
        return UiString.getInvalidDateMessage();
    }
}
