package com.aivarsda.batterystatus.Utils;

import android.util.Log;

public class Logger {

    private static boolean IS_LOG_ENABLED = false;
    private static final String SOURCE_FILE = "SourceFile";

    /**Enabling/disabling the log. The default is disabled.
     * @param isLoggingEnabled
     */
    public static void enableLogging(boolean isLoggingEnabled)
    {
        IS_LOG_ENABLED = isLoggingEnabled;
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param message
     */
    public static void log(String tag, String message) {
        if (IS_LOG_ENABLED) {
            Log.i(tag, createLogMsg(message));
        }
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param message
     */
    public static void log(String message) {
        if (IS_LOG_ENABLED) {
            Log.i(getTag(), createLogMsg(message));
        }
    }

    private static String getTag() {
        StackTraceElement traceElement = new Throwable().getStackTrace()[2];
        String fileName = traceElement.getFileName();
        if (fileName == null) return SOURCE_FILE;
        return fileName.split("[.]")[0];
    }

    private static String createLogMsg(String message)
    {
        StackTraceElement traceElement = new Throwable().getStackTrace()[2];
        String fullClassName = traceElement.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = traceElement.getMethodName();
        int lineNumber = traceElement.getLineNumber();

        return "["+lineNumber+"] " + className + "." + methodName + "(): " + message;
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, double msg) {
        log(tag, msg + "");
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, int msg) {
        log(tag, msg + "");
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, float msg) {
        log(tag, msg + "");
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, long msg) {
        log(tag, msg + "");
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, Exception msg) {
        log(tag, msg.toString() + " : Message : " + msg.getMessage());
    }

    /**
     * Printing the log message with the following format: [LineNumber] ClassName.Method(): Message.
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, Object msg) {
        log(tag, msg.toString());
    }
}
