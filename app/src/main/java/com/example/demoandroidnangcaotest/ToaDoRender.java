package com.example.demoandroidnangcaotest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToaDoRender extends SQLiteOpenHelper {
    public ToaDoRender(Context context){
        super(context,"toado.db",null,1);

    }

    public static String TABLE_NAME ="ToaDo";
    public static String COL_KINHDO = "kinhdo";
    public static String COL_VIDO = "vido";
    public static String COL_TITLE = "title";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "create table " + TABLE_NAME + "("+COL_TITLE+" text primary key, "+COL_KINHDO+" double, "+COL_VIDO+" double)";
        sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
