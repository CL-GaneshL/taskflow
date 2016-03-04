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

import static com.caratlane.taskflow.taskgenerator.db.DBConnectionImpl.ExceptionMessages.CONNECTIONIMPL_EXCEP_CANNOT_OPEN_CONNECTION;
import static com.caratlane.taskflow.taskgenerator.db.DBConnectionImpl.ExceptionMessages.CONNECTIONIMPL_EXCEP_FAILED_GET_LAST_ID;
import static com.caratlane.taskflow.taskgenerator.db.DBConnectionImpl.ExceptionMessages.CONNECTIONIMPL_EXCEP_FAILED_QUERY_DATABASE;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
class DBConnectionImpl implements DBConnection {

    public class ExceptionMessages {

        public static final String CONNECTIONIMPL_EXCEP_CANNOT_OPEN_CONNECTION
                = "Cannot open connection, database = [{0}] : [{1}].";
        public static final String CONNECTIONIMPL_EXCEP_FAILED_QUERY_DATABASE
                = "Failed to query database, query = [{0}] : [{1}].";
        public static final String CONNECTIONIMPL_EXCEP_FAILED_GET_LAST_ID
                = "Failed to get last id, database = [{0}] : [{2}].";

        public static final String EXCEP_FAILED_TO_DUMP_TABLE_TO_FILE
                = "Failed to dump table to file, sql = [{0}] : [{1}].";

        public static final String EXCEP_FAILED_TO_GET_ALL_TABLE_NAMES
                = "Failed to dump table to file, sql = [{0}] : [{1}].";

        public static final String EXCEP_FAILED_RUN_SCRIPT
                = "Failed to run sql script, database = [{0}], connection = [{1}], script = [{2}] : [{3}].";

    }

    protected class LogMessages {

        public static final String OPENING_CONNECTION
                = "[{0}] : Opening connection.";
        public static final String FAILED_OPEN_CONNECTION
                = "[{0}] : Failed to open connection.";
        public static final String OPENED_CONNECTION
                = "[{0}] : Opened connection.";

        public static final String CLOSING_CONNECTION
                = "[{0}] : Closing connection.";
        public static final String CLOSED_CONNECTION
                = "[{0}] : Closed connection.";

        public static final String CLEARING_CONNECTION
                = "[{0}] : Clearing connection.";
        public static final String CLEARED_CONNECTION
                = "[{0}] : Clearded connection.";

        public static final String FAILED_QUERY
                = "[{0}] : Failed to query db : query : [{1}].";
        public static final String SUCCESSFUL_QUERY
                = "[{0}] : Succeed to query db : query : [{1}].";
        public static final String QUERY
                = "[{0}] : Querying db : query : [{1}], result class : [{2}].";
        public static final String RESULT_QUERY
                = "[{0}] : Querying db : Nb objects retrieved : [{1}].";

        public static final String FAILED_SINGLE_RESULT_QUERY
                = "[{0}] : Failed to (single result) query db : query : [{1}].";
        public static final String SUCCESSFUL_SINGLE_RESULT_QUERY
                = "[{0}] : Succeed to (single result) query db : query : [{1}].";
        public static final String SINGLE_RESULT_QUERY
                = "[{0}] : (single result) Querying db : query : [{1}], result class : [{2}].";
        public static final String RESULT_SINGLE_RESULT_QUERY
                = "[{0}] : (single result) Querying db : Nb objects retrieved : [{1}].";

        public static final String FAILED_GET_LAST_ID
                = "[{0}] : Failed to get last Id.";
        public static final String SUCCESSFUL_GET_LAST_ID
                = "[{0}] : Succeed to get last Id.";
        public static final String GET_LAST_ID_NATIVE_SQL
                = "[{0}] : Get last Id native SQL : sql : [{1}].";
        public static final String GET_LAST_ID
                = "[{0}] : Get last Id.";
        public static final String RESULT_GET_LAST_ID
                = "[{0}] : Get last Id : Nb objects retrieved : [{1}].";

        public static final String DUMPING_TABLE_TO_FILE
                = "[{0}] : Dumping table to file : file : [{1}], schema : [{2}], table : [{3}].";
        public static final String DUMP_TABLE_TO_FILE_NATIVE_SQL
                = "[{0}] : Dumping table to file : sql : [{1}].";
        public static final String FAILED_TO_DUMP_TABLE_TO_FILE
                = "[{0}] : Failed to dump table to file : file : [{1}], schema : [{2}], table : [{3}].";
        public static final String DUMPED_TABLE_TO_FILE
                = "[{0}] : Dumped table to file.";
        public static final String DUMP_TABLE_TO_FILE_PATH
                = "[{0}] : Dumping table to file : file path : [{1}].";

        public static final String UNEXPECTED_DATABASE_VENDOR
                = "Unexpected database vendor , vendor = [{0}].";

        public static final String GETTING_ALL_TABLE_NAMES
                = "[{0}] : Getting all table's names.";
        public static final String GET_ALL_TABLE_NAMES_NATIVE_SQL
                = "[{0}] : Get all table's names : sql : [{1}].";
        public static final String FAILED_TO_GET_ALL_TABLE_NAMES
                = "[{0}] : Failed to get all table's names.";
        public static final String GOT_ALL_TABLE_NAMES
                = "[{0}] : Got all table's names.";

    }

    private final DBDatabase db;
    private final EntityManagerFactory emf;
    private EntityManager em = null;
//    private final int conId;
//    private final HexaDBDatabaseVendor vendor;

//    private static int conNumber;
    /**
     * Logger for this class.
     *
     */
//    private static final HexaDBLogger LOG = HexaDBLogManager.getLogger(DBConnectionImpl.class);
//    static {
//        // keep track of connections by giving them an unique number
//        DBConnectionImpl.conNumber = 0;
//    }
    DBConnectionImpl(@NotNull DBDatabase db) throws DBException {

        this.db = db;
        this.emf = db.getEntityManagerFactory();
//         this.em = this.emf.createEntityManager();
//        this.em = db.getEntityManager();
//        this.vendor = db.getDatabaseVendor();

//        DBConnectionImpl.conNumber++;
//        this.conId = DBConnectionImpl.conNumber;
        // equals is done against the coID field
//        super.addEqualsOmitFieldName("em");
//        super.addEqualsOmitFieldName("emf");
//        super.addEqualsOmitFieldName("db");
//        super.addEqualsOmitFieldName("vendor");
//
//        super.addToStringOmitFieldName("tx");
//        super.addToStringOmitFieldName("em");
//        super.addToStringOmitFieldName("emf");
//
//        this.setOmitClassName(true);
    }

    @Override
    public DBConnection open() throws DBException {

//        assert this.emf != null && this.emf.isOpen() : ("emf = " + this.emf);
        assert this.em == null : ("em = " + this.em);

//        LOG.debug(OPENING_CONNECTION, this);
        try {
            // Obtain an entity manager and a transaction
//            this.em = this.emf.createEntityManager();
//            this.em = this.db.getEntityManager();

            this.em = this.emf.createEntityManager();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_OPEN_CONNECTION, this);
            this.close();
            this.em = null;

//            logErrorStackTrace(LOG, excep);
            throw new DBException(CONNECTIONIMPL_EXCEP_CANNOT_OPEN_CONNECTION, this.db, excep.getMessage());
        }

//        LOG.debug(OPENED_CONNECTION, this);
        return this;
    }

    @Override
    public void close() {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(CLOSING_CONNECTION, this);
        if (this.em != null) {
            if (this.em.isOpen()) {
                this.em.close();
            }
            this.em = null;
        }

//        LOG.debug(CLOSED_CONNECTION, this);
    }

    @Override
    public void clear() {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(CLEARING_CONNECTION, this);
        this.em.clear();

//        LOG.debug(CLEARED_CONNECTION, this);
    }

    @Override
    @NotNull
    public DBTransaction getTransaction() {
        return new DBTransactionImpl(this);
    }

    @Override
    public String getProperty(@NotNull String key) {
        return this._getProperty(key);
    }

    @Override
    @NotNull
    public <T> List< T> query(
            @NotNull
            final String name,
            @NotNull
            final Class<T> resultClass,
            Object... parameters)
            throws DBException {

//      public <T extends HexaDBEntity> List< T> query(
//            @NotNull
//            final String name,
//            @NotNull
//            final Class<T> resultClass,
//            Object... parameters)
//            throws DBException {
        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(QUERY, this, name, resultClass);
        List<T> result = null;

        try {
            TypedQuery<T> query = this.em.createNamedQuery(name, resultClass);
            if (parameters.length > 0) {
                for (int paramNumber = 0; paramNumber < parameters.length; paramNumber += 2) {
                    String paramName = (String) parameters[paramNumber];
                    Object object = parameters[paramNumber + 1];

                    query = query.setParameter(paramName, object);
                }
            }
            result = query.getResultList();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_QUERY, this, name);
//            DBException.logErrorStackTrace(LOG, excep);
            throw new DBException(CONNECTIONIMPL_EXCEP_FAILED_QUERY_DATABASE, name, excep.getMessage());
        }

//        LOG.debug(SUCCESSFUL_QUERY, this, name);
//        LOG.debug(RESULT_QUERY, this, result.size());
        return result;
    }

    @Override
    @NotNull
//    public <T extends HexaDBEntity> T querySingleResult(
//            @NotNull final String name,
//            @NotNull final Class<T> resultClass,
//            Object... parameters)
//            throws DBException {

    public <T> T querySingleResult(
            @NotNull final String name,
            @NotNull final Class<T> resultClass,
            Object... parameters)
            throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(SINGLE_RESULT_QUERY, this, name, resultClass);
        T result = null;

        try {
            TypedQuery<T> query = this.em.createNamedQuery(name, resultClass);
            if (parameters.length > 0) {
                for (int paramNumber = 0; paramNumber < parameters.length; paramNumber += 2) {
                    String paramName = (String) parameters[paramNumber];
                    Object object = parameters[paramNumber + 1];

                    query = query.setParameter(paramName, object);
                }
            }
            result = query.getSingleResult();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_SINGLE_RESULT_QUERY, this, name);
//            DBException.logErrorStackTrace(LOG, excep);
            throw new DBException(CONNECTIONIMPL_EXCEP_FAILED_QUERY_DATABASE, name, excep.getMessage());
        }

//        LOG.debug(SUCCESSFUL_SINGLE_RESULT_QUERY, this, name);
//        LOG.debug(RESULT_SINGLE_RESULT_QUERY, this, 1);
        return result;
    }

    /**
     *
     * @return @throws DBException
     */
    @Override
    public long getLastID() throws DBException {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

//        LOG.debug(GET_LAST_ID, this);
        long result;

        try {

            final String sql = "SELECT LAST_INSERT_ID()";
//            final String sql = this.vendor.getLastIdInsertedSQLCommand();

//            LOG.debug(GET_LAST_ID_NATIVE_SQL, this, sql);
            Query query = this.em.createNativeQuery(sql);
            final Object resultObj = query.getSingleResult();
            result = ((BigInteger) resultObj).longValue();

        } catch (RuntimeException excep) {

//            LOG.error(FAILED_GET_LAST_ID, this);
//            DBException.logErrorStackTrace(LOG, excep);
            throw new DBException(CONNECTIONIMPL_EXCEP_FAILED_GET_LAST_ID, this.db, excep.getMessage());
        }

//        LOG.debug(SUCCESSFUL_GET_LAST_ID, this);
//        LOG.debug(RESULT_GET_LAST_ID, this, result);
        return result;

    }

//    @Override
//    public void dumpTableToFile(
//            @NotNull final String schema,
//            @NotNull final String table,
//            @NotNull final String filename
//    ) throws DBException {
//
//        assert this.em != null && this.em.isOpen() : ("em = " + this.em);
//
////        LOG.debug(DUMPING_TABLE_TO_FILE, this, filename, schema, table);
//        final HexaDBLoadFolder loadFolder = this.db.getLoadFolder();
//        File dumpFile = loadFolder.getFile(filename);
//
//        try {
//
//            switch (this.vendor) {
//
//                case H2:
//
//                    // make sure the load directory exists
//                    final String workingDir = System.getProperty("user.dir");
//                    final File workingDirFile = new File(workingDir);
//                    final Path workingDirPath = workingDirFile.toPath();
//
//                    final Path dumpFilePathH2 = dumpFile.toPath();
//
//                    // path relative to the working directory
//                    final String dumpFileRelativePath = workingDirPath.relativize(dumpFilePathH2).toString();
//
//                    // H2 : the file must be already created.
//                    dumpFile.delete();
//                    dumpFile.createNewFile();
//
////                    LOG.debug(DUMP_TABLE_TO_FILE_PATH, this, dumpFileRelativePath);
//                    final String sql = this.vendor.getDumpTableToFileSQLCommand(dumpFileRelativePath, schema, table);
////                    LOG.debug(DUMP_TABLE_TO_FILE_NATIVE_SQL, this, sql);
//                    final Query query = this.em.createNativeQuery(sql);
//                    query.getResultList();
//
//                    break;
//
//                case MYSQL:
//
//                    // make sure the file does not already exist
//                    // in the dump directory ( MySQL requierement )
//                    dumpFile.delete();
//
//                    final String dumpFileStr = dumpFile.toString().replace("\\", "\\\\");
////                    LOG.debug(DUMP_TABLE_TO_FILE_PATH, this, dumpFile.toString());
//                    DBTransaction tx = this.getTransaction();
////                    tx.callProcedure("dumpTable",
////                            "p_file", String.class, ParameterMode.IN, dumpFileStr,
////                            "p_schema", String.class, ParameterMode.IN, schema,
////                            "p_table", String.class, ParameterMode.IN, table);
//
//                    break;
//
//                default:
//
//                    throw new DBException(UNEXPECTED_DATABASE_VENDOR, this);
//            }
//
//        } catch (IOException | RuntimeException excep) {
//
////            LOG.error(FAILED_TO_DUMP_TABLE_TO_FILE, this, dumpFile.toString(), schema, table);
////            DBException.logErrorStackTrace(LOG, excep);
//            throw new DBException(
//                    EXCEP_FAILED_TO_DUMP_TABLE_TO_FILE, excep.getMessage());
//        }
//
////        LOG.debug(DUMPED_TABLE_TO_FILE, this);
//    }
//    @Override
//    public List<String> getAllTableNames() throws DBException {
//
//        assert this.em != null && this.em.isOpen() : ("em = " + this.em);
//
////        LOG.debug(GETTING_ALL_TABLE_NAMES, this);
//        List<String> names = null;
//        String sql = null;
//
//        try {
//            sql = this.vendor.getAllTableNamesSQLCommand();
//
////            LOG.debug(GET_ALL_TABLE_NAMES_NATIVE_SQL, this, sql);
//            Query query = this.em.createNativeQuery(sql);
//
//            switch (this.vendor) {
//
//                case MYSQL:
//                    @SuppressWarnings("unchecked")
//                    final List<String> namesMySQL = query.getResultList();
//                    names = namesMySQL;
//                    break;
//
//                case H2:
//
//                    names = new LinkedList<>();
//                    @SuppressWarnings("unchecked") List<Object[]> namesH2 = query.getResultList();
//                    for (Object[] array : namesH2) {
//
//                        String schema = ((String) array[1]).toLowerCase();
//
////                        if (schema.equals(HEXACTITUDEDB_SCHEMA.toLowerCase())) {
////                            names.add((String) array[0]);
////                        }
//                    }
//
//                    break;
//
//                default:
//                    throw new DBException(UNEXPECTED_DATABASE_VENDOR, this);
//            }
//        } catch (RuntimeException excep) {
//
////            LOG.error(FAILED_TO_GET_ALL_TABLE_NAMES, this);
////            DBException.logErrorStackTrace(LOG, excep);
//            throw new DBException(
//                    EXCEP_FAILED_TO_GET_ALL_TABLE_NAMES, sql, excep.getMessage());
//        }
//
////        LOG.debug(GOT_ALL_TABLE_NAMES, this);
//        return names;
//
//    }
    @Override
    @NotNull
    public EntityManager getEntityManager() {

        return this.em;
    }

    /**
     * Get the properties and hints and associated values that are in effect for
     * the entity manager.
     *
     * @param key the property key
     * @return a property in effect for entity manager.
     */
    private String _getProperty(final String key) {

        assert this.em != null && this.em.isOpen() : ("em = " + this.em);

        Object value = null;
        Map<String, Object> props;

        if (this.em != null && this.em.isOpen()) {
            props = this.em.getProperties();
            value = props.get(key);
        }

        return value != null ? value.toString() : null;
    }

}
