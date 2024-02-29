package com.example.appproject.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appproject.R;

public class RegisterActivity extends AppCompatActivity {
    private
    EditText username,password,email;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname= username.getText().toString();
                String mail= email.getText().toString();
                String pword= password.getText().toString();
                validateinfo(uname,mail,pword);

            }
        });
    }
    private Boolean validateinfo(String uname, String mail, String pword) {
        if(uname.length()==0){

            username.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }
        else if(pword.length()<5){

            password.setError("Mật khẩu tối thiểu 6 ký tự");
            return false;
        }
        else if(mail.length()==0){

            email.setError("Vui lòng nhập email");
            return false;
        }
        else if (!mail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){

            email.setError("Vui lòng nhập email hợp lệ");
            return false;
        }

        else {
            return true;
        }
    }

    private void addControls() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        btnRegister = findViewById(R.id.btnRegister);
    }
}