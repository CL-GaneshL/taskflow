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
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WD Media
 * @param <ITEM> Item type.
 */
public interface HexaDBItemQueryProvider<ITEM extends HexaDBEntity> {

    /**
     * Returns a list of all items of type ITEM.
     *
     * @param itemClass
     * @return An ordered list of Items.
     * @throws DBException If failed.
     */
    public List<ITEM> findAllItems(@NotNull Class<ITEM> itemClass) throws DBException;

    /**
     * Returns a list of all items of type ITEM ordered by Reference.
     *
     * @param itemClass
     * @return An ordered list of Items.
     * @throws DBException If failed.
     */
    public List<ITEM> findAllItemsOrderedByReferenceDesc(@NotNull Class<ITEM> itemClass) throws DBException;

    /**
     *
     *
     * @param itemClass
     * @param reference
     * @return An ordered list of Items.
     * @throws DBException If failed.
     */
    public List<ITEM> findItemsByReference(@NotNull Class<ITEM> itemClass, @NotNull String reference) throws DBException;

    /**
     *
     *
     * @param itemClass
     * @param reference
     * @throws DBException If failed.
     */
    public void deleteItemsByReference(@NotNull Class<ITEM> itemClass, @NotNull String reference) throws DBException;

    /**
     *
     *
     * @param itemClass
     * @throws DBException If failed.
     */
    public void deleteAllItems(@NotNull Class<ITEM> itemClass) throws DBException;

}
