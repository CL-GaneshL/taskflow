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

/**
 *
 * @author WD Media
 */
public class HexaDBConstants {

    private HexaDBConstants() {
    }

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    public static final String SQL_DELIMITER = "$$";

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // database properties
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    /**
     * MYSQL TINYBLOB's size
     */
    public final static int MYSQL_TINY_BLOB_SIZE = 256;                          // 256 bytes
    /**
     * MYSQL BLOB's size
     */
    public final static int MYSQL_BLOB_SIZE = 65 * 1024;                        // 65 kb
    /**
     * MYSQL MEDIUMBLOB's size
     */
    public final static int MYSQL_MEDIUM_BLOB_SIZE = 16 * 1024 * 1024;           // 16 Mb
    /**
     * MYSQL LONGBLOB's size
     */
    public final static int MYSQL_LONG_BLOB_SIZE = 4 * 1024 * 1024 * 1024;       // 4 Gb

    /**
     *
     */
    public final static int MYSQL_BLOB_MAX_MANAGEABLE_SIZE = MYSQL_MEDIUM_BLOB_SIZE;

    /**
     *
     */
    public static final String MYSQL_LAST_ID_INSERTED_NATIVE_SQL = "SELECT LAST_INSERT_ID()";

    /**
     *
     */
    public static final String MYSQL_DUMP_TABLE_TO_FILE_NATIVE_SQL = null;

    /**
     *
     */
    public static final String H2_DUMP_TABLE_TO_FILE_NATIVE_SQL = "CALL CSVWRITE(''{0}'', ''SELECT * FROM `{1}`.`{2}`'')";

    /**
     *
     */
    public static final String MYSQL_TABLE_NAMES_NATIVE_SQL = "SELECT table_name FROM information_schema.tables WHERE TABLE_SCHEMA=DATABASE()";

    /**
     *
     */
//    public static final String H2_TABLE_NAMES_NATIVE_SQL = "SHOW TABLES FROM `" + HexaDBGlobalConstants.HEXACTITUDEDB_SCHEMA + "`";
  public static final String H2_TABLE_NAMES_NATIVE_SQL = "" ;

    /**
     *
     */
    public static final String MYSQL_LOAD_DIRECTORY = "C:\\ProgramData\\MySQL\\MySQL Server 5.6\\load";

    /**
     *
     */
    public static final String H2_LOAD_DIRECTORY = "H2\\load";

    /**
     * H2 BLOB's size
     */
    public final static int H2_BLOB_SIZE = 16 * 1024 * 1024;                // 16 Mb
    /**
     * H2 TINYBLOB's size
     */
    public final static int H2_TINYBLOB_SIZE = H2_BLOB_SIZE;
    /**
     * H2 MEDIUMBLOB's size
     */
    public final static int H2_MEDIUM_BLOB_SIZE = H2_BLOB_SIZE;
    /**
     * H2 LONGBLOB's size
     */
    public final static int H2_LONG_BLOB_SIZE = H2_BLOB_SIZE;

    /**
     *
     */
    public final static int H2_BLOB_MAX_MANAGEABLE_SIZE = H2_BLOB_SIZE;

    /**
     *
     */
    public static final String H2_LAST_ID_INSERTED_NATIVE_SQL = "CALL IDENTITY()";

//    // ------------------------------------------------------------------
//    // Named queries
//    // ------------------------------------------------------------------
    /**
     *
     */
//    public static final String FIND_ALL_SUFFIX = ".findAll";
//    /**
//     *
//     */
//    public static final String FIND_ALL_ORDERED_BY_REFERENCE_SUFFIX = ".findAllOrderedByReferenceDesc";
//    /**
//     *
//     */
//    public static final String FIND_BY_REFERENCE_SUFFIX = ".findByReference";
//    /**
//     *
//     */
//    public static final String DELETE_BY_REFERENCE_SUFFIX = ".deleteByReference";
//
//    /**
//     *
//     */
//    public static final String DELETE_ALL_SUFFIX = ".deleteAll";

}
