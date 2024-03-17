package com.example.appproject.db;

import static com.example.appproject.db.MyDatabaseHelper.ID_ACCOUNT;
import static com.example.appproject.db.MyDatabaseHelper.TABLE_NAME3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appproject.model.Account;
import com.example.appproject.model.Comic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class AccountDataHelper extends SQLiteOpenHelper {
    public AccountDataHelper(@Nullable Context context){
        super(context,MyDatabaseHelper.DATABASE_NAME,null,MyDatabaseHelper.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @NotNull
    @Contract("_ -> new")
    private Account cursorToAccount(@NotNull Cursor cursor) {
        return new Account(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getBlob(5)
        );
    }
    public Account getAccountByName(String username) {
        Account account = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM account" + " WHERE username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }
    public ArrayList<Account> getAllAccount() {
        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account", null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                       Account account = cursorToAccount(cursor);

                        accounts .add(account);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return accounts ;
    }

    public Account getAccountById(int accountId) {
        Account account = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME3 + " WHERE " + ID_ACCOUNT + " = ?",
                new String[]{String.valueOf(accountId)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }
}
