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
import com.example.appproject.adapter.CustomComicGenAdapter;
import com.example.appproject.admin.AddComic_GenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Comic_GenreDataActivity extends AppCompatActivity {

    CustomComicGenAdapter customComicGenAdapter;
    ImageView empty_imageview4;
    TextView no_data4;

    RecyclerView recyclerView4;
    FloatingActionButton add_button4;
    MyDatabaseHelper mydb;
    ArrayList<String> comicgenre_id ,comicgenre_idgen,comicgenre_idcomic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_genre_data);
        recyclerView4= findViewById(R.id.recyclerView4);
        add_button4 = findViewById(R.id.add_button4);
        empty_imageview4 = findViewById(R.id.empty_imageview4);
        no_data4 = findViewById(R.id.no_data4);
        add_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Comic_GenreDataActivity.this, AddComic_GenActivity.class);
                startActivity(intent);
            }
        });
        mydb = new MyDatabaseHelper(Comic_GenreDataActivity.this);
        comicgenre_id= new ArrayList<>();
        comicgenre_idcomic = new ArrayList<>();
        comicgenre_idgen = new ArrayList<>();
        storeDataInArrays();

        customComicGenAdapter = new CustomComicGenAdapter(Comic_GenreDataActivity.this,this, comicgenre_id ,comicgenre_idcomic,comicgenre_idgen);
        recyclerView4.setAdapter(customComicGenAdapter);
        recyclerView4.setLayoutManager(new LinearLayoutManager(Comic_GenreDataActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    public void storeDataInArrays() {
        Cursor cursor = mydb.readAllDataComic_Genre();
        if (cursor.getCount() == 0) {
            empty_imageview4.setVisibility(View.VISIBLE);
            no_data4.setVisibility(View.VISIBLE);
        } else {

            while (cursor.moveToNext()) {
                comicgenre_id.add((cursor.getString(0)));
                comicgenre_idcomic.add(cursor.getString(1));
                comicgenre_idgen.add(cursor.getString(2));

            }
            empty_imageview4.setVisibility(View.GONE);
            no_data4.setVisibility(View.GONE);
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
            Toast.makeText(this, "Xóa dữ liệu", Toast.LENGTH_SHORT).show();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn xóa tất cả ?");
        builder.setMessage("Bạn có chắc là muốn xóa hết tất cả dữ liệu ");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB= new MyDatabaseHelper(Comic_GenreDataActivity.this);
                myDB.deleteAllData();
                Intent intent= new Intent(Comic_GenreDataActivity.this,Comic_GenreDataActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}