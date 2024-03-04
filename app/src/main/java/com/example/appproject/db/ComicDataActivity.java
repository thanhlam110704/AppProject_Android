package com.example.appproject.db;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.adapter.CustomComicAdapter;
import com.example.appproject.admin.AddComicActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ComicDataActivity extends AppCompatActivity {

    public CustomComicAdapter customAdapter;
    ImageView empty_imageview;
    TextView no_data;

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper mydb;
    ArrayList<String> comic_id, comic_name, comic_author, comic_description, comic_status, comic_dateupdate;
    ArrayList<byte[]> comic_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_data);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComicDataActivity.this, AddComicActivity.class);
                startActivity(intent);
            }
        });
        mydb = new MyDatabaseHelper(ComicDataActivity.this);
        comic_id = new ArrayList<>();
        comic_name = new ArrayList<>();
        comic_author = new ArrayList<>();
        comic_description = new ArrayList<>();
        comic_status = new ArrayList<>();
        comic_dateupdate = new ArrayList<>();
        comic_avatar= new ArrayList<byte[]>();
        storeDataInArrays();

        customAdapter = new CustomComicAdapter(ComicDataActivity.this,this, comic_id, comic_name, comic_author, comic_description, comic_status, comic_dateupdate,comic_avatar);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ComicDataActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    public void storeDataInArrays() {
        Cursor cursor = mydb.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {

            while (cursor.moveToNext()) {
                comic_id.add((cursor.getString(0)));
                comic_name.add(cursor.getString(1));
                comic_author.add(cursor.getString(2));
                comic_description.add(cursor.getString(3));
                comic_status.add(cursor.getString(4));
                comic_dateupdate.add(cursor.getString(5));
                comic_avatar.add(cursor.getBlob(6));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
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
                MyDatabaseHelper myDB= new MyDatabaseHelper(ComicDataActivity.this);
                myDB.deleteAllData();
                Intent intent= new Intent(ComicDataActivity.this,ComicDataActivity.class);
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