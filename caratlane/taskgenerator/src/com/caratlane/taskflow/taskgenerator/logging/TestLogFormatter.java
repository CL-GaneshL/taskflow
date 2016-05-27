/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author wdmtraining
 */
class TestLogFormatter extends Formatter {

    private static final DateFormat DATE_FORMAT
            = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    private static final String CLASSNAME_PREFIX
            = "com.caratlane.taskflow.taskgenerator";

    @Override
    public String format(LogRecord record) {

        final StringBuilder builder = new StringBuilder(1000);

        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public String getHead(Handler h) {
        return super.getHead(h);
    }

    @Override
    public String getTail(Handler h) {
        return super.getTail(h);
    }

    /**
     *
     * @param millisecs
     * @return
     */
    private String formatDateTime(long millisecs) {
        Date recordDate = new Date(millisecs);
        return DATE_FORMAT.format(recordDate);
    }

    /**
     *
     * @param record
     * @return
     */
    private String formatClassName(LogRecord record) {
        return record.getSourceClassName().replaceFirst(CLASSNAME_PREFIX, "");
    }
}
