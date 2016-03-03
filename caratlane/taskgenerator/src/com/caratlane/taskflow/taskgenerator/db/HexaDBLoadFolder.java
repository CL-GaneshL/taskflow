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
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 */
public interface HexaDBLoadFolder {

    /**
     *
     * @param fileName
     * @return
     */
    public File getFile(@NotNull final String fileName);

    /**
     *
     * @param file
     * @throws DBException
     */
    public void createFile(@NotNull final File file) throws DBException;

    /**
     *
     * @param fileName
     * @return
     * @throws DBException
     */
    public File createFile(@NotNull final String fileName) throws DBException;

    /**
     *
     */
    public void deleteAllFiles();

}
