package com.example.appproject.db;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.appproject.model.Comic;

import java.util.ArrayList;

public class ComicDataHelper  extends SQLiteOpenHelper {
    public ComicDataHelper(@Nullable Context context) {
        super(context, MyDatabaseHelper.DATABASE_NAME, null, MyDatabaseHelper.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private Comic cursorToComic(Cursor cursor) {
        byte[] imageByteArray = cursor.getBlob(6); // Lấy dữ liệu từ trường "image"
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length); // Chuyển đổi mảng byte thành đối tượng Bitmap

        return new Comic(
                cursor.getInt(0),//id
                cursor.getString(1),//name
                cursor.getString(2),//description
                cursor.getString(3),//author
                cursor.getString(4),//status
                cursor.getString(5),//date_update
                imageByteArray//avatar
        );

    }
    public ArrayList<Comic> getAllComics() {
        ArrayList<Comic> comics = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM comic", null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Comic comic = cursorToComic(cursor);

                        comics.add(comic);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return comics;
    }

}
