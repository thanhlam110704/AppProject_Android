package com.example.appproject.db;
import static com.example.appproject.db.MyDatabaseHelper.DATE_PUBLISH;
import static com.example.appproject.db.MyDatabaseHelper.ID_COMIC_FOREGIN_CHAPTER;
import static com.example.appproject.db.MyDatabaseHelper.TABLE_NAME2;
import static com.example.appproject.db.MyDatabaseHelper.VIEWER;

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
private Chapter cursorToChapter(Cursor cursor){
    byte[] imageByteArray1= cursor.getBlob(4);
    Bitmap imageBitmap=BitmapFactory.decodeByteArray(imageByteArray1,0,imageByteArray1.length);
    byte[] imageByteArray2= cursor.getBlob(5);
    Bitmap imageBitmap2=BitmapFactory.decodeByteArray(imageByteArray2,0,imageByteArray2.length);
    byte[] imageByteArray3= cursor.getBlob(6);
    Bitmap imageBitmap3=BitmapFactory.decodeByteArray(imageByteArray3,0,imageByteArray3.length);
    byte[] imageByteArray4= cursor.getBlob(7);
    Bitmap imageBitmap4=BitmapFactory.decodeByteArray(imageByteArray4,0,imageByteArray4.length);
    byte[] imageByteArray5= cursor.getBlob(8);
    Bitmap imageBitmap5=BitmapFactory.decodeByteArray(imageByteArray5,0,imageByteArray5.length);
    return new Chapter(
            cursor.getInt(0),//id
            cursor.getString(1), //nameChap
            cursor.getString(2),//viewer
            cursor.getString(3),//dataPublish
            imageByteArray1,//img1
            imageByteArray2,//img2
            imageByteArray3,//img3
            imageByteArray4,//img4
            imageByteArray5,//img5
            cursor.getInt(9)
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
                   Chapter chapter= cursorToChapter(cursor);

                    chapters.add(chapter);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
    }
    return chapters;
}
    public  ArrayList<Chapter> getChaptersByComicId( int comicId) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + ID_COMIC_FOREGIN_CHAPTER + " = ?";
        String[] selectionArgs = {String.valueOf(comicId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Chapter chapter= cursorToChapter(cursor);

                        chapters.add(chapter);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return chapters;
    }
    public Chapter getLatestChapterByComicId(int comicId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Chapter latestChapter = new Chapter();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + ID_COMIC_FOREGIN_CHAPTER + " = ?" +
                " ORDER BY " + DATE_PUBLISH + " DESC LIMIT 1";
        String[] selectionArgs = {String.valueOf(comicId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            latestChapter = cursorToChapter(cursor);
        }

        cursor.close();
        return latestChapter;
    }
    public int getTotalViewsByComicId(int comicId) {
        int totalViews = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + VIEWER + ") FROM " + TABLE_NAME2 + " WHERE " + ID_COMIC_FOREGIN_CHAPTER + " = ?";
        String[] selectionArgs = {String.valueOf(comicId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            totalViews = cursor.getInt(0);
        }

        cursor.close();
        return totalViews;
    }
    public ArrayList<Chapter> getLatestChaptersByComicId(int comicId, int numberOfChapters) {
        ArrayList<Chapter> latestChapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + ID_COMIC_FOREGIN_CHAPTER + " = ?" +
                " ORDER BY " + DATE_PUBLISH + " DESC LIMIT ?";
        String[] selectionArgs = {String.valueOf(comicId), String.valueOf(numberOfChapters)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Chapter chapter = cursorToChapter(cursor);
                        latestChapters.add(chapter);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return latestChapters;
    }







}
