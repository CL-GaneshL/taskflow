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

import com.caratlane.taskflow.taskgenerator.db.DBException;
import java.io.File;
import java.io.IOException;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
public class HexaDBLoadFolderImpl implements HexaDBLoadFolder {

    public class LogMessages {

        public static final String UNEXPECTED_DATABASE_VENDOR
                = "Unexpected database vendor , vendor = [{0}].";

    }

    final File root;

    /**
     * Logger for this class.
     *
     */
//    private static final HexaDBLogger LOG = HexaDBLogManager.getLogger(HexaDBLoadFolderImpl.class);
    /**
     *
     * @param vendor
     */
    HexaDBLoadFolderImpl(@NotNull final HexaDBDatabaseVendor vendor) throws DBException {

//        switch (vendor) {
//
//            case H2:
//                // create the directory into the system tmp folder
//                this.root = new File(vendor.getLoadDirectory());
//                this.root.mkdirs();
//
//                break;
//
//            case MYSQL:
//                // the directory already exists defined by 
//                // the my.ini option : --secure-file-priv=path
//                this.root = new File(vendor.getLoadDirectory());
//                break;
//
//            default:
//                throw new DBException(UNEXPECTED_DATABASE_VENDOR, this);
//        }
        this.root = null;
    }

    @Override
    public File getFile(String fileName) {
        return new File(this.root, fileName);
    }

    @Override
    public void createFile(File file) throws DBException {
        try {
            file.createNewFile();

        } catch (IOException ex) {

//            DBException.logErrorStackTrace(LOG, ex);
            throw new DBException(ex);
        }
    }

    @Override
    public File createFile(String fileName) throws DBException {

        File createdFile = null;

        try {
            createdFile = new File(this.root, fileName);
            createdFile.createNewFile();

        } catch (IOException ex) {

//            DBException.logErrorStackTrace(LOG, ex);
            throw new DBException(ex);
        }

        return createdFile;
    }

    @Override
    public void deleteAllFiles() {
        File[] files = this.root.listFiles();
        if (files != null) {
            for (File each : files) {
                recursiveDelete(each);
            }
        }
    }

    private void recursiveDelete(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File each : files) {
                recursiveDelete(each);
            }
        }
        file.delete();
    }

}
