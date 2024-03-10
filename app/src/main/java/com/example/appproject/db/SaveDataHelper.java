package com.example.appproject.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.appproject.model.Comment;
import com.example.appproject.model.Save;

import java.util.ArrayList;

public class SaveDataHelper extends SQLiteOpenHelper {
    public SaveDataHelper(@Nullable Context context){
        super(context,MyDatabaseHelper.DATABASE_NAME,null,MyDatabaseHelper.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private Save cursorToSave(Cursor cursor){
        byte[] imageByteArray= cursor.getBlob(3);
        Bitmap imageBitmap= BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length);
        return new Save(
                cursor.getInt(0),//idSave
                cursor.getInt(1),//id_account_save
                cursor.getInt(2)//id_comic_save
        );
    }
    public ArrayList<Save> getAllSave(){
        ArrayList<Save> saves = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM save",null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Save save= cursorToSave(cursor);

                        saves.add(save);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return saves;
    }

}
