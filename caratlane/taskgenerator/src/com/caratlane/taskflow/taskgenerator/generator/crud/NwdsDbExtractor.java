/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.crud;

import com.caratlane.taskflow.taskgenerator.db.DBConnection;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbNonWorkingDays;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers._D_IN_THREE_MONTH;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers._D_TODAY;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.getQueryName;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_NWD_SUFFIX;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class NwdsDbExtractor {

    public static LinkedList<NonWorkingDay> getNonWorkingDays() throws TaskGeneratorException {

        final LinkedList<NonWorkingDay> nwds = new LinkedList<>();

        final String queryName = getQueryName(DbNonWorkingDays.class, FIND_NWD_SUFFIX);
        final List<DbNonWorkingDays> dbNwds;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbNwds = con.query(
                    queryName,
                    DbNonWorkingDays.class,
                    "now", _D_TODAY,
                    "max_date", _D_IN_THREE_MONTH
            );

            // create a Task fom a DbTask
            final Function<DbNonWorkingDays, NonWorkingDay> mapper = (DbNonWorkingDays t) -> new NonWorkingDay(t);

            // build the list of Tasks to be returned
            final Consumer<NonWorkingDay> action = (NonWorkingDay t) -> {
                nwds.add(t);
            };

            dbNwds.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String pattern = "Failed to retrieve Non Working Days from the database.";
            LogManager.getLogger().log(Level.SEVERE, pattern);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return nwds;
    }

}
