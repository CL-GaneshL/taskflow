/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator;

import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.ACTION;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.DATABASE;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.DEFAULT_MODE;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.GENERATE_ACTION;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.HOST;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.LOGPATH;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.MODE;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.NULL_LOGPATH;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.PASSWORD;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.PORT;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.RESET_ACTION;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.USERNAME;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.VERSION_ACTION;
import static com.caratlane.taskflow.taskgenerator.Version.APP_AUTHOR;
import static com.caratlane.taskflow.taskgenerator.Version.APP_COPYRIGHT;
import static com.caratlane.taskflow.taskgenerator.Version.APP_NAME;
import static com.caratlane.taskflow.taskgenerator.Version.APP_VERSION;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.exceptions.CommandLineException;
import com.caratlane.taskflow.taskgenerator.generator.Generator;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;

/**
 *
 * @author wdmtraining
 */
public class TaskGenerator {

    private static final Integer SUCCESS_EXIT_STATUS = 0;
    private static final Integer ERROR_EXIT_STATUS = 1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Integer exitStatus = SUCCESS_EXIT_STATUS;

//        String driver = null;
        String host = null;
        String port = null;
        String database = null;
        String username = null;
        String password = null;
//        String charset = null;
//        String collation = null;
        String logpath = NULL_LOGPATH;
        String mode = DEFAULT_MODE;
        String action = DEFAULT_MODE;

        final Generator generator;

        // ==============================================================
        // - Command line parsing 
        // ==============================================================
        try {

            for (String arg : args) {

                final String splitArg[] = arg.split("=");

                if (splitArg.length != 2) {
                    final String msg = "Wrong parameter : " + Arrays.toString(splitArg);
                    throw new CommandLineException(msg);
                }

                final String flag = splitArg[0];
                final String value = splitArg[1];

                switch (flag) {

//                    case DRIVER:
//                        driver = value;
//                        break;
                    case HOST:
                        host = value;
                        break;
                    case PORT:
                        port = value;
                        break;
                    case DATABASE:
                        database = value;
                        break;
                    case USERNAME:
                        username = value;
                        break;
                    case PASSWORD:
                        password = value;
                        break;
//                    case CHARSET:
//                        charset = value;
//                        break;
//                    case COLLATION:
//                        collation = value;
//                        break;
                    case LOGPATH:
                        logpath = value;
                        break;
                    case MODE:
                        mode = value;
                        break;
                    case ACTION:
                        action = value;
                        break;

                    default:
                        final String msg = "Wrong flag : " + Arrays.toString(splitArg);
                        throw new CommandLineException(msg);
                }
            }

            // --------------------------------------------------------------
            // - mandatory parameters
            // --------------------------------------------------------------
//            if (driver == null) {
//                final String msg = "Driver not defined.";
//                throw new CommandLineException(msg);
//            }
            if (host == null) {
                final String msg = "Host not defined.";
                throw new CommandLineException(msg);
            }

            if (port == null) {
                final String msg = "Port not defined.";
                throw new CommandLineException(msg);
            }

            if (database == null) {
                final String msg = "Database not defined.";
                throw new CommandLineException(msg);
            }

            if (username == null) {
                final String msg = "Username not defined.";
                throw new CommandLineException(msg);
            }

            if (password == null) {
                final String msg = "Password not defined.";
                throw new CommandLineException(msg);
            }

//            if (charset == null) {
//                final String msg = "Charset not defined.";
//                throw new CommandLineException(msg);
//            }
//
//            if (collation == null) {
//                final String msg = "Collation not defined.";
//                throw new CommandLineException(msg);
//            }
            // --------------------------------------------------------------
            // - log the parameters 
            // --------------------------------------------------------------
//            System.out.println("TaskGenerator : Command Line : driver => " + driver);
            System.out.println("TaskGenerator : Command Line : host => " + host);
            System.out.println("TaskGenerator : Command Line : port => " + port);
            System.out.println("TaskGenerator : Command Line : database => " + database);
            System.out.println("TaskGenerator : Command Line : username => " + username);
            System.out.println("TaskGenerator : Command Line : password => " + password);
//            System.out.println("TaskGenerator : Command Line : charset => " + charset);
//            System.out.println("TaskGenerator : Command Line : collation => " + collation);
            System.out.println("TaskGenerator : Command Line : logpath => " + logpath);
            System.out.println("TaskGenerator : Command Line : mode => " + mode);
            System.out.println("TaskGenerator : Command Line : action => " + action);

        } catch (CommandLineException ex) {
            // ==============================================================
            // - failed to parse the command line
            // ==============================================================
            System.out.println("TaskGenerator : Command Line : exception => " + ex.getMessage());
        }

        if (action.equals(VERSION_ACTION)) {

            System.out.println("App : " + APP_NAME);
            System.out.println("Author : " + APP_AUTHOR);
            System.out.println("Copyright : " + APP_COPYRIGHT);
            System.out.println("Version : " + APP_VERSION);

        } else {
            // ==============================================================
            // - initialize the app
            // ==============================================================
            try {
                LogManager.initialize(logpath, mode);

                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "Initilizing TaskGenerator ...");
                // ---------------------------------------------------------------------

                DBManager.initialize(
                        host,
                        port,
                        database,
                        username,
                        password
                );

                generator = new Generator();

                switch (action) {

                    case RESET_ACTION:
                        // ==============================================================
                        // - reset database
                        // ==============================================================
                        displayVersion();
                        generator.reset();
                        break;

                    case GENERATE_ACTION:
                        // ==============================================================
                        // - task generation
                        // ==============================================================
                        displayVersion();
                        generator.generate();
                        break;

                }

            } catch (TaskGeneratorException | IOException | DBException ex) {

                // ==============================================================
                // - top level exception catch !
                // ==============================================================
                LogManager.getLogger().log(Level.SEVERE, null, ex);

                exitStatus = ERROR_EXIT_STATUS;

            } finally {
                // ==============================================================
                // - terminate the app
                // ==============================================================
                DBManager.shutdown();

                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "TaskGenerator shutdown.");
                // ---------------------------------------------------------------------

                LogManager.shutdown();
            }
        }

        System.exit(exitStatus);
    }

    /**
     *
     */
    private static void displayVersion() {

        LogManager.getLogger().log(Level.INFO, "App : " + APP_NAME);
        LogManager.getLogger().log(Level.INFO, "Author : " + APP_AUTHOR);
        LogManager.getLogger().log(Level.INFO, APP_COPYRIGHT);
        LogManager.getLogger().log(Level.INFO, "Version : " + APP_VERSION);
    }

}
