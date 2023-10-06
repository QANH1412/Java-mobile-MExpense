package com.example.myapplication;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TripDBHelper extends SQLiteOpenHelper {

    public TripDBHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {
        sql.execSQL(TripDBInput.CREATE_TABLE_SQLITE);
        sql.execSQL(ExpensesDBInput.CREATE_TABLE_SQLITE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int i, int i1) {
        sql.execSQL(TripDBInput.DELETE_TABLE_SQLITE);
        sql.execSQL(ExpensesDBInput.DELETE_TABLE_SQLITE);
        onCreate(sql);
    }
}
