package com.example.appproject.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appproject.R;
import com.example.appproject.adapter.CustomAccountAdapter;
import com.example.appproject.adapter.SaveAdapter;
import com.example.appproject.db.AccountDataActivity;
import com.example.appproject.db.AccountDataHelper;
import com.example.appproject.db.ComicDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.db.SaveDataHelper;
import com.example.appproject.db.SessionManager;
import com.example.appproject.model.Account;
import com.example.appproject.model.Comic;
import com.example.appproject.model.Save;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveActivity extends AppCompatActivity {
    SaveAdapter saveAdapter;

    MyDatabaseHelper myDatabaseHelper;
    SessionManager sessionManager;
    SaveDataHelper saveDataHelper;
    List<Comic> comicList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        recyclerView = findViewById(R.id.rcv_save);
        myDatabaseHelper = new MyDatabaseHelper(SaveActivity.this);
        sessionManager = new SessionManager(SaveActivity.this);
        saveDataHelper = new SaveDataHelper(SaveActivity.this);
        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        String id = userDetails.get(SessionManager.KEY_IDUSER);
        comicList= myDatabaseHelper.getSavedComicsByAccountId(Integer.parseInt(id));
        saveAdapter =new SaveAdapter(SaveActivity.this,comicList);
        recyclerView.setAdapter(saveAdapter);
        int numberOfColumns = 3; // Số cột bạn muốn hiển thị
        recyclerView.setLayoutManager(new GridLayoutManager(SaveActivity.this, numberOfColumns));



    }
}