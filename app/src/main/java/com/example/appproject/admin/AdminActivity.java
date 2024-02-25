package com.example.appproject.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerView_adddata;
    FloatingActionButton add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addEventAddData();
    }
    /*Xử lý thêm dữ liệu database*/
    private void addEventAddData() {
        recyclerView_adddata =findViewById(R.id.rcv_add);
        add_button=findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, AddDataActivity.class);
                startActivity(intent);
            }
        });
    }
    /*Xử lý thêm dữ liệu database*/
}