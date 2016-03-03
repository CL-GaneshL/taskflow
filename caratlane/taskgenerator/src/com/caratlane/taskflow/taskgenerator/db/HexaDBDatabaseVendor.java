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

import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_BLOB_MAX_MANAGEABLE_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_BLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_DUMP_TABLE_TO_FILE_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_LAST_ID_INSERTED_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_LOAD_DIRECTORY;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_LONG_BLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_MEDIUM_BLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_TABLE_NAMES_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.H2_TINYBLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_BLOB_MAX_MANAGEABLE_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_BLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_DUMP_TABLE_TO_FILE_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_LAST_ID_INSERTED_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_LOAD_DIRECTORY;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_LONG_BLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_MEDIUM_BLOB_SIZE;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_TABLE_NAMES_NATIVE_SQL;
import static com.caratlane.taskflow.taskgenerator.db.HexaDBConstants.MYSQL_TINY_BLOB_SIZE;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import java.io.File;
import java.text.MessageFormat;

/**
 *
 * @author WD Media
 */
public enum HexaDBDatabaseVendor {

    /**
     *
     */
    MYSQL(
            "mySQL",
            MYSQL_TINY_BLOB_SIZE,
            MYSQL_BLOB_SIZE,
            MYSQL_MEDIUM_BLOB_SIZE,
            MYSQL_LONG_BLOB_SIZE,
            MYSQL_BLOB_MAX_MANAGEABLE_SIZE,
            MYSQL_LAST_ID_INSERTED_NATIVE_SQL,
            MYSQL_DUMP_TABLE_TO_FILE_NATIVE_SQL,
            MYSQL_TABLE_NAMES_NATIVE_SQL,
            MYSQL_LOAD_DIRECTORY
    ),
    /**
     *
     */
    H2(
            "H2",
            H2_TINYBLOB_SIZE,
            H2_BLOB_SIZE,
            H2_MEDIUM_BLOB_SIZE,
            H2_LONG_BLOB_SIZE,
            H2_BLOB_MAX_MANAGEABLE_SIZE,
            H2_LAST_ID_INSERTED_NATIVE_SQL,
            H2_DUMP_TABLE_TO_FILE_NATIVE_SQL,
            H2_TABLE_NAMES_NATIVE_SQL,
            H2_LOAD_DIRECTORY
    ),
    /**
     *
     */
    UNKNOWN(
            "UNKNOWN VENDOR !",
            -1, -1, -1, -1, -1, null, null, null, null);

    final private String vendorName;
    final private int tinyBlobSize;
    final private int blobSize;
    final private int mediumBlobSize;
    final private int longBlobSize;
    final private int blobMaxManageableSize;
    final private String lastIdInsertedSQLCommand;
    final private String dumpTableToFileSQLCommand;
    final private String allTableNamesSQLCommand;
    final private String loadDirectory;

    private HexaDBDatabaseVendor(
            final String vendorName,
            final int tinyBlobSize,
            final int blobSize,
            final int mediumBlobSize,
            final int longBlobSize,
            final int blobMaxManageableSize,
            final String lastIdInsertedSQLCommand,
            final String dumpTableToFileSQLCommand,
            final String allTableNamesSQLCommand,
            final String loadDirectory) {

        this.vendorName = vendorName;
        this.tinyBlobSize = tinyBlobSize;
        this.blobSize = blobSize;
        this.mediumBlobSize = mediumBlobSize;
        this.longBlobSize = longBlobSize;
        this.blobMaxManageableSize = blobMaxManageableSize;
        this.lastIdInsertedSQLCommand = lastIdInsertedSQLCommand;
        this.dumpTableToFileSQLCommand = dumpTableToFileSQLCommand;
        this.allTableNamesSQLCommand = allTableNamesSQLCommand;
        this.loadDirectory = loadDirectory;
    }

    public String getVendorName() {
        return vendorName;
    }

    public int getTinyBlobSize() {
        return tinyBlobSize;
    }

    public int getBlobSize() {
        return blobSize;
    }

    public int getMediumBlobSize() {
        return mediumBlobSize;
    }

    public int getLongBlobSize() {
        return longBlobSize;
    }

    public int getBlobMaxManageableSize() {
        return blobMaxManageableSize;
    }

    public String getLastIdInsertedSQLCommand() {
        return lastIdInsertedSQLCommand;
    }

    /**
     * Build the native SQL statement allowing the table's dump onto a file.
     *
     *
     * @param dumpFilePath
     * @param schema
     * @param table the table's name.
     * @return the native SQL statement.
     * @throws DBException if failed to build the SQL command.
     */
    public String getDumpTableToFileSQLCommand(
            final String dumpFilePath,
            final String schema,
            final String table
    ) throws DBException {

        String sql = null;

        switch (this) {

            case MYSQL:

//                throw new HexaDBException("Unexpected call to getDumpTableToFileSQLCommand ! vendor = [{0}].", this);
                throw new UnsupportedOperationException("Unexpected call to getDumpTableToFileSQLCommand ! vendor = [{0}].");

            case H2:

                sql = MessageFormat.format(this.dumpTableToFileSQLCommand, dumpFilePath, schema, table).replace("\\", "\\\\");

                break;

            default:

                throw new DBException("Unexpected database vendor , vendor = [{0}].", this);
        }

        return sql;
    }

    public String getAllTableNamesSQLCommand() {

        return this.allTableNamesSQLCommand;
    }

    // TODO : use tmp filesystem instead
    private static final String HEXADB_TMP_SUBDIR = "HexaDB";

    public String getLoadDirectory() throws DBException {

        String path = null;

        switch (this) {

            case MYSQL:

                path = this.loadDirectory;
                break;

            case H2:

                // TODO : use tmp filesystem instead
                // system tmp directory
                final File hexaLoadDir = new File(System.getProperty("java.io.tmpdir"), HEXADB_TMP_SUBDIR);
                path = (new File(hexaLoadDir, this.loadDirectory)).toString();
                break;

            default:

                throw new DBException("Unexpected database vendor , vendor = [{0}].", this);
        }

        return path;
    }

    @Override
    public String toString() {
        return "[vendorName = " + this.vendorName + "]";
    }

}
