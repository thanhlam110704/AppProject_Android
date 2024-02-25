package com.example.appproject.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private  Context context;
    public static final String DATABASE_NAME="Comic.db";
    public static final int DATABASE_VERSION=1;


    // Bảng 1
    private static final String TABLE_NAME1 = "Comic";
    private static final int ID_COMIC =1;
    private static final String AVATAR=null;
    private static final String NAME_COMIC= null;
    private static final String DESCRIPTION = null;
    private static final String AUTHOR=null;
    private static final String DATE_UPDATE=null;
    private static final String STATUS= null;



    // Bảng 2
    private static final String TABLE_NAME2 = "Chapter";
    private static final String ID_CHAPTER = null;
    private static final String NAME_CHAPTER= null;
    private static final String DATE_PUBLISH = null;
    private static final String FILE=null;
    private static final String VIEWER = null;
    private static final String ID_COMIC_FOREGIN_CHAPTER=null;



    //Bảng 3
    private static final String TABLE_NAME3 = "Account";
    private static final String ID_ACCOUNT = null;
    private static final String NAME_ACCOUNT= null;
    private static final String EMAIL=null;
    private static final String PASSWORD=null;
    private static final String ROLE=null;



    //Bảng 4
    private static final String TABLE_NAME4 = "Gender";
    private static final String ID_GENDER = null;
    private static final String NAME_GENDER= null;



    //Bảng 5
    private static final String TABLE_NAME5 = "Comic_Gender";
    private static final String ID_Comic_Gender =null;
    private static final String ID_GENDER_FOREGIN = null;
    private static final String ID_COMIC_FOREGIN_COMIC_GENDER = null;



    //Bảng 6
    private static final String TABLE_NAME6 = "Comment";
    private static final String ID_COMMENT = null;
    private static final String ID_ACCOUNT_FOREGIN_COMENT = null;
    private static final String ID_COMIC_FOREGIN_COMMENT = null;



    //Bảng 7
    private static final String TABLE_NAME7 = "Save";
    private static final String ID_SAVE = null;
    private static final String ID_ACCOUNT_FOREGIN_SAVE = null;
    private static final String ID_COMIC_FOREGIN_SAVE = null;






    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Comic
        String createTableComic = "CREATE TABLE " + TABLE_NAME1 + " ("
                + ID_COMIC + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AVATAR + " BLOB, "
                + NAME_COMIC + " TEXT, "
                + DESCRIPTION + " TEXT, "
                + AUTHOR + " TEXT, "
                + DATE_UPDATE + " TEXT, "
                + STATUS + " INTEGER);";
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
                + TABLE_NAME1 + "(" + ID_COMIC + "));";
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
                + TABLE_NAME1 + "(" + ID_COMIC + "));";
        db.execSQL(createTableComicGender);

        // Tạo bảng Comment
        String createTableComment = "CREATE TABLE " + TABLE_NAME6 + " ("
                + ID_COMMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_ACCOUNT_FOREGIN_COMENT + " INTEGER, "
                + ID_COMIC_FOREGIN_COMMENT+ " INTEGER, "
                + "FOREIGN KEY(" + ID_ACCOUNT_FOREGIN_COMENT + ") REFERENCES "
                + TABLE_NAME3 + "(" + ID_ACCOUNT + "), "
                + "FOREIGN KEY(" +  ID_COMIC_FOREGIN_COMMENT+ ") REFERENCES "
                + TABLE_NAME1 + "(" + ID_COMIC + "));";
        db.execSQL(createTableComment);

        // Tạo bảng Save
        String createTableSave = "CREATE TABLE " + TABLE_NAME7 + " ("
                + ID_SAVE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_ACCOUNT_FOREGIN_SAVE + " INTEGER, "
                + ID_COMIC_FOREGIN_SAVE + " INTEGER, "
                + "FOREIGN KEY(" + ID_ACCOUNT_FOREGIN_SAVE + ") REFERENCES "
                + TABLE_NAME3 + "(" + ID_ACCOUNT + "), "
                + "FOREIGN KEY(" +  ID_COMIC_FOREGIN_SAVE+ ") REFERENCES "
                + TABLE_NAME1 + "(" + ID_COMIC + "));";
        db.execSQL(createTableSave);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu chúng tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME6);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME7);

        // Gọi lại phương thức onCreate() để tạo lại cấu trúc cơ sở dữ liệu mới
        onCreate(db);
    }

}
