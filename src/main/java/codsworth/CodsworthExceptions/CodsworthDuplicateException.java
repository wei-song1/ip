package codsworth.codsworthexceptions;

import codsworth.ui.UiString;

/**
 * Thrown when theres a duplicate task being created
 */
public class CodsworthDuplicateException extends RuntimeException {
    @Override
    public String toString() {
        return UiString.getDuplicateMessage();
    }
}
