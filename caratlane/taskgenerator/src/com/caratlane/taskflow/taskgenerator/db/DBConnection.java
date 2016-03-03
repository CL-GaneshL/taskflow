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

import java.util.List;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
public interface DBConnection {

    /**
     * Open a database connection.
     *
     * @return
     * @throws DBException if fails to open a connection.
     */
    public DBConnection open() throws DBException;

    /**
     * Closes a database connection,
     */
    public void close();

    public DBTransaction getTransaction();

    /**
     * Clear the persistence context, causing all managed entities to become
     * detached. Changes made to entities that have not been flushed to the
     * database will not be persisted.
     */
    public void clear();

    /**
     * Get the properties and hints and associated values that are in effect for
     * the entity manager.
     *
     * @param key the property key
     * @return a property in effect for entity manager.
     */
    public String getProperty(final String key);

    /**
     * Execute a SELECT query and return the query results as a typed List.
     *
     * @param <T> query result type
     * @param name the name of a query defined in metadata. Cannot be null.
     * @param resultClass the type of the query result. Cannot be null.
     * @param parameters parameter names and values
     * @return a list of the results
     * @throws DBException if fails to query the database
     */
//    public <T extends HexaDBEntity> List<T> query(@NotNull final String name, @NotNull final Class<T> resultClass, Object... parameters) throws DBException;
    public <T> List<T> query(@NotNull final String name, @NotNull final Class<T> resultClass, Object... parameters) throws DBException;

    /**
     * Execute a SELECT query that returns a single result.
     *
     * @param <T> query result type
     * @param name the name of a query defined in metadata. Cannot be null.
     * @param resultClass the type of the query result. Cannot be null.
     * @param parameters parameter names and values
     * @return a list of the results
     * @throws DBException if fails to query the database
     */
//    public <T extends HexaDBEntity> T querySingleResult(@NotNull final String name, @NotNull final Class<T> resultClass, Object... parameters) throws DBException;
    public <T> T querySingleResult(@NotNull final String name, @NotNull final Class<T> resultClass, Object... parameters) throws DBException;

    /**
     *
     * @return
     */
    public EntityManager getEntityManager();

    /**
     *
     * @return @throws DBException
     */
    public long getLastID() throws DBException;
    /**
     *
     * @param schema
     * @param table
     * @param filename
     * @throws HexaDBDatabaseException
     */
//    public void dumpTableToFile(
//            final String schema,
//            final String table,
//            final String filename
//    ) throws DBException;
    /**
     *
     * @return @throws DBException
     */
//    public List<String> getAllTableNames() throws DBException;
}
