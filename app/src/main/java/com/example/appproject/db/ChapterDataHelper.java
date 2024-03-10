package com.example.appproject.db;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;

import java.util.ArrayList;

public class ChapterDataHelper extends SQLiteOpenHelper {
    public  ChapterDataHelper(@Nullable Context context){
        super(context,MyDatabaseHelper.DATABASE_NAME,null,MyDatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
private Chapter cursorToProduct(Cursor cursor){
    byte[] imageByteArray= cursor.getBlob(10);
    Bitmap imageBitmap=BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length);
    return new Chapter(
            cursor.getInt(0),//id
            cursor.getString(1), //nameChap
            cursor.getString(2),//viewer
            cursor.getString(3),//dataPublish
            cursor.getString(4),//img1
            cursor.getString(5),//img2
            cursor.getString(6),//img3
            cursor.getString(7),//img4
            cursor.getString(8),//img5
            imageByteArray//image

    );
}
public ArrayList<Chapter>getAllChapter(){
    ArrayList<Chapter> chapters = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor= db.rawQuery("SELECT * FROM chapter",null);
    if (cursor != null) {
        try {
            if (cursor.moveToFirst()) {
                do {
                   Chapter chapter= cursorToProduct(cursor);

                    chapters.add(chapter);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
    }
    return chapters;
}


}
