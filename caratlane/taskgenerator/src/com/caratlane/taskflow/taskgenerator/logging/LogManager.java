/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.logging;

import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.CHECK_ACTION;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.DEBUG_MODE;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.NULL_LOGPATH;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author wdmtraining
 */
public class LogManager {

    private static final String TEST_MESSAGE_PREFIX = "TEST_MGS";

    private static Handler handler = new ConsoleHandler();
    private static Handler testHandler = new ConsoleHandler();
    private static Logger logger = null;

    public static Logger getLogger() {
        return logger;
    }

    /**
     *
     * @param logpath
     * @param mode
     * @param action
     * @throws IOException
     */
    public static void initialize(
            final String logpath,
            final String mode,
            final String action)
            throws IOException {

        if (logpath.equals(NULL_LOGPATH)) {
            LogManager.handler = new ConsoleHandler();
        } else {
            LogManager.handler = new FileHandler(logpath);
        }

        if (mode.equals(DEBUG_MODE)) {
            LogManager.handler.setLevel(Level.ALL);
        } else {
            LogManager.handler.setLevel(Level.INFO);
        }

        final LogFormatter formatter = new LogFormatter();
        LogManager.handler.setFormatter(formatter);

        // filter the type of messsages : no test messages
        LogManager.handler.setFilter((LogRecord record)
                -> !record.getMessage().startsWith(TEST_MESSAGE_PREFIX));

        if (action.equals(CHECK_ACTION)) {
            final TestLogFormatter testLogFormatter = new TestLogFormatter();
            LogManager.testHandler.setFormatter(testLogFormatter);

            // filter the type of messsages : only test messages
            LogManager.testHandler.setFilter((LogRecord record)
                    -> record.getMessage().startsWith(TEST_MESSAGE_PREFIX));
        }

        // create the logger
        LogManager.logger = Logger.getAnonymousLogger();
        LogManager.logger.setUseParentHandlers(false);

        // assign the current handler
        LogManager.logger.addHandler(LogManager.handler);
        LogManager.handler.setLevel(Level.ALL);
        LogManager.logger.setLevel(Level.ALL);

        // assign the test handler
        LogManager.logger.addHandler(LogManager.testHandler);
        LogManager.testHandler.setLevel(Level.ALL);
        LogManager.testHandler.setLevel(Level.ALL);
    }

    /**
     *
     * @return
     */
    public static boolean isTestLoggable() {
        return LogManager.logger != null;
    }

    /**
     *
     * @param level
     * @param msg
     */
    public static void logTestMsg(
            final Level level,
            final String msg) {
        LogManager.logger.log(
                Level.FINE,
                TEST_MESSAGE_PREFIX + " " + level.toString() + " " + "{0}", msg);
    }

    /**
     *
     * @param level
     * @param msg
     * @param param1
     */
    public static void logTestMsg(
            final Level level,
            final String msg,
            final String param1) {
        LogManager.logger.log(
                Level.FINE,
                TEST_MESSAGE_PREFIX + " " + level.toString() + " " + msg, param1);
    }

    /**
     *
     * @param level
     * @param msg
     * @param param1
     * @param param2
     */
    public static void logTestMsg(
            final Level level,
            final String msg,
            final String param1,
            final String param2) {

        LogManager.logger.log(level,
                MessageFormat.format(
                        TEST_MESSAGE_PREFIX + " " + level.toString() + " " + msg,
                        new Object[]{param1, param2}));
    }

    /**
     *
     */
    public static void shutdown() {

        if (handler != null) {
            handler.flush();
            handler.close();
            handler = null;
        }

        if (testHandler != null) {
            testHandler.flush();
            testHandler.close();
            testHandler = null;
        }

        if (logger != null) {
            logger = null;
        }
    }

}
