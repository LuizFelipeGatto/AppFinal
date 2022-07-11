package br.edu.ifsuldeminas.mch.appFinal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER =
            " CREATE TABLE IF NOT EXISTS users ( " +
            " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " nome TEXT NOT NULL, " +
            " naturalidade TEXT NOT NULL);";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // não vamos tratar alterações
    }
}
