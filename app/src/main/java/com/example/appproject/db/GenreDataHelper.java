package com.example.appproject.db;

import static com.example.appproject.db.MyDatabaseHelper.ID_COMIC_FOREGIN_CHAPTER;
import static com.example.appproject.db.MyDatabaseHelper.TABLE_NAME2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.appproject.model.Chapter;
import com.example.appproject.model.Genre;

import java.util.ArrayList;

public class GenreDataHelper extends SQLiteOpenHelper {
    public GenreDataHelper(@Nullable Context context){
        super(context,MyDatabaseHelper.DATABASE_NAME,null,MyDatabaseHelper.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private Genre cursorToGenre(Cursor cursor){
        return new Genre(
                cursor.getInt(0),//idGenre
                cursor.getString(1)//nameGenre
        );
    }
    public ArrayList<Genre> getAllGenre(){
        ArrayList<Genre> genres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM genre",null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Genre genre= cursorToGenre(cursor);

                        genres.add(genre);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return genres;
    }



}
