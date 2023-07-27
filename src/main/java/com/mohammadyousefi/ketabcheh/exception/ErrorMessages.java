package com.mohammadyousefi.ketabcheh.exception;

public abstract class ErrorMessages {
    private static final String UNAUTHORIZED_MESSAGE = "you can't access to this endpoint";
    private static final String EXPECTATION_FAILED_MESSAGE = "an error was happened please try again";

    public static String notFound(String entityName) {
        return "there is no " + entityName + " with this id";
    }

    public static String notFound(String entityName, String by) {
        return "there is no " + entityName + " with this " + by;
    }

    public static String unAuthorized() {
        return UNAUTHORIZED_MESSAGE;
    }

    public static String expectationFailed() {
        return EXPECTATION_FAILED_MESSAGE;
    }
}
