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
import java.util.Properties;
import java.util.logging.Level;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
class DBDatabaseImpl implements DBDatabase {

    public class ExceptionMessages {

        public static final String DATABASEIMPL_EXCEP_FAIL_OPEN_DATABASE
                = "Failed to open database, persistence unit name = [{0}] : [{1}].";
        public static final String DATABASEIMPL_EXCEP_HIBERNATE_URL_CONNECTION_NOT_DEFINED
                = "Hibernate url connection is not defined, persistence unit name = [{0}].";
        public static final String DATABASEIMPL_EXCEP_HIBERNATE_DIALECT_NOT_DEFINED
                = "Hibernate dialect is not defined, persistence unit name = [{0}].";
        public static final String DATABASEIMPL_EXCEP_UNKNOWN_DATABASE_VENDOR
                = "Unknown database vendor, persistence unit name = [{0}].";
    }

    protected class LogMessages {

        public static final String OPENING_DATABASE = "[{0}] : Opening database.";
        public static final String FAILED_OPEN_DATABASE = "[{0}] : Failed to open database.";
        public static final String OPENED_DATABASE = "[{0}] : Opened database.";
        public static final String CLOSING_DATABASE = "[{0}] : Closing database.";
        public static final String FAILED_CLOSE_DATABASE = "[{0}] : Failed to close database.";
        public static final String CLOSED_DATABASE = "[{0}] : Closed database.";
    }

//    private final EntityManager em;
    final EntityManagerFactory emf;

    /**
     * Sole constructor.
     *
     * @throws com.wdmedia.hexactitude.db.exeptions.HexaDBDatabaseException
     */
    public DBDatabaseImpl(
            String host,
            String port,
            String database,
            String username,
            String password
    ) throws DBException {

        this.emf = this.createEntityManagerFactory(host, port, database, username, password);
//        this.em = this.emf.createEntityManager();
    }

    private EntityManagerFactory createEntityManagerFactory(
            final String host,
            final String port,
            final String database,
            final String username,
            final String password
    ) throws DBException {

        final EntityManagerFactory entityManagerFactory;

        try {
            Properties properties = new Properties();
            final String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "Connection Url : [{0}]", url);
            // ---------------------------------------------------------------------

            properties.put("javax.persistence.provider", "org.hibernate.jpa.HibernatePersistenceProvider");
            properties.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
            properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
            properties.put("hibernate.archive.autodetection", "class");
            properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
            properties.put("hibernate.connection.url", url);
            properties.put("hibernate.connection.username", username);
            properties.put("hibernate.connection.password", password);
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.generate_statistics", "false");
            properties.put("hibernate.use_sql_comments", "false");
            properties.put("hibernate.connection.autocommit", "false");

            entityManagerFactory
                    = Persistence.createEntityManagerFactory("TaskGeneratorPersistenceUnit", properties);

        } catch (NumberFormatException ex) {
            throw new DBException(ex);
        }

        return entityManagerFactory;
    }

    @Override
    public void close() {

        if (this.emf != null) {
            this.emf.close();
        }
    }

    @Override
    @NotNull
    public DBConnection getConnection() throws DBException {
        return new DBConnectionImpl(this);
    }

    @Override
    @NotNull
    public EntityManagerFactory getEntityManagerFactory() {

        return this.emf;
    }

//    @Override
//    @NotNull
//    public EntityManager getEntityManager() {
//
//        return this.em;
//    }
//    @Override
//    public void open() throws DBException {
//
//        assert this.emf == null : ("emf = " + this.emf);
//        assert this.pun != null : ("pun = " + this.pun);
//
////        LOG.debug(OPENING_DATABASE, this);
//        try {
//            this.emf = Persistence.createEntityManagerFactory(this.pun);
//
//            if (this.emf == null) {
////                LOG.error(FAILED_OPEN_DATABASE, this);
//                throw new DBException(DATABASEIMPL_EXCEP_FAIL_OPEN_DATABASE, this.pun);
//            }
//
//        } catch (RuntimeException excep) {
//
////            LOG.error(FAILED_OPEN_DATABASE, this);
////            logErrorStackTrace(LOG, excep);
//            throw new DBException(
//                    DATABASEIMPL_EXCEP_FAIL_OPEN_DATABASE, this.pun, excep.getMessage());
//        }
//
////        LOG.info(OPENED_DATABASE, this);
//
////        LOG.info(OPENED_DATABASE, this);
//    }
//    @Override
//    public void close() {
//
////        LOG.debug(CLOSING_DATABASE, this);
//        try {
//            if (this.emf != null && this.emf.isOpen()) {
//                this.emf.close();
//            }
//
//        } catch (RuntimeException excep) {
//
////            LOG.debug(FAILED_CLOSE_DATABASE, this);
////            logErrorStackTrace(LOG, excep);
//        } finally {
//            this.emf = null;
//        }
//
////        LOG.debug(CLOSED_DATABASE, this);
//    }
//    @Override
//    @NotNull
//    public String getPersistenceUnitName() {
//
//        return this.pun;
//    }
    /**
     * Returns true is autocommit is on.
     *
     * @return true is autocommit is on, false otherwise.
     */
//    @Override
//    public boolean isAutocommit() {
//
//        final String prop = this.getProperty("hibernate.connection.autocommit");
//        return Boolean.valueOf(prop);
//    }
//    @Override
//    @NotNull
//    public HexaDBDatabaseVendor getDatabaseVendor() throws DBException {
//
//        final HexaDBDatabaseVendor vendor;
//        final String url = this.getProperty("hibernate.connection.url");
//
//        if (url == null) {
//            throw new DBException(
//                    DATABASEIMPL_EXCEP_HIBERNATE_URL_CONNECTION_NOT_DEFINED, this.pun);
//        }
//
//        if (url.toLowerCase().contains(":mysql:")) {
//            vendor = MYSQL;
//        } else if (url.toLowerCase().contains(":h2:")) {
//            vendor = H2;
//        } else {
//            vendor = UNKNOWN;
//        }
//
//        return vendor;
//    }
    /**
     *
     * @return
     */
//    @Override
//    @NotNull
//    public HexaDBConnectionMode getConnectionMode() throws DBException {
//
//        final HexaDBConnectionMode mode;
//        final HexaDBDatabaseVendor vendor = this.getDatabaseVendor();
//        final String url = this.getProperty("hibernate.connection.url");
//
//        if (url != null) {
//        } else {
//            throw new DBException(
//                    DATABASEIMPL_EXCEP_HIBERNATE_DIALECT_NOT_DEFINED, this.pun);
//        }
//
//        switch (vendor) {
//
//            case MYSQL:
//                if (url.toLowerCase().contains(":localhost:")) {
//                    mode = LOCAL;
//                } else {
//                    mode = HexaDBConnectionMode.UNKNOWN;
//                }
//                break;
//
//            case H2:
//                if (url.toLowerCase().contains(":file:")) {
//                    mode = LOCAL;
//                } else if (url.toLowerCase().contains(":mem:")) {
//                    mode = MEMORY;
//                } else if (url.toLowerCase().contains(":ssl:") || url.toLowerCase().contains(":tcp:")) {
//                    mode = REMOTE;
//                } else {
//                    mode = HexaDBConnectionMode.UNKNOWN;
//                }
//                break;
//
//            default:
//                throw new DBException(
//                        DATABASEIMPL_EXCEP_UNKNOWN_DATABASE_VENDOR, this.pun);
//        }
//
//        return mode;
//    }
//    @Override
//    public HexaDBLoadFolder getLoadFolder() throws DBException {
//
//        return new HexaDBLoadFolderImpl(this.getDatabaseVendor());
//    }
    /**
     * Get the properties and hints and associated values that are in effect for
     * the entity manager.
     *
     * @param key the property key
     * @return a property in effect for entity manager.
     */
//    @Override
//    public String getProperty(final String key) {
//
//        assert this.emf != null && this.emf.isOpen() : ("emf = " + this.emf);
//
//        Object value = null;
//        Map<String, Object> props;
//
//        if (this.emf.isOpen()) {
//            props = this.emf.getProperties();
//            value = props.get(key);
//        }
//
//        return value != null ? value.toString() : null;
//    }
//    @Override
//    public boolean isOpen() {
//
//        return this.emf != null && this.emf.isOpen();
//    }
//    @Override
//    public boolean isClose() {
//
//        return this.emf == null || !this.emf.isOpen();
//    }
}
