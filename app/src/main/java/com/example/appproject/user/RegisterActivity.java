package com.example.appproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appproject.R;

public class RegisterActivity extends AppCompatActivity {
    EditText username,password,email,repassword;
    Button btnRegister;

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
                String rpword = repassword.getText().toString();
                validateinfo(uname,mail,pword,rpword);


            }
        });
    }

    private void validateinfo(String uname, String mail, String pword, String rpword) {
        StringBuilder errorMessage = new StringBuilder();
        boolean hasError = false;

        if (uname.length() == 0) {
            errorMessage.append("Vui lòng nhập tên đăng nhập\n");
            username.setError("Vui lòng nhập tên đăng nhập");
            hasError = true;
        }

        if (pword.length() < 6) {
            errorMessage.append("Mật khẩu tối thiểu 6 ký tự\n");
            password.setError("Mật khẩu tối thiểu 6 ký tự");
            hasError = true;
        } else if (!pword.matches(".*[!@#$%^&*~`()_+=-].*")) {
            errorMessage.append("Mật khẩu cần chứa ít nhất 1 ký tự đặc biệt\n");
            password.setError("Mật khẩu cần chứa ít nhất 1 ký tự đặc biệt");
            hasError = true;
        }

        if (!rpword.equals(pword)) {
            errorMessage.append("Mật khẩu không khớp\n");
            repassword.setError("Mật khẩu không khớp");
            hasError = true;
        }

        if (mail.length() == 0) {
            errorMessage.append("Vui lòng nhập email\n");
            email.setError("Vui lòng nhập email");
            hasError = true;
        } else if (!mail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            errorMessage.append("Vui lòng nhập email hợp lệ\n");
            email.setError("Vui lòng nhập email hợp lệ");
            hasError = true;
        }

        // Display combined error message
        if (hasError) {
            // Show a dialog or toast with the combined error message
            showToast(errorMessage.toString());
        } else {
            // All fields are valid, continue with your registration logic
            // ...
        }
    }
    private void showToast(String message) {
        // Display a Toast with the combined error message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void addControls() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        btnRegister = findViewById(R.id.btnRegister);
        repassword = findViewById(R.id.repassword);
    }


}