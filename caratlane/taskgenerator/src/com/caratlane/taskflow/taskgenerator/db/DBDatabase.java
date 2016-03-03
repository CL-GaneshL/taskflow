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

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WD Media
 */
public interface DBDatabase {

    /**
     * Returns a new transaction.
     *
     * @return HexaDBTransaction instance
     * @throws DBException
     *
     */
    public DBConnection getConnection() throws DBException;

    /**
     * Open a database connection.
     *
     * @throws DBException if fails to open a connection.
     */
//    public void open() throws DBException;

    /**
     * Closes a database connection,
     */
    public void close();

    /**
     * Get the properties and hints and associated values that are in effect for
     * the entity manager.
     *
     * @param key the property key
     * @return a property in effect for entity manager.
     */
//    public String getProperty(final String key);

    /**
     * Returns true is autocommit is on.
     *
     * @return true is autocommit is on, false otherwise.
     */
//    public boolean isAutocommit();

    /**
     *
     * @return @throws DBException
     */
//    public HexaDBDatabaseVendor getDatabaseVendor() throws DBException;

    /**
     *
     * @return @throws DBException
     */
//    public HexaDBConnectionMode getConnectionMode() throws DBException;

    /**
     *
     * @return
     */
//    public String getPersistenceUnitName();
    /**
     *
     * @return
     */
    public EntityManagerFactory getEntityManagerFactory();

    /**
     *
     * @return @throws HexaDBDatabaseException
     */
//    public HexaDBLoadFolder getLoadFolder() throws DBException;
    /**
     *
     * @return
     */
//    public boolean isOpen();

    /**
     *
     * @return
     */
//    public boolean isClose();

}
