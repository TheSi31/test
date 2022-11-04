package com.example.hna.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Tematike extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "predmet";
    public static final String TABLE_CONTACTS = "tema";

    public static final String KEY_ID ="_id";
    public static final String PREDMET = "predmet";

    public Tematike(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "("
                + KEY_ID+" integer primary key,"
                + PREDMET +" text" +")");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+ TABLE_CONTACTS);
        onCreate(db);
    }
}
