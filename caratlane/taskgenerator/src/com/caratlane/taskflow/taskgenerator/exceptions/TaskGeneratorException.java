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
public class TaskGeneratorException extends Exception {

    /**
     * Creates a new instance of <code>TaskGenaratorException</code> without
     * detail message.
     */
    public TaskGeneratorException() {
    }

    /**
     * Constructs an instance of <code>TaskGenaratorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TaskGeneratorException(String msg) {
        super(msg);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public TaskGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public TaskGeneratorException(Throwable cause) {
        super(cause);
    }

}
