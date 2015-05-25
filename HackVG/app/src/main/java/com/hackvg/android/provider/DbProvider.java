/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackvg.android.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


public class DbProvider extends ContentProvider {

    private static final int MOVIES = 0;
    private static final int MOVIES_ID = 1;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DbConstants.PROVIDER_NAME, DbConstants.Movies.TABLE_NAME, MOVIES);
        uriMatcher.addURI(DbConstants.PROVIDER_NAME, DbConstants.Movies.TABLE_NAME + "/#", MOVIES_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DbHelper dbHelper = new DbHelper(getContext());
        database = dbHelper.getWritableDatabase();
        return (database != null);
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.hackvg." + DbConstants.Movies.TABLE_NAME;
            case MOVIES_ID:
                return "vnd.android.cursor.item/vnd.hackvg." + DbConstants.Movies.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported	URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                rowId = database.insert(DbConstants.Movies.TABLE_NAME, null, values);

                if (rowId > 0) {
                    uri = ContentUris.withAppendedId(DbConstants.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;

            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case MOVIES_ID:
                queryBuilder.appendWhere(DbConstants.Movies.ID + "="
                        + uri.getLastPathSegment());
            case MOVIES:
                queryBuilder.setTables(DbConstants.Movies.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DbConstants.Movies.DEFAULT_SORT_ORDER;
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor c = queryBuilder.query(database, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                rowsUpdated = database.update(DbConstants.Movies.TABLE_NAME, values,
                        selection, selectionArgs);
                break;

            case MOVIES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = database.update(DbConstants.Movies.TABLE_NAME, values,
                            DbConstants.Movies.ID + "=" + id, null);
                } else {
                    rowsUpdated = database.update(DbConstants.Movies.TABLE_NAME, values,
                            DbConstants.Movies.ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                rowsDeleted = database.delete(DbConstants.Movies.TABLE_NAME, selection,
                        selectionArgs);
                break;

            case MOVIES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = database.delete(DbConstants.Movies.TABLE_NAME,
                            DbConstants.Movies.ID + "=" + id, null);
                } else {
                    rowsDeleted = database.delete(DbConstants.Movies.TABLE_NAME,
                            DbConstants.Movies.ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }
}
