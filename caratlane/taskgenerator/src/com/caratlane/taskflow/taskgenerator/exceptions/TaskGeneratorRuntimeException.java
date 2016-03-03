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
public class TaskGeneratorRuntimeException extends RuntimeException {

    /**
     * Creates a new instance of <code>TaskGeneratorRuntimeException</code>
     * without detail message.
     */
    public TaskGeneratorRuntimeException() {
    }

    /**
     * Constructs an instance of <code>TaskGeneratorRuntimeException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public TaskGeneratorRuntimeException(String msg) {
        super(msg);
    }

    /**
     *
     * @param cause
     */
    public TaskGeneratorRuntimeException(Throwable cause) {
        super(cause);
    }

}
