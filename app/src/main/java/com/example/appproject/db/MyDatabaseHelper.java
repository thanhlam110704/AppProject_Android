package com.example.appproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Comic.db";
    private static final int DATABASE_VERSION = 2;



    // Bảng 1
    private static final String TABLE_NAME = "my_comic";
    private static final String ID_COMIC = "id_comic";
    private static final String AVATAR="avatar";
    private static final String NAME_COMIC= "name_comic";
    private static final String DESCRIPTION = "description";
    private static final String AUTHOR="author";
    private static final String DATE_UPDATE="date_update";
    private static final String STATUS= "status";


    // Bảng 2
    private static final String TABLE_NAME2 = "chapter";
    private static final String ID_CHAPTER = "id_chapter";
    private static final String NAME_CHAPTER= "name_chapter";
    private static final String VIEWER = "viewer";
    private static final String DATE_PUBLISH = "date_publish";
    private static final String IMG1="img1";
    private static final String IMG2="img2";
    private static final String IMG3="img3";
    private static final String IMG4="img4";
    private static final String IMG5="img5";
    private static final String ID_COMIC_FOREGIN_CHAPTER="id_comic";



    //Bảng 3
    private static final String TABLE_NAME3 = "account";
    private static final String ID_ACCOUNT = "id_account";
    private static final String NAME_ACCOUNT= "name_account";
    private static final String EMAIL="email";
    private static final String PASSWORD="pass";
    private static final String ROLE="role";



    //Bảng 4
    private static final String TABLE_NAME4 = "genre";
    private static final String ID_GENRE = "id_genre";
    private static final String NAME_GENRE= "name_genre";



    //Bảng 5
    private static final String TABLE_NAME5 = "comic_Genre";
    private static final String ID_Comic_Genre ="id_comicgenre";
    private static final String ID_GENRE_FOREGIN_COMIC_GENRE = "id_genre_comic_genre";
    private static final String ID_COMIC_FOREGIN_COMIC_GENRE = "id_comic_comic_genre";



    //Bảng 6
    private static final String TABLE_NAME6 = "comment";
    private static final String ID_COMMENT = "id_comment";
    private static final String ID_ACCOUNT_FOREGIN_COMENT = "id_account_comment";
    private static final String ID_COMIC_FOREGIN_COMMENT = "id_comic_comment";



    //Bảng 7
    private static final String TABLE_NAME7 = "save";
    private static final String ID_SAVE = "id_save";
    private static final String ID_ACCOUNT_FOREGIN_SAVE = "id_account_save";
    private static final String ID_COMIC_FOREGIN_SAVE = "id_comic_save";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableComic = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COMIC + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COMIC + " TEXT, "
                + DESCRIPTION + " TEXT, "
                + AUTHOR + " TEXT, "
                + STATUS + " TEXT,"
                + DATE_UPDATE + " TEXT, "
                + AVATAR + " BLOB)";
        ;
        db.execSQL(createTableComic);

        // Tạo bảng Chapter
        String createTableChapter = "CREATE TABLE " + TABLE_NAME2 + " ("
                + ID_CHAPTER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_CHAPTER + " TEXT, "
                + VIEWER + " INTEGER, "
                + DATE_PUBLISH + " TEXT, "
                + IMG1+ " BLOB,"
                + IMG2+ " BLOB,"
                + IMG3+ " BLOB,"
                + IMG4+ " BLOB,"
                + IMG5+ " BLOB,"
                + ID_COMIC_FOREGIN_CHAPTER + " INTEGER, "
                + "FOREIGN KEY(" + ID_COMIC_FOREGIN_CHAPTER + ") REFERENCES "
                + TABLE_NAME + "(" + ID_COMIC + "));";
        db.execSQL(createTableChapter);

        // Tạo bảng Account
        String createTableAccount = "CREATE TABLE " + TABLE_NAME3 + " ("
                + ID_ACCOUNT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_ACCOUNT + " TEXT, "
                + EMAIL + " TEXT, "
                + PASSWORD + " TEXT, "
                + ROLE + " INTEGER);";
        db.execSQL(createTableAccount);

        // Tạo bảng Gender
        String createTableGender = "CREATE TABLE " + TABLE_NAME4 + " ("
                + ID_GENRE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_GENRE + " TEXT);";
        db.execSQL(createTableGender);

        // Tạo bảng Comic_Gender
        String createTableComicGender = "CREATE TABLE " + TABLE_NAME5 + " ("
                + ID_Comic_Genre + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_GENRE_FOREGIN_COMIC_GENRE + " INTEGER, "
                + ID_COMIC_FOREGIN_COMIC_GENRE + " INTEGER, "
                + "FOREIGN KEY(" + ID_GENRE_FOREGIN_COMIC_GENRE + ") REFERENCES "
                + TABLE_NAME4 + "(" + ID_GENRE + "), "
                + "FOREIGN KEY(" + ID_COMIC_FOREGIN_COMIC_GENRE + ") REFERENCES "
                + TABLE_NAME + "(" + ID_COMIC + "));";
        db.execSQL(createTableComicGender);

        // Tạo bảng Comment
        String createTableComment = "CREATE TABLE " + TABLE_NAME6 + " ("
                + ID_COMMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_ACCOUNT_FOREGIN_COMENT + " INTEGER, "
                + ID_COMIC_FOREGIN_COMMENT+ " INTEGER, "
                + "FOREIGN KEY(" + ID_ACCOUNT_FOREGIN_COMENT + ") REFERENCES "
                + TABLE_NAME3 + "(" + ID_ACCOUNT + "), "
                + "FOREIGN KEY(" +  ID_COMIC_FOREGIN_COMMENT+ ") REFERENCES "
                + TABLE_NAME + "(" + ID_COMIC + "));";
        db.execSQL(createTableComment);

        // Tạo bảng Save
        String createTableSave = "CREATE TABLE " + TABLE_NAME7 + " ("
                + ID_SAVE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_ACCOUNT_FOREGIN_SAVE + " INTEGER, "
                + ID_COMIC_FOREGIN_SAVE + " INTEGER, "
                + "FOREIGN KEY(" + ID_ACCOUNT_FOREGIN_SAVE + ") REFERENCES "
                + TABLE_NAME3 + "(" + ID_ACCOUNT + "), "
                + "FOREIGN KEY(" +  ID_COMIC_FOREGIN_SAVE+ ") REFERENCES "
                + TABLE_NAME + "(" + ID_COMIC + "));";
        db.execSQL(createTableSave);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME6);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME7);
        onCreate(db);
    }

    public void addComic(String name, String description, String author, String status, String dateupdate, byte[] avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_COMIC, name);
        cv.put(DESCRIPTION, description);
        cv.put(AUTHOR, author);
        cv.put(STATUS, status);
        cv.put(DATE_UPDATE, dateupdate);
        cv.put(AVATAR,avatar);


        long result = db.insert(TABLE_NAME, null, cv);


        if (result == -1) {
            Toast.makeText(context, "Failed to add comic", Toast.LENGTH_SHORT).show();
            Log.d("DBHelper", "Avatar bytes length: " + (avatar != null ? avatar.length : 0));

        } else {
            Toast.makeText(context, "Comic added successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData_comic(String row_id,String name, String description, String author, String status ,String date_update,byte[] avatar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME_COMIC,name);
        cv.put(DESCRIPTION,description);
        cv.put(DATE_UPDATE,date_update);
        cv.put(STATUS,status);
        cv.put(AUTHOR,author);
        cv.put(AVATAR,avatar);

        long result = db.update(TABLE_NAME, cv, "id_comic=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneRow_comic(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id_comic=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
    public void addChapter(String chapter, String viewer, String date_publish, List<byte[]> imageList , String idcomic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_CHAPTER, chapter);
        cv.put(DATE_PUBLISH, date_publish);
        cv.put(VIEWER, viewer);
        // Ensure the imageList has at least 5 elements
        for (int i = 0; i < 5; i++) {
            String imgColumnName = "IMG" + (i + 1);
            if (i < imageList.size()) {
                cv.put(imgColumnName, imageList.get(i));
            } else {
                cv.putNull(imgColumnName); // Set to NULL if no image is provided
            }
        }
        cv.put(ID_COMIC_FOREGIN_CHAPTER, Integer.parseInt(idcomic));

        long result = db.insert(TABLE_NAME2, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add chapter", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Chapter added successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateData_chapter(String row_id,String chapter, String viewer, String date_publish,List<byte[]> imageList,String idcomic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME_CHAPTER,chapter);
        cv.put(VIEWER, viewer);
        cv.put(DATE_PUBLISH, date_publish);
        // Ensure the imageList has at least 5 elements
        for (int i = 0; i < 5; i++) {
            String imgColumnName = "IMG" + (i + 1);
            if (i < imageList.size()) {
                cv.put(imgColumnName, imageList.get(i));
            } else {
                cv.putNull(imgColumnName); // Set to NULL if no image is provided
            }
        }
        cv.put(ID_COMIC_FOREGIN_CHAPTER,Integer.parseInt(idcomic));

        long result = db.update(TABLE_NAME, cv, "id_chapter=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    Cursor readAllDataChapter(){
        String query = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void deleteOneRow_chapter(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "id_chapter=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    public void addGen(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_GENRE, name);
        long result = db.insert(TABLE_NAME4, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add genre", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Genre added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateData_genre(String row_id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_GENRE, name);

        long result = db.update(TABLE_NAME4, cv, "id_genre=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    Cursor readAllDataGenre(){
        String query = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void deleteOneRow_genre(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME4, "id_genre=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    public void addComic_Gen(String idcomic, String idgen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_COMIC_FOREGIN_COMIC_GENRE,idcomic);
        cv.put(ID_GENRE_FOREGIN_COMIC_GENRE, idgen);
        long result = db.insert(TABLE_NAME4, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add genre", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Genre added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateDatacomic_genre(String row_id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_GENRE, name);

        long result = db.update(TABLE_NAME4, cv, "id_genre=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    Cursor readAllDataComic_Genre(){
        String query = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void deleteOneRowcomic_genre(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME4, "id_genre=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}
