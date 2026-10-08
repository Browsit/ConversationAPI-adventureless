package org.browsit.conversations.api.util;

/**
 * @author Illusion created on 2/9/2023
 */
public final class Constants {

    private Constants() {
    }

    /**
     * The default message that gets displayed when the user enters an invalid input.
     */
    public static final String INVALID_INPUT_MESSAGE = "Invalid input, try again.";

    /**
     * The default message that gets displayed when the user enters an input that can't be converted.
     */
    public static final String CONVERSION_FAILED_MESSAGE = "Conversion failed, try again.";

    /**
     * The default message that gets displayed when the user runs out of attempts.
     */
    public static final String ATTEMPTS_OVER_MESSAGE = "You've run out of attempts.";
}
