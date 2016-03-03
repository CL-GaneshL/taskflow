/*
 * Copyright (C) 2014 WD Media
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.caratlane.taskflow.taskgenerator.db;


import java.text.MessageFormat;

/**
 *
 * @author WD Media
 */
public class DBException extends Exception {

    private static final long serialVersionUID = -6211815902040598946L;

    private DBException() {
    }

    /**
     * Constructs an instance of <code>HexaDBException2</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DBException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>HexaDBException2</code> with the
     * specified throwable.
     *
     * @param cause the throwable.
     */
    public DBException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param pattern
     * @param arguments
     */
    public DBException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
    }

    /**
     * Prints an exception stack trace on stdout.
     *
     * @param LOG a logger for the class calling this method.
     * @param excep the throwable.
     */
//    public static void logErrorStackTrace(HexaDBLogger LOG, Throwable excep) {
//
//        LOG.error(excep.getMessage());
//        LOG.error("Exception Stack trace : ");
//
//        printCauseStackTrace('A', LOG, excep);
//    }

    /**
     * Prints an exception stack trace on stdout.
     *
     * @param rank the throwable index in the stack.
     * @param LOG a logger for the class calling this method.
     * @param excep the throwable.
     */
//    private static void printCauseStackTrace(char rank, HexaDBLogger LOG, Throwable cause) {
//
//        if (cause != null) {
//            int index = 0;
//            LOG.error("msg : " + cause.getMessage());
//            StackTraceElement[] traces = cause.getStackTrace();
//            for (StackTraceElement trace : traces) {
//                LOG.error(" [" + rank + "-" + (index++) + "] " + trace);
//            }
//            Throwable next = cause.getCause();
//            printCauseStackTrace(++rank, LOG, next);
//        }
//    }

}
