package com.example.appproject.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.appproject.R;
import com.example.appproject.db.MyDatabaseHelper;

public class AddComic_GenActivity extends AppCompatActivity {

    EditText idgen_input,idcomic_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comic_gen);
        idgen_input=findViewById(R.id.idgen_input);
        idcomic_input=findViewById(R.id.idcomic_input);
        add_button=findViewById(R.id.add_button1);
        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper (AddComic_GenActivity.this);
            myDB.addComic_Gen(idcomic_input.getText().toString().trim(),
                    idgen_input.getText().toString().trim()
            );
        });
    }
}