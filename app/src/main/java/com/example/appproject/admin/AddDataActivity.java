package com.example.appproject.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appproject.R;
import com.example.appproject.db.MyDatabaseHelper;

public class AddDataActivity extends AppCompatActivity {
    EditText edtTruyen,edtDescription,edtStatus,edtNgayCapNhat,edtTacgia;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        addControls();
        addEvent();


    }

    private void addEvent() {
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(AddDataActivity.this);
                mydb.addComic(edtTruyen.getText().toString().trim(),edtDescription.getText().toString().trim(),
                        edtDescription.getText().toString().trim(),
                        edtNgayCapNhat.getText().toString().trim(),
                        edtStatus.getText().toString().trim());
            }
        });
    }

    private void addControls() {
        edtTruyen=findViewById(R.id.edtTenTruyen_add);
        edtStatus=findViewById(R.id.edtTrangThai_add);
        edtDescription=findViewById(R.id.edtDescription_add);
        edtNgayCapNhat=findViewById(R.id.edtNgayCapNhat_add);
        edtTacgia=findViewById(R.id.edtTacgia_add);
        add_button=findViewById(R.id.addData_button_add);

    }
}