package com.rnehru.langutils.match;

/**
 * Exception type used when match-map does not have a conclusive match
 */
public final class MatchException extends RuntimeException {

    /** Constructor for MatchException
     * @param message provided for stacktrace and cause
     */
    public MatchException(String message) {
        super(message);
    }

}
