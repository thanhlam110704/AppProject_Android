package com.example.appproject.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.db.MyDatabaseHelper;

public class UpdateGenActivity extends AppCompatActivity {

    EditText genre_input;
    Button update_button, delete_button;
    String id, genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gen);
        genre_input=findViewById(R.id.genre_input);
        update_button=findViewById(R.id.update_button3);
        delete_button=findViewById(R.id.delete_button3);

        getandsetIntentData();
        ActionBar ab= getSupportActionBar();
        if(ab!=null)
        {
            ab.setTitle(genre);
        }
        update_button.setOnClickListener(view -> {
            String newGenre = genre_input.getText().toString().trim();
            if (!newGenre.isEmpty()) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateGenActivity.this);
                myDB.updateData_genre(id, newGenre);
                finish();
            } else {
                Toast.makeText(UpdateGenActivity.this, "Vui lòng nhập thể loại mới", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getandsetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("genre")){
            // Lấy dữ liệu từ Intent
            id = getIntent().getStringExtra("id");
            genre=getIntent().getStringExtra("genre");
            ;
            // Setting dữ liệu từ Intent
            genre_input.setText(genre);
            Log.d("stev", genre);

        } else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn xóa" + genre+ " ?");
        builder.setMessage("Bạn có chắc là muốnn xóa" + genre+ " ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateGenActivity.this);
                mydb.deleteOneRow_genre(id);
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