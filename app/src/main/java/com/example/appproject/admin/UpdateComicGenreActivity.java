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

public class UpdateComicGenreActivity extends AppCompatActivity {

    EditText comicgenre_idcomic_input,comicgenre_idgen_input;
    Button update_button, delete_button;
    String id,id_comic,id_gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comic_genre);
        comicgenre_idcomic_input=findViewById(R.id.comicgenre_idcomic_input);
        comicgenre_idcomic_input=findViewById(R.id.comicgenre_idgen_input);
        update_button=findViewById(R.id.update_button4);
        delete_button=findViewById(R.id.delete_button4);

        getandsetIntentData();
        ActionBar ab= getSupportActionBar();
        if(ab!=null)
        {
            ab.setTitle(id_comic+""+id_gen);
        }
        update_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateComicGenreActivity.this);
            id_comic= comicgenre_idcomic_input.getText().toString().trim();
            id_gen=comicgenre_idgen_input.getText().toString().trim();
            myDB.updateDatacomic_genre(id, id_comic,id_gen);
            finish();
        });
        delete_button.setOnClickListener(view -> confirmDialog());
    }

    public void getandsetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("idcomic")&& getIntent().hasExtra("idgen")){
            // Lấy dữ liệu từ Intent
            id = getIntent().getStringExtra("id");
            id_comic=getIntent().getStringExtra("idcomic");
            id_gen=getIntent().getStringExtra("idgen");
            // Setting dữ liệu từ Intent
            comicgenre_idcomic_input.setText(id_comic);
            comicgenre_idgen_input.setText(id_gen);
            Log.d("stev", id+""+id_comic+""+id_gen);

        } else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn xóa dòng này ?");
        builder.setMessage("Bạn có chắc muốn xóa dòng này  ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateComicGenreActivity.this);
                mydb.deleteOneRowcomic_genre(id);
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