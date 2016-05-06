/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author wdmtraining
 */
public class TestTaskGeneratorException extends Exception {

    /**
     * Creates a new instance of <code>TestTaskGeneratorException</code> without
     * detail message.
     */
    public TestTaskGeneratorException() {
    }

    /**
     * Constructs an instance of <code>TestTaskGeneratorException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public TestTaskGeneratorException(String msg) {
        super(msg);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public TestTaskGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

}
