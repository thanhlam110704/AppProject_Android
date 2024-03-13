package com.example.appproject.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    public final Context context;
    public static final String DATABASE_NAME = "Comic.db";
    public static final int DATABASE_VERSION = 16;


    // Bảng 1-
    public static final String TABLE_NAME = "comic";
    public static final String ID_COMIC = "id_comic";
    public static final String AVATAR="avatar";
    public static final String NAME_COMIC= "name_comic";
    public static final String DESCRIPTION = "description";
    public static final String AUTHOR="author";
    public static final String DATE_UPDATE="date_update";
    public static final String STATUS= "status";


    // Bảng 2
    public static final String TABLE_NAME2 = "chapter";
    public static final String ID_CHAPTER = "id_chapter";
    public static final String NAME_CHAPTER= "name_chapter";
    public static final String VIEWER = "viewer";
    public static final String DATE_PUBLISH = "date_publish";
    public static final String IMG1="img1";
    public static final String IMG2="img2";
    public static final String IMG3="img3";
    public static final String IMG4="img4";
    public static final String IMG5="img5";
    public static final String ID_COMIC_FOREGIN_CHAPTER="id_comic";



    //Bảng 3
    public static final String TABLE_NAME3 = "account";
    public static final String ID_ACCOUNT = "id_account";
    public static final String NAME_ACCOUNT= "name_account";
    public static final String EMAIL="email";
    public static final String Phone="phone";
    public static final String AVATAR_ACCOUNT="account";
    public static final String PASSWORD="pass";
    public static final String ROLE="role";



    //Bảng 4
    public static final String TABLE_NAME4 = "genre";
    public static final String ID_GENRE = "id_genre";
    public static final String NAME_GENRE= "name_genre";



    //Bảng 5
    public static final String TABLE_NAME5 = "comic_Genre";
    public static final String ID_Comic_Genre ="id_comicgenre";
    public static final String ID_GENRE_FOREGIN_COMIC_GENRE = "id_genre_comic_genre";
    public static final String ID_COMIC_FOREGIN_COMIC_GENRE = "id_comic_comic_genre";



    //Bảng 6
    public static final String TABLE_NAME6 = "comment";
    public static final String ID_COMMENT = "id_comment";
    public static final String ID_ACCOUNT_FOREGIN_COMENT = "id_account_comment";
    public static final String ID_COMIC_FOREGIN_COMMENT = "id_comic_comment";



    //Bảng 7
    public static final String TABLE_NAME7 = "save";
    public static final String ID_SAVE = "id_save";
    public static final String ID_ACCOUNT_FOREGIN_SAVE = "id_account_save";
    public static final String ID_COMIC_FOREGIN_SAVE = "id_comic_save";

    //Bảng 8
    public static final String TABLE_NAME8 ="register";
    public static final String ID_USERNAME ="id_username";
    private static final String ID_PASSWORD ="id_password";
    public static final String ID_EMAIL ="id_email";
    private static final String ID_ROLE ="id_role";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        SQLiteDatabase db = this.getWritableDatabase();
        String insertDefaultAccount = "INSERT INTO " + TABLE_NAME8 + " (" + ID_USERNAME + ", " + ID_PASSWORD + ", " + ID_EMAIL + ", " + ID_ROLE + ") VALUES (?, ?, ?, ?)";
        db.execSQL(insertDefaultAccount, new String[]{"Thanh", "admin123", "thanh@gmail.com", "1"});
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableRegister = "CREATE TABLE " + TABLE_NAME8 + " ("
                + ID_USERNAME + " TEXT, "
                + ID_PASSWORD + " TEXT, "
                + ID_EMAIL + " TEXT, "
                + ID_ROLE + " TEXT)";
        db.execSQL(createTableRegister);

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
                + ID_COMIC_FOREGIN_COMIC_GENRE + " INTEGER, "
                + ID_GENRE_FOREGIN_COMIC_GENRE + " INTEGER, "
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME8);
        onCreate(db);
    }
    //Data in SQLite
    public void addRegister(String uname, String pword, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        if (isUsernameExists(uname)) {
            Toast.makeText(context, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEmailExists(email)) {
            Toast.makeText(context, "Email đã được đăng ký", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(ID_USERNAME, uname);
        cv.put(ID_PASSWORD, pword);
        cv.put(ID_EMAIL, email);
        cv.put(ID_ROLE, "0");

        db.insert(TABLE_NAME8, null, cv);

        db.close();
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

    public Cursor readAllData(){
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
    public Cursor getChaptersByComicId(int comicId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + ID_COMIC_FOREGIN_CHAPTER + " = " + comicId;
        return db.rawQuery(query, null);
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
        long result = db.insert(TABLE_NAME5, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add comic genre", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Genre added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateDatacomic_genre(String row_id,String idcomic, String idgen){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_COMIC_FOREGIN_COMIC_GENRE,idcomic);
        cv.put(ID_GENRE_FOREGIN_COMIC_GENRE, idgen);

        long result = db.update(TABLE_NAME5, cv, "id_comicgenre=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    Cursor readAllDataComic_Genre(){
        String query = "SELECT * FROM " + TABLE_NAME5;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void deleteOneRowcomic_genre(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME5, "id_comicgenre=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    //Data in SQlite

    //Login + Register
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME8 + " WHERE " + ID_USERNAME + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME8 + " WHERE " + ID_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }
    public boolean checkLogin(String uname, String pword, String[] roleHolder) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_USERNAME,ID_ROLE};
        String selection = ID_USERNAME + " = ?" + " AND " + ID_PASSWORD + " = ?";
        String[] selectionArgs = {uname, pword};
        Cursor cursor = db.query(TABLE_NAME8, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            // User exists, retrieve the role
            int roleIndex = cursor.getColumnIndex(ID_ROLE);

            if (roleIndex != -1) {
                String role = cursor.getString(roleIndex);
                cursor.close();
                roleHolder[0] = role;
                return true;
            } else {
                // ID_ROLE column not found
                cursor.close();
                return false;
            }
        } else {
            // User not found or other issues
            cursor.close();
            return false; // Return a value that indicates failure
        }

    }
    //Login + Register
    public List<String> getGenresByComicId(int comicId) {
        List<String> genres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + NAME_GENRE +
                " FROM " + TABLE_NAME5 +
                " INNER JOIN " +TABLE_NAME4 +
                " ON " + ID_GENRE_FOREGIN_COMIC_GENRE + " = " + ID_GENRE +
                " WHERE " + ID_COMIC_FOREGIN_COMIC_GENRE + " = ?";

        String[] selectionArgs = {String.valueOf(comicId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String genreName = cursor.getString(cursor.getColumnIndex(NAME_GENRE));
                        genres.add(genreName);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }

        db.close();

        return genres;
    }
}
