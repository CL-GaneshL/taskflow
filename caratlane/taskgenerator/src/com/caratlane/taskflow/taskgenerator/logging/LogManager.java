/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author wdmtraining
 */
public class LogManager {

    private static Handler handler = new ConsoleHandler();

    public static Logger getLogger() {
        Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.ALL);
        logger.addHandler(LogManager.handler);
        return logger;
    }

    public static void initialize(final String logpath, final String mode) throws IOException {

        if (logpath != null) {

            LogManager.handler = new FileHandler(logpath);

        } else {
            LogManager.handler = new ConsoleHandler();
        }

        if (mode.equals("debug")) {
            LogManager.handler.setLevel(Level.ALL);
        } else {
            LogManager.handler.setLevel(Level.INFO);
        }

        SimpleFormatter simpleFormatter = new SimpleFormatter();
        LogManager.handler.setFormatter(simpleFormatter);
    }

    public static void shutdown() {

    }

}
