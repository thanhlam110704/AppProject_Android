package com.example.appproject.db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appproject.R;
import com.example.appproject.adapter.CustomAccountAdapter;
import com.example.appproject.model.Account;
import com.example.appproject.model.Comic;

import java.util.List;

public class AccountDataActivity extends AppCompatActivity {
    CustomAccountAdapter customAccountAdapter;
    AccountDataHelper accountDataHelper;
    List<Account> accountList;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);
        recyclerView = findViewById(R.id.recyclerView5);
        accountDataHelper= new AccountDataHelper(AccountDataActivity.this);
        accountList=accountDataHelper.getAllAccount();
        customAccountAdapter= new CustomAccountAdapter(accountList,this);
        recyclerView.setAdapter(customAccountAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager((linearLayoutManager));


    }
}