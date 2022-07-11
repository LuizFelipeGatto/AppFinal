package br.edu.ifsuldeminas.mch.appFinal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

abstract class DAO<T> {
    private DBHandler dbHandler = null;

    DAO(Context context){
        if (dbHandler == null){
            dbHandler = new DBHandler(context);
        }
    }

    SQLiteDatabase openToWrite(){
        SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
        return sqLiteDatabase;
    }

    SQLiteDatabase openToRead(){
        return dbHandler.getReadableDatabase();
    }

    public abstract void save(T entity);
    public abstract void update(T entity);
    public abstract void delete(T entity);
    public abstract List<T> listAll();
}
