package com.example.appproject.user;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.appproject.admin.AdminActivity;
import com.example.appproject.db.MyDatabaseHelper;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.example.appproject.R;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
public class LoginActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    Button btnLogin;
    CheckBox checkBox;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new MyDatabaseHelper(this);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        checkBox=findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show/hide the password based on the checkbox state
                if (isChecked) {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }

                // Move the cursor to the end of the text to ensure it's always visible
                edtPassword.setSelection(edtPassword.getText().length());

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = edtUsername.getText().toString().trim();
                String pword = edtPassword.getText().toString().trim();
                String[] roleHolder = new String[1];
                boolean loginSuccessful = databaseHelper.checkLogin(uname, pword, roleHolder);
                if (loginSuccessful) {
                    // Login successful, show a pop-up message
                    String role = roleHolder[0];
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Login Successful")
                            .setMessage("Welcome to the app!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if ("0".equals(role)) {
                                        // User role is 0 (normal user), navigate to HomeActivity
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else if ("1".equals(role)) {
                                        // User role is 1 (admin), navigate to AdminActivity
                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .show();
                } else {
                    // Login failed, show an error message
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}