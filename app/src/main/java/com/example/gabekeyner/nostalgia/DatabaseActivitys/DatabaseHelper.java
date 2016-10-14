package com.example.gabekeyner.nostalgia.DatabaseActivitys;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GabeKeyner on 10/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "NostalgiaDataBase";
    public static final String COMMENTS_TABLE_NAME = "comments";
    public static final String GROUPS_TABLE_NAME = "groups";

    public static final String [] COMMENTS_COLUMNS = new String[]{"image","title","comment","rating"};
    //public static final String [] GROUPS_COLUMNS








    //    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
