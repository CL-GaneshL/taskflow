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

import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
public interface DBTransaction {

    /**
     * Start a resource transaction.
     *
     * @throws DBException if fails to start the transaction.
     */
    void begin() throws DBException;

    /**
     * This method calculates ...
     *
     * @throws DBException if the commit fails.
     * @throws
     * com.wdmedia.hexactitude.db.exeptions.HexaDBDatabaseRollbackException
     */
    void commit() throws DBException, DBRollbackException;

    /**
     * Commit the current resource transaction, writing any unflushed changes to
     * the database.
     *
     * @throws DBException if the rollback fails.
     */
    void rollback() throws DBException;

    /**
     * Synchronize the persistence context to the underlying database.
     *
     * @throws com.wdmedia.hexactitude.db.exeptions.HexaDBDatabaseException
     */
    public void flush() throws DBException;

    /**
     * Make an instance managed and persistent.
     *
     * @param <T>
     * @param entity entity instance
     * @throws DBException if the persist operation fails,
     */
//    public <T extends HexaDBEntity> void persist(@NotNull
//            final T entity) throws DBException;
    public <T> void persist(@NotNull final T entity) throws DBException;

    public <T> long persistGetId(@NotNull final T entity) throws DBException;

    /**
     * Delete entities.
     *
     * @param name the name of a query defined in metadata. Cannot be null.
     * @param parameters parameter names and values
     * @return a list of the results
     * @throws DBException if fails to query the database
     */
    public int delete(@NotNull
            final String name, Object... parameters) throws DBException;

    /**
     * Updates an entity. Merges the state of the given entity into the current
     * persistence context.
     *
     * @param <T> Object type to be updated.
     * @param entity the entity to be updated.
     * @return the managed instance that the state was merged to.
     * @throws DBException if the update fails.
     */
    public <T extends HexaDBEntity> T update(@NotNull T entity) throws DBException;

    /**
     * Execute native SQL code.
     *
     * @param sql the SQL code to execute
     * @return returns the number of updates
     * @throws DBException if fails to execute the SQL code
     */
    public int executeNativeSQL(@NotNull String sql) throws DBException;

    /**
     * Execute a native SQL script.
     *
     * @param url the script file url.
     * @return returns the number of updates
     * @throws HexaDBDatabaseException if fails to execute the script.
     */
//    public int runScript(@NotNull URL url) throws DBException;
//    public void callProcedure(@NotNull String procedureName, Object... parameters) throws DBException;
}
