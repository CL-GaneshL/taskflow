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
public abstract class HexaDBEntity  {

    /**
     * Create an Item query provider.
     *
     * @param <ITEM> The type of the Query Provider.
     * @return An Item query provider.
     */
//    public static <ITEM extends HexaDBEntity> HexaDBItemQueryProvider<ITEM> createDBItemQueryProvider() {
//
//        return new HexaDBItemQueryProvider<ITEM>() {
//
////            private final HexaDBLogger LOG = HexaDBLogManager.getLogger(HexaDBItemQueryProvider.class);
//
//            @Override
//            public List<ITEM> findAllItems(
//                    @NotNull Class<ITEM> itemClass) throws DBException {
//
//                final List<ITEM> dbItems;
//
//                String queryName = null;
//                final NamedQueries namedQueries[] = itemClass.getAnnotationsByType(NamedQueries.class);
//
//                for (NamedQueries nQs : namedQueries) {
//                    for (NamedQuery namedQuerie : nQs.value()) {
//                        if (namedQuerie.name().endsWith(FIND_ALL_SUFFIX)) {
//                            queryName = namedQuerie.name();
//                            break;
//                        }
//                    }
//                }
//
//                // the named query must exist
//                assert queryName != null : ("queryName = " + queryName);
//
//                final DBDatabase db;
//                DBConnection con = null;
//
//                try {
//                    db = DBDatabaseManager.getHexaDatabaseInstance();
//                    con = db.getConnection();
//                    dbItems = con.open().query(queryName, itemClass);
//
//                } finally {
//
//                    if (con != null) {
//                        con.close();
//                    }
//                }
//
//                return dbItems;
//            }
//
//            @Override
//            public List<ITEM> findAllItemsOrderedByReferenceDesc(
//                    @NotNull Class<ITEM> itemClass) throws DBException {
//
//                final List<ITEM> dbItems;
//
//                String queryName = null;
//                final NamedQueries namedQueries[] = itemClass.getAnnotationsByType(NamedQueries.class);
//
//                for (NamedQueries nQs : namedQueries) {
//                    for (NamedQuery namedQuerie : nQs.value()) {
//                        if (namedQuerie.name().endsWith(FIND_ALL_ORDERED_BY_REFERENCE_SUFFIX)) {
//                            queryName = namedQuerie.name();
//                            break;
//                        }
//                    }
//                }
//
//                // the named query must exist
//                assert queryName != null : ("queryName = " + queryName);
//
//                final DBDatabase db;
//                DBConnection con = null;
//
//                try {
//                    db = DBDatabaseManager.getHexaDatabaseInstance();
//                    con = db.getConnection();
//                    dbItems = con.open().query(queryName, itemClass);
//
//                } finally {
//
//                    if (con != null) {
//                        con.close();
//                    }
//                }
//
//                return dbItems;
//            }
//
//            @Override
//            public List<ITEM> findItemsByReference(
//                    @NotNull Class<ITEM> itemClass, @NotNull String reference)
//                    throws DBException {
//
//                final List<ITEM> dbItems;
//
//                String queryName = null;
//                final NamedQueries namedQueries[] = itemClass.getAnnotationsByType(NamedQueries.class);
//
//                for (NamedQueries nQs : namedQueries) {
//                    for (NamedQuery namedQuerie : nQs.value()) {
//                        if (namedQuerie.name().endsWith(FIND_BY_REFERENCE_SUFFIX)) {
//                            queryName = namedQuerie.name();
//                            break;
//                        }
//                    }
//                }
//
//                // the named query must exist
//                assert queryName != null : ("queryName = " + queryName);
//
//                final DBDatabase db;
//                DBConnection con = null;
//
//                try {
//                    db = DBDatabaseManager.getHexaDatabaseInstance();
//                    con = db.getConnection();
//                    dbItems = con.open().query(queryName, itemClass, "reference", reference);
//
//                } finally {
//
//                    if (con != null) {
//                        con.close();
//                    }
//                }
//
//                return dbItems;
//            }
//
//            @Override
//            public void deleteItemsByReference(
//                    @NotNull Class<ITEM> itemClass, @NotNull String reference)
//                    throws DBException {
//
//                String queryName = null;
//                final NamedQueries namedQueries[] = itemClass.getAnnotationsByType(NamedQueries.class);
//
//                for (NamedQueries nQs : namedQueries) {
//                    for (NamedQuery namedQuerie : nQs.value()) {
//                        if (namedQuerie.name().endsWith(DELETE_BY_REFERENCE_SUFFIX)) {
//                            queryName = namedQuerie.name();
//                            break;
//                        }
//                    }
//                }
//
//                // the named query must exist
//                assert queryName != null : ("queryName = " + queryName);
//                DBDatabase database = DBDatabaseManager.getHexaDatabaseInstance();
//
//                // TODO : wrong !!!!!!!!!!!
//                database.getConnection().query(queryName, itemClass, "reference", reference);
//            }
//
//            @Override
//            public void deleteAllItems(@NotNull Class<ITEM> itemClass) throws DBException {
//
//                String queryName = null;
//                final NamedQueries namedQueries[] = itemClass.getAnnotationsByType(NamedQueries.class);
//
//                for (NamedQueries nQs : namedQueries) {
//                    for (NamedQuery namedQuerie : nQs.value()) {
//                        if (namedQuerie.name().endsWith(DELETE_ALL_SUFFIX)) {
//                            queryName = namedQuerie.name();
//                            break;
//                        }
//                    }
//                }
//
//                // the named query must exist
//                assert queryName != null : ("queryName = " + queryName);
//                DBDatabase database = DBDatabaseManager.getHexaDatabaseInstance();
//
//                // TODO : wrong !!!!!!!!!!!
//                database.getConnection().query(queryName, itemClass);
//            }
//        };
//    }

}
