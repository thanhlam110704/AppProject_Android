package com.example.appproject.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.appproject.model.Comment;
import com.example.appproject.model.Genre;

import java.util.ArrayList;

public class CommentDataHelper extends SQLiteOpenHelper {
    public CommentDataHelper(@Nullable Context context){
        super(context,MyDatabaseHelper.DATABASE_NAME,null,MyDatabaseHelper.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private Comment cursorToProduct(Cursor cursor){
        byte[] imageByteArray= cursor.getBlob(3);
        Bitmap imageBitmap= BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length);
        return new Comment(
                cursor.getString(0),//idComment
                cursor.getString(1),//id_account_name
                cursor.getString(2)//id_comic_name
        );
    }
    public ArrayList<Comment> getAllComment(){
        ArrayList<Comment> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM comment",null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Comment comment= cursorToProduct(cursor);

                        comments.add(comment);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return comments;
    }

}
