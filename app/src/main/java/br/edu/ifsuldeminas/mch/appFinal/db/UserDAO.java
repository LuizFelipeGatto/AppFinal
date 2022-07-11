package br.edu.ifsuldeminas.mch.appFinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.domain.Category;
import br.edu.ifsuldeminas.mch.appFinal.domain.User;

public class UserDAO extends DAO<User> {

    private Context context;

    public UserDAO(Context context){
        super(context);
        this.context = context;
    }

    @Override
    public void save(User entity) {
        SQLiteDatabase database = openToRead();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", entity.getNome());
        contentValues.put("naturalidade", entity.getNaturalidade());

        database.insert("users", null, contentValues);

        database.close();
    }

    @Override
    public void update(User entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", entity.getNome());
        contentValues.put("naturalidade", entity.getNaturalidade());

        String[] params = {entity.getId().toString()};
        database.update("users", contentValues,
                "id = ?", params);

        database.close();
    }

    @Override
    public void delete(User entity) {
        SQLiteDatabase database = openToWrite();

        String[] params = {entity.getId().toString()};
        database.delete("users", " id = ? ", params);

        database.close();
    }

    @Override
    public List<User> listAll() {
        SQLiteDatabase database = openToRead();
        List<User> users = new ArrayList<>();

        String sql = " SELECT * FROM users ";

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(
                    cursor.getColumnIndexOrThrow("nome"));

            String naturalidade = cursor.getString(
                    cursor.getColumnIndexOrThrow("naturalidade"));


            User user = new User(id, nome, naturalidade);

            users.add(user);
        }

        cursor.close();
        database.close();

        return users;
    }
}
