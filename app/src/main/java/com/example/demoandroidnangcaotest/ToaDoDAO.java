package com.example.demoandroidnangcaotest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.demoandroidnangcaotest.ToaDoRender.COL_TITLE;

public class ToaDoDAO {
    ToaDoRender toaDoRender;

    public ToaDoDAO(Context context) {
        toaDoRender = new ToaDoRender(context);
    }

    public long insertma(ToaDo_db toaDo_db) {
        SQLiteDatabase sqLiteDatabase = toaDoRender.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(toaDoRender.COL_KINHDO, toaDo_db.getKinhdo());
        contentValues.put(toaDoRender.COL_VIDO, toaDo_db.getVido());
        contentValues.put(COL_TITLE, toaDo_db.getTitle());


        long result = sqLiteDatabase.insert(toaDoRender.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();


        return result;
    }

    public long updatema(ToaDo_db toaDo_db) {
        SQLiteDatabase sqLiteDatabase = toaDoRender.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(toaDoRender.COL_KINHDO, toaDo_db.getKinhdo());
        contentValues.put(toaDoRender.COL_VIDO, toaDo_db.getVido());
        contentValues.put(COL_TITLE, toaDo_db.getTitle());


        long result = sqLiteDatabase.update(toaDoRender.TABLE_NAME, contentValues, COL_TITLE + "=?", new String[]{toaDo_db.getTitle()});
        sqLiteDatabase.close();


        return result;
    }

    public List<ToaDo_db> getAll() {
        List<ToaDo_db> toaDo_dbList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = toaDoRender.getReadableDatabase();


        String query = "Select * from " + toaDoRender.TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String kinhdo = cursor.getString(cursor.getColumnIndex("kinhdo"));
            String vido = cursor.getString(cursor.getColumnIndex("vido"));

            ToaDo_db toaDo_db = new ToaDo_db();

            toaDo_db.setKinhdo(Double.parseDouble(kinhdo));
            toaDo_db.setVido(Double.parseDouble(vido));
            toaDo_db.setTitle(title);

            toaDo_dbList.add(toaDo_db);
            cursor.moveToNext();
        }
        cursor.close();

        return toaDo_dbList;
    }

    public List<ToaDo_db> getAllfull() {
        List<ToaDo_db> sachList = new ArrayList<>();


        SQLiteDatabase sqLiteDatabase = toaDoRender.getReadableDatabase();

        String SQL = "SELECT * FROM " + toaDoRender.TABLE_NAME ;

        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    ToaDo_db sach = new ToaDo_db();

                    sach.setTitle(cursor.getString(cursor.getColumnIndex(toaDoRender.COL_TITLE)));
                    sach.setKinhdo(cursor.getDouble(cursor.getColumnIndex(toaDoRender.COL_KINHDO)));
                    sach.setVido(cursor.getDouble(cursor.getColumnIndex(toaDoRender.COL_VIDO)));

                    sachList.add(sach);
                    cursor.moveToNext();

                }
                cursor.close();
            }
        }

        return sachList;
    }


    public long deletema(ToaDo_db id){
        SQLiteDatabase sqLiteDatabase = toaDoRender.getWritableDatabase();
       long result= sqLiteDatabase.delete(toaDoRender.TABLE_NAME, toaDoRender.COL_TITLE + "=?",new String[]{id.getTitle()});
        return result ;
    }
}
