package com.example.appproject.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.adapter.CustomGenAdapter;
import com.example.appproject.admin.AddGenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GenDataActivity extends AppCompatActivity {

    CustomGenAdapter customGenAdapter;
    ImageView empty_imageview3;
    TextView no_data3;

    RecyclerView recyclerView3;
    FloatingActionButton add_button3;
    MyDatabaseHelper mydb;
    ArrayList<String> genre_id ,genre_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_data);
        recyclerView3= findViewById(R.id.recyclerView3);
        add_button3 = findViewById(R.id.add_button3);
        empty_imageview3 = findViewById(R.id.empty_imageview3);
        no_data3 = findViewById(R.id.no_data3);
        add_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenDataActivity.this, AddGenActivity.class);
                startActivity(intent);
            }
        });
        mydb = new MyDatabaseHelper(GenDataActivity.this);
        genre_id = new ArrayList<>();
        genre_name = new ArrayList<>();
        storeDataInArrays();

        customGenAdapter = new CustomGenAdapter(GenDataActivity.this,this, genre_id ,genre_name);
        recyclerView3.setAdapter(customGenAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(GenDataActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    public void storeDataInArrays() {
        Cursor cursor = mydb.readAllDataGenre();
        if (cursor.getCount() == 0) {
            empty_imageview3.setVisibility(View.VISIBLE);
            no_data3.setVisibility(View.VISIBLE);
        } else {

            while (cursor.moveToNext()) {
                genre_id.add((cursor.getString(0)));
                genre_name.add(cursor.getString(1));

            }
            empty_imageview3.setVisibility(View.GONE);
            no_data3.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.delete_all){
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all data");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB= new MyDatabaseHelper(GenDataActivity.this);
                myDB.deleteAllData();
                Intent intent= new Intent(GenDataActivity.this,GenDataActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}