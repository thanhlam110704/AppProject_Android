package com.example.appproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Comic.db";
    private static final int DATABASE_VERSION = 2; // Increase the version number when making changes to the database schema

    private static final String TABLE_NAME = "my_comic";

    // Bảng 1
    private static final String ID_COMIC = "id_comic";
    //private static final String AVATAR="avatar";
    private static final String NAME_COMIC= "name_comic";
    private static final String DESCRIPTION = "description";
    private static final String AUTHOR="author";
    private static final String DATE_UPDATE="date_update";
    private static final String STATUS= "status";


    // Bảng 2
    private static final String TABLE_NAME2 = "chapter";
    private static final String ID_CHAPTER = "id_chapter";
    private static final String NAME_CHAPTER= "name_chapter";
    private static final String DATE_PUBLISH = "date_publish";
    private static final String FILE="file";
    private static final String VIEWER = "viewer";
    private static final String ID_COMIC_FOREGIN_CHAPTER="id_comic";



    //Bảng 3
    private static final String TABLE_NAME3 = "account";
    private static final String ID_ACCOUNT = "id_account";
    private static final String NAME_ACCOUNT= "name_account";
    private static final String EMAIL="email";
    private static final String PASSWORD="pass";
    private static final String ROLE="role";



    //Bảng 4
    private static final String TABLE_NAME4 = "gender";
    private static final String ID_GENDER = "id_gender";
    private static final String NAME_GENDER= "name_gender";



    //Bảng 5
    private static final String TABLE_NAME5 = "comic_Gender";
    private static final String ID_Comic_Gender ="id_comicgender";
    private static final String ID_GENDER_FOREGIN = "id_gender_comic_gender";
    private static final String ID_COMIC_FOREGIN_COMIC_GENDER = "id_comic_comic_gender";



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
                + DATE_UPDATE + " TEXT, "
                + STATUS + " TEXT);";
        db.execSQL(createTableComic);

        // Tạo bảng Chapter
        String createTableChapter = "CREATE TABLE " + TABLE_NAME2 + " ("
                + ID_CHAPTER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_CHAPTER + " INTEGER, "
                + DATE_PUBLISH + " TEXT, "
                + FILE + " BLOB, "
                + VIEWER + " INTEGER, "
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
                + ID_GENDER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_GENDER + " TEXT);";
        db.execSQL(createTableGender);

        // Tạo bảng Comic_Gender
        String createTableComicGender = "CREATE TABLE " + TABLE_NAME5 + " ("
                + ID_Comic_Gender + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_GENDER_FOREGIN + " INTEGER, "
                + ID_COMIC_FOREGIN_COMIC_GENDER + " INTEGER, "
                + "FOREIGN KEY(" + ID_GENDER_FOREGIN + ") REFERENCES "
                + TABLE_NAME4 + "(" + ID_GENDER + "), "
                + "FOREIGN KEY(" + ID_COMIC_FOREGIN_COMIC_GENDER + ") REFERENCES "
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

    public void addComic(String name, String description, String author, String status, String dateupdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_COMIC, name);
        cv.put(DESCRIPTION, description);
        cv.put(AUTHOR, author);
        cv.put(STATUS, status);
        cv.put(DATE_UPDATE, dateupdate);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add comic", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Comic added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addChapter(String name, String date_publish, String file, String viewer, String id_comic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_CHAPTER, name);
        cv.put(DATE_PUBLISH, date_publish);
        cv.put(FILE, file);
        cv.put(VIEWER, viewer);
        cv.put(ID_COMIC_FOREGIN_CHAPTER, id_comic);

        long result = db.insert(TABLE_NAME2, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add chapter", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Chapter added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addGen(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_GENDER, name);
        long result = db.insert(TABLE_NAME3, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add chapter", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Chapter added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
