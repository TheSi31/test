package com.example.hna.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Tema extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tema";
    public static String TABLE_CONTACTS = "tems";

    public static final String KEY_ID ="_id";
    public static final String PREDMET = "predmet";
    public static final String TEMA = "tema";
    public static final String TEXTS = "texts";
    public static final String FILE = "file";

    public Tema(Context context,String TABLE) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        TABLE_CONTACTS=TABLE;
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "("
                + KEY_ID+" integer primary key,"
                + PREDMET +" text,"
                + TEMA + " text,"
                + TEXTS + " text,"
                + FILE +" text)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+ TABLE_CONTACTS);
        onCreate(db);
    }
}
