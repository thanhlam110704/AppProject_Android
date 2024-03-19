package com.example.appproject.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.db.MyDatabaseHelper;

public class AddGenActivity extends AppCompatActivity {

    EditText name_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gen);
        name_input=findViewById(R.id.namegen_input);
        add_button=findViewById(R.id.add_button1);
        add_button.setOnClickListener(view -> {
            String genName = name_input.getText().toString().trim();
            if (!genName.isEmpty()) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddGenActivity.this);
                myDB.addGen(genName);
                // Thêm mã xử lý khi thêm thành công, ví dụ: chuyển sang màn hình khác hoặc hiển thị thông báo
            } else {
                Toast.makeText(AddGenActivity.this, "Vui lòng nhập tên Gen", Toast.LENGTH_LONG).show();
            }
        });

    }
}