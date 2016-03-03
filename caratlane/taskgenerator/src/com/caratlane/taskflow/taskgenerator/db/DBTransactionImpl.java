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

import static com.caratlane.taskflow.taskgenerator.db.DBTransactionImpl.ExceptionMessages.TRANSACTIONIMPL_EXCEP_FAILED_COMMIT_TRANSACTION;
import static com.caratlane.taskflow.taskgenerator.db.DBTransactionImpl.ExceptionMessages.TRANSACTIONIMPL_EXCEP_FAILED_DELETE;
import static com.caratlane.taskflow.taskgenerator.db.DBTransactionImpl.ExceptionMessages.TRANSACTIONIMPL_EXCEP_FAILED_EXECUTE_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.DBTransactionImpl.ExceptionMessages.TRANSACTIONIMPL_EXCEP_FAILED_FLUSH_TRANSACTION;
import static com.caratlane.taskflow.taskgenerator.db.DBTransactionImpl.ExceptionMessages.TRANSACTIONIMPL_EXCEP_FAILED_PERSIST_ENTITY;
import static com.caratlane.taskflow.taskgenerator.db.DBTransactionImpl.ExceptionMessages.TRANSACTIONIMPL_EXCEP_FAILED_UPDATE_ENTITY;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
class DBTransactionImpl implements DBTransaction {
//class DBTransactionImpl extends HexaDBObject implements DBTransaction {

    public class ExceptionMessages {

        public static final String TRANSACTIONIMPL_EXCEP_CANNOT_OPEN_TRANSACTION
                = "Cannot open transaction, database = [{0}], connection = [{1}] : [{2}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_COMMIT_TRANSACTION
                = "Failed to commit transaction, : [{0}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_ROLLBACK_TRANSACTION
                = "Failed to rollback transaction, database = [{0}], connection = [{1}] : [{2}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_FLUSH_TRANSACTION
                = "Failed to flush transaction, database = [{0}], connection = [{1}] : [{2}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_PERSIST_ENTITY
                = "Failed to persist entity, entity = [{0}] : [{1}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_DELETE
                = "Failed to delete, database = [{0}], query = [{1}], entity = [{2}] : [{3}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_UPDATE_ENTITY
                = "Failed to update entity, database = [{0}], connection = [{1}], entity = [{2}] : [{3}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_EXECUTE_NATIVE_SQL
                = "Failed to execute native sql, database = [{0}], connection = [{1}], sql = [{2}] : [{3}].";
        public static final String TRANSACTIONIMPL_EXCEP_FAILED_RUN_SCRIPT
                = "Failed to run sql script, database = [{0}], connection = [{1}], script = [{2}] : [{3}].";

        public static final String EXCEP_FAILED_LOAD_DELIMITED_CODE
                = "Failed to load delimited code, database = [{0}], connection = [{1}], script = [{2}] : [{3}].";

        public static final String EXCEP_FAILED_CALL_PROCEDURE
                = "Failed to call procedure, database = [{0}], connection = [{1}], script = [{2}] : [{3}].";

    }

    protected class LogMessages {

        public static final String BEGINING_TRANSACTION
                = "[{0}] : Begining transaction.";
        public static final String FAILED_BEGIN_TRANSACTION
                = "[{0}] : Failed to begin transaction.";
        public static final String BEGUN_TRANSACTION
                = "[{0}] : Transaction begun.";

        public static final String CLOSING_TRANSACTION
                = "[{0}] : Closing connection.";
        public static final String CLOSED_TRANSACTION
                = "[{0}] : Closed connection.";

        public static final String COMMITTING_TRANSACTION
                = "[{0}] : Committing transaction.";
        public static final String SET_ROLLBACK_TRANSACTION
                = "[{0}] : Transaction set to rollback.";
        public static final String FAILED_COMMIT_TRANSACTION
                = "[{0}] : Failed to commit transaction.";
        public static final String COMMITTED_TRANSACTION
                = "[{0}] : Committed transaction.";

        public static final String ROLLBACKING_TRANSACTION
                = "[{0}] : Rollbacking transaction.";
        public static final String FAILED_ROLLBACK_TRANSACTION
                = "[{0}] : Failed to rollback transaction.";
        public static final String ROLLBACKED_TRANSACTION
                = "[{0}] : Rollbacked transaction.";

        public static final String FLUSHING_DATA
                = "[{0}] : Flushing transaction.";
        public static final String FAILED_FLUSH_DATA
                = "[{0}] : Failed to flush transaction.";
        public static final String FLUSHED_DATA
                = "[{0}] : Flushed transaction.";

        public static final String PERSISTING_ENTITY
                = "[{0}] : Persisting entity, entity : [{1}].";
        public static final String FAILED_PERSIST_ENTITY
                = "[{0}] : Failed to persist entity.";
        public static final String PERSISTED_ENTITY
                = "[{0}] : Persisted entity.";

        public static final String FAILED_DELETE
                = "[{0}] : Failed to delete : query : [{1}].";
        public static final String SUCCESSFUL_DELETE
                = "[{0}] : Succeed to delete : query : [{1}].";
        public static final String DELETING
                = "[{0}] : Deleting : query : [{1}].";
        public static final String RESULT_DELETE
                = "[{0}] : Deleted, Nb deletions : [{1}].";

        public static final String UPDATING_ENTITY
                = "[{0}] : Persisting entity, entity : [{1}].";
        public static final String FAILED_UPDATE_ENTITY
                = "[{0}] : Failed to persist entity.";
        public static final String UPDATED_ENTITY
                = "[{0}] : Persisted entity.";
        public static final String RESULT_UPDATE_ENTITY
                = "[{0}] : Persisted entity, updated entity : [{1}].";

        public static final String FAILED_EXECUTE_NATIVE_SQL
                = "[{0}] : Failed to execute native SQL : sql : [{1}].";
        public static final String SUCCESSFUL_EXECUTE_NATIVE_SQL
                = "[{0}] : Succeed to execute native SQL : sql : [{1}].";
        public static final String EXECUTE_NATIVE_SQL
                = "[{0}] : Excuting native SQL : query : [{1}].";
        public static final String RESULT_EXECUTE_NATIVE_SQL
                = "[{0}] : Excuted native SQL : Nb objects retrieved : [{1}].";

        public static final String FAILED_RUN_SCRIPT
                = "[{0}] : Failed to run script : url : [{1}].";
        public static final String RUN_SCRIPT_SQL
                = "[{0}] : Running script : sql : [{1}].";
        public static final String SUCCESSFUL_RUN_SCRIPT
                = "[{0}] : Succeed to run script : url : [{1}].";
        public static final String RUNNING_SCRIPT
                = "[{0}] : Running script : url : [{1}].";
        public static final String RESULT_RUN_SCRIPT
                = "[{0}] : Ran script : Nb of updates : [{1}].";

        public static final String FAILED_LOAD_DELIMITED_CODE
                = "[{0}] : Failed to load delimited code : url : [{1}].";
        public static final String LOAD_DELIMITED_CODE_SQL
                = "[{0}] : Loading delimited code : sql : [{1}].";

        public static final String CALLING_PROCEDURE
                = "[{0}] : Calling procedure, procedure name : [{1}].";
        public static final String FAILED_CALL_PROCEDURE
                = "[{0}] : Failed to call procedure : procedure name : [{1}].";
        public static final String CALLED_PROCEDURE
                = "[{0}] : Called procedure.";

    }

    private final String con;
    private final EntityManager em;
    private EntityTransaction tx;
    private final int txId;

    protected static int txNumber;

    /**
     * Logger for this class.
     *
     */
//    private static final HexaDBLogger LOG = HexaDBLogManager.getLogger(DBTransactionImpl.class);
    static {
        // keep track of transactions by giving them an unique number
        DBTransactionImpl.txNumber = 0;
    }

    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected DBTransactionImpl(@NotNull DBConnection con) {

        this.con = con.toString();
        this.em = con.getEntityManager();

        DBTransactionImpl.txNumber++;
        this.txId = DBTransactionImpl.txNumber;
//
//        // equals is done against the txID field
//        super.addEqualsOmitFieldName("tx");
//        super.addEqualsOmitFieldName("em");
//        super.addEqualsOmitFieldName("con");
//
//        super.addToStringOmitFieldName("tx");
//        super.addToStringOmitFieldName("em");
//        super.addToStringOmitFieldName("con");
//
//        this.setOmitClassName(true);
    }

    @Override
    public void begin() throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);
        assert this.tx == null : ("tx = " + this.tx);

//        try {
//            LOG.debug(BEGINING_TRANSACTION, this);
        this.tx = em.getTransaction();
        this.tx.begin();

//        } catch (RuntimeException excep) {
//
////            LOG.error(FAILED_BEGIN_TRANSACTION, this);
//            this.tx = null;
////            logErrorStackTrace(LOG, excep);
//            throw new DBException(
//                    TRANSACTIONIMPL_EXCEP_CANNOT_OPEN_TRANSACTION, this.con, excep.getMessage());
//        }
//        LOG.debug(BEGUN_TRANSACTION, this);
//        LOG.debug(BEGUN_TRANSACTION, this);
    }

    @Override
    public void commit() throws DBException, DBRollbackException {

        assert this.tx != null : ("tx = " + this.tx);

//        LOG.debug(COMMITTING_TRANSACTION, this);
        try {
            this.tx.commit();

        } catch (RollbackException excep) {

//            LOG.error(SET_ROLLBACK_TRANSACTION, this);
//            if (this.tx.isActive()) {
//                this.tx.setRollbackOnly();
//            }
//            logErrorStackTrace(LOG, excep);
            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_COMMIT_TRANSACTION, excep.getMessage());

        }
//        catch (RuntimeException excep) {
//
////            LOG.error(FAILED_COMMIT_TRANSACTION, this);
//            this.tx = null;
//
////            logErrorStackTrace(LOG, excep);
//            throw new DBException(
//                    TRANSACTIONIMPL_EXCEP_FAILED_COMMIT_TRANSACTION, excep.getMessage());
//        }

//        LOG.debug(COMMITTED_TRANSACTION, this);
//        LOG.debug(COMMITTED_TRANSACTION, this);
    }

    @Override
    public void rollback() throws DBException {

        assert this.tx != null : ("tx = " + this.tx);

//        LOG.debug(ROLLBACKING_TRANSACTION, this);
        try {
//            if (this.tx.isActive()) {
            this.tx.rollback();
//            }
        } //        catch (RuntimeException excep) {
        //
        ////            LOG.error(FAILED_ROLLBACK_TRANSACTION, this);
        //            if (this.tx.isActive()) {
        //                this.tx.setRollbackOnly();
        //            }
        //
        ////            logErrorStackTrace(LOG, excep);
        //            throw new DBException(
        //                    TRANSACTIONIMPL_EXCEP_FAILED_ROLLBACK_TRANSACTION, this.con, excep.getMessage());
        //
        //        } 
        finally {
            this.tx = null;
        }

//        LOG.debug(ROLLBACKED_TRANSACTION, this);
    }

    /**
     * Synchronize the persistence context to the underlying database.
     */
    @Override
    public void flush() throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(FLUSHING_DATA, this);
        try {
            this.em.flush();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_FLUSH_DATA, this);
//            logErrorStackTrace(LOG, excep);
            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_FLUSH_TRANSACTION, this.con, excep.getMessage());
        }

//        LOG.debug(FLUSHED_DATA, this);
//        LOG.debug(FLUSHED_DATA, this);
    }

    @Override
//    public <T extends HexaDBEntity> void persist(@NotNull T entity) throws DBException {
    public <T> void persist(@NotNull T entity) throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(PERSISTING_ENTITY, this, entity.toString());
        try {
            this.em.persist(entity);

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_PERSIST_ENTITY, this);
//            logErrorStackTrace(LOG, excep);
            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_PERSIST_ENTITY, this.con, entity.toString(), excep.getMessage());
        }

//        LOG.debug(PERSISTED_ENTITY, this);
//        LOG.debug(PERSISTED_ENTITY, this);
    }

    @Override
    public <T> long persistGetId(@NotNull final T entity) throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

        long result;

        try {
            this.em.persist(entity);

            final String sql = "SELECT LAST_INSERT_ID()";
            Query query = this.em.createNativeQuery(sql);
            final Object resultObj = query.getSingleResult();
            result = ((BigInteger) resultObj).longValue();

        } catch (RuntimeException excep) {

            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_PERSIST_ENTITY,  entity.toString(), excep.getMessage());
        }

        return result;
    }

    @Override
    public int delete(@NotNull String name, Object... parameters) throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(DELETING, this, name);
        int updates = 0;

        try {
            Query query = this.em.createNamedQuery(name);
            if (parameters.length > 0) {
                for (int paramNumber = 0; paramNumber < parameters.length; paramNumber += 2) {
                    String paramName = (String) parameters[paramNumber];
                    Object object = parameters[paramNumber + 1];

                    query = query.setParameter(paramName, object);
                }
            }
            updates = query.executeUpdate();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_DELETE, this, name);
//            logErrorStackTrace(LOG, excep);
            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_DELETE, this.con, name, excep.getMessage());
        }

//        LOG.debug(SUCCESSFUL_DELETE, this, name);
//        LOG.debug(RESULT_DELETE, this, updates);
        return updates;

    }

    @Override
    public <T extends HexaDBEntity> T update(@NotNull T entity) throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(UPDATING_ENTITY, this, entity);
        final T managedInstance;

        try {
            managedInstance = this.em.merge(entity);

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_UPDATE_ENTITY, this);
//            logErrorStackTrace(LOG, excep);
            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_UPDATE_ENTITY, this.con, entity.toString(), excep.getMessage());
        }

//        LOG.debug(UPDATED_ENTITY, this);
//        LOG.debug(RESULT_UPDATE_ENTITY, this, managedInstance.toString());
        return managedInstance;

    }

    @Override
    public int executeNativeSQL(@NotNull String sql) throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(EXECUTE_NATIVE_SQL, this, sql);
        int updates = 0;

        try {
            Query query = this.em.createNativeQuery(sql);
            updates = query.executeUpdate();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_EXECUTE_NATIVE_SQL, this, sql);
//            logErrorStackTrace(LOG, excep);
            throw new DBException(
                    TRANSACTIONIMPL_EXCEP_FAILED_EXECUTE_NATIVE_SQL, this.con, sql, excep.getMessage());
        }

//        LOG.debug(SUCCESSFUL_EXECUTE_NATIVE_SQL, this, sql);
//        LOG.debug(RESULT_EXECUTE_NATIVE_SQL, this, updates);
        return updates;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.con);
        hash = 71 * hash + Objects.hashCode(this.em);
        hash = 71 * hash + Objects.hashCode(this.tx);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DBTransactionImpl other = (DBTransactionImpl) obj;
        if (!Objects.equals(this.con, other.con)) {
            return false;
        }
        if (!Objects.equals(this.em, other.em)) {
            return false;
        }
        return Objects.equals(this.tx, other.tx);
    }

    @Override
    public String toString() {
        return "DBTransactionImpl{" + "con=" + con + ", em=" + em + ", tx=" + tx + '}';
    }

    /**
     *
     * @param url
     * @return
     * @throws DBException
     */
//    @Override
//    public int runScript(@NotNull URL url) throws DBException {
//
//        assert this.em != null && this.em.isOpen() : ("em = " + this.em);
//
//        LOG.debug(RUNNING_SCRIPT, this, url.toString());
//
//        int updates = 0;
//        BufferedReader bufferedReader = null;
//
//        try {
//            String line;
//            String sql = "";
//            boolean delimited = false;
//            String delimitedSql = null;
//
//            Reader streamReader = ConfigHelper.getConfigStreamReader(url.toString());
//            bufferedReader = new BufferedReader(streamReader);
//
//            while ((line = bufferedReader.readLine()) != null) {
//
//                // remove sql comments "/*....*/"
//                line = line.replaceAll("\\/\\*.*?\\*\\/ ?", "");
//
//                // remove sql comments "--"
//                int index = line.indexOf("--");
//                if (index != -1) {
//                    line = line.substring(0, index);
//                }
//
//                // remove leading and trailing whitespace
//                line = line.trim();
//
//                if (line.length() > 0) {
//
//                    // DELIMITER : very MySQL oriented !!!! Not portable.
//                    if (delimited == false) {
//
//                        // procedure starts here
//                        if (line.toUpperCase().contains("DELIMITER " + SQL_DELIMITER)) {
//                            delimitedSql = "";
//                            delimited = true;
//                            continue;
//                        }
//
//                    } else {
//
//                        // procedure ends here
//                        if (line.toUpperCase().contains("DELIMITER ;")) {
//
//                            try {
//                                LOG.info(LOAD_DELIMITED_CODE_SQL, this, delimitedSql);
//                                Query query = this.em.createNativeQuery(delimitedSql);
//                                updates += query.executeUpdate();
//
//                            } catch (RuntimeException ex) {
//
//                                LOG.error(FAILED_LOAD_DELIMITED_CODE, this, url.toString());
//
//                                logErrorStackTrace(LOG, ex);
//                                throw new DBException(
//                                        EXCEP_FAILED_LOAD_DELIMITED_CODE, this.con, url.toString(), ex.getMessage());
//                            }
//
//                            delimited = false;
//                            continue;
//                        }
//
//                        if (line.contains("END $$")) {
//                            line = "END;";
//                        }
//
//                        // procedure's code
//                        line = line.replaceAll("\\s+", " ");
//                        delimitedSql += " " + line;
//                        continue;
//                    }
//
//                    sql += " " + line;
//
//                    if (line.endsWith(";")) {
//
//                        // remove leading and trailing whitespace
//                        sql = sql.trim();
//
//                        if (!line.equals(";")) {
//
//                            // replace multiple whitespace characters 
//                            //(including space, tab, new line, etc.)
//                            sql = sql.replaceAll("\\s+", " ");
//
//                            LOG.info(RUN_SCRIPT_SQL, this, sql);
//
//                            try {
//                                Query query = this.em.createNativeQuery(sql);
//                                updates += query.executeUpdate();
//                                this.em.flush();
//
//                            } catch (RuntimeException ex) {
//
//                                LOG.error(FAILED_RUN_SCRIPT, this, url.toString());
//
//                                logErrorStackTrace(LOG, ex);
//                                throw new DBException(
//                                        TRANSACTIONIMPL_EXCEP_FAILED_RUN_SCRIPT, this.con, url.toString(), ex.getMessage());
//                            }
//                        }
//                        // starts a new statement
//                        sql = "";
//                    }
//                }
//            }
//
//        } catch (IOException ex) {
//
//            LOG.error(FAILED_RUN_SCRIPT, this, url.toString());
//
//            logErrorStackTrace(LOG, ex);
//            throw new DBException(
//                    TRANSACTIONIMPL_EXCEP_FAILED_RUN_SCRIPT, this.con, url.toString(), ex.getMessage());
//
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException excep) {
//                    logErrorStackTrace(LOG, excep);
//                }
//            }
//        }
//
//        LOG.debug(SUCCESSFUL_RUN_SCRIPT, this, url.toString());
//        LOG.debug(RESULT_RUN_SCRIPT, this, updates);
//
//        return updates;
//    }
//    @Override
//    public void callProcedure(@NotNull String procedureName, Object... parameters) throws DBException {
//
//        assert this.em != null && this.em.isOpen() : ("em = " + this.em);
//
//        LOG.debug(CALLING_PROCEDURE, this, procedureName);
//
//        try {
//
//            StoredProcedureQuery storedProcedureQuery = this.em.createStoredProcedureQuery(procedureName);
//            if (parameters.length > 0) {
//
//                for (int paramNumber = 0; paramNumber < parameters.length; paramNumber += 4) {
//                    String paramName = (String) parameters[paramNumber];
//
//                    Class<?> clazz = (Class<?>) parameters[paramNumber + 1];
//                    ParameterMode parameterMode = (ParameterMode) parameters[paramNumber + 2];
//
//                    storedProcedureQuery.registerStoredProcedureParameter(paramName, clazz, parameterMode);
//                }
//
//                for (int paramNumber = 0; paramNumber < parameters.length; paramNumber += 4) {
//                    String paramName = (String) parameters[paramNumber];
//                    Object object = parameters[paramNumber + 3];
//
//                    storedProcedureQuery.setParameter(paramName, object);
//                }
//            }
//
//            storedProcedureQuery.execute();
//
//        } catch (RuntimeException excep) {
//
//            LOG.error(FAILED_CALL_PROCEDURE, this, procedureName);
//
//            logErrorStackTrace(LOG, excep);
//            throw new DBException(
//                    EXCEP_FAILED_CALL_PROCEDURE, this.con, procedureName, excep.getMessage());
//        }
//
//        LOG.debug(CALLED_PROCEDURE, this);
//    }
}
