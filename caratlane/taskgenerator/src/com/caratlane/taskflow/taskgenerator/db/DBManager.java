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

import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.logging.Level;

/**
 *
 * @author WD Media
 */
public class DBManager {

    /**
     * Sole constructor (private). Preventing Singleton object instantiation
     * from outside
     */
    private DBManager() {
    }

    public static void initialize(
            String host,
            String port,
            String database,
            String username,
            String password
    ) throws DBException {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "Initilizing Database ...");
        // ---------------------------------------------------------------------

        if (DBManagerHolder.INSTANCE == null) {
            DBManagerHolder.INSTANCE
                    = new DBDatabaseImpl(
                            host,
                            port,
                            database,
                            username,
                            password
                    );
        }
    }

    public static void shutdown() {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "Database shutdown");
        // ---------------------------------------------------------------------

        if (DBManagerHolder.INSTANCE != null) {
            DBManagerHolder.INSTANCE.close();
        }
    }

    /**
     * Providing Global point of access.
     *
     * @param persistenceUnitName
     * @return the unique instance of the <code>HexaDBDatabaseManager</code>
     * class.
     */
//    public static DBDatabase databaseInstance(@NotNull String persistenceUnitName) {
//
//        if (HexaDBDatabaseManagerHolder.INSTANCE == null) {
//            HexaDBDatabaseManagerHolder.INSTANCE = new DBDatabaseImpl(persistenceUnitName);
//        }
//
//        return HexaDBDatabaseManagerHolder.INSTANCE;
//    }
    /**
     *
     * @return
     */
    public static DBDatabase getDatabaseInstance() {

        assert DBManagerHolder.INSTANCE != null : "DBManagerHolder.INSTANCE = null";
        return DBManagerHolder.INSTANCE;
    }

    /*
     * Static member holds only one instance of the <code>DBManager</code> class.
     */
    private static class DBManagerHolder {

        private static DBDatabase INSTANCE = null;
    }
}
