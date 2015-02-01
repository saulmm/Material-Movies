package com.hackvg.android.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ismael on 31/01/2015.
 */
public class DbConstants {

    public static final String PROVIDER_NAME = "com.hackvg.android.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/"
            + Movies.TABLE_NAME);

    public final static String DB_NAME = "hackvg.db";
    public final static int DB_VERSION = 1;

    public class Movies {
        public static final String TABLE_NAME = "movies";
        public static final String ID = BaseColumns._ID;
        public static final String ID_MOVIE = "id_movie";
        public static final String STATUS = "status";
        public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ID_MOVIE + " INTEGER, " +
                STATUS + " INTEGER)";
        public static final String DEFAULT_SORT_ORDER = ID + " ASC";
    }


}
