/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.exceptions;

/**
 *
 * @author wdmtraining
 */
public class CommandLineException extends TaskGeneratorException {

    /**
     * Creates a new instance of <code>CommandLineException</code> without
     * detail message.
     */
    public CommandLineException() {
    }

    /**
     * Constructs an instance of <code>CommandLineException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CommandLineException(String msg) {
        super("Task Generator command line : " + msg);
    }
}
