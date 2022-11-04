package com.example.hna.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID ="_id";
    public static final String FIO = "fio";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String OTEL = "otel";
    public static final String GROUP = "groups";
    public static final String A = "a";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);//удаления бд
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "("
                +KEY_ID + " integer primary key,"+
                FIO+" text,"
                +LOGIN+" text,"
                +PASSWORD+ " text,"
                +OTEL+" text,"+
                GROUP+" text,"+
                A+" integer"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+ TABLE_CONTACTS);
        onCreate(db);
    }
}
