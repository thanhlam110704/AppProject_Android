package com.example.appproject.user;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.appproject.admin.AdminActivity;
import com.example.appproject.db.AccountDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;
import com.example.appproject.R;
import com.example.appproject.db.SessionManager;
import com.example.appproject.model.Account;

import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
public class LoginActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    TextView txtForgetPassword,txtSignUp;
    Button btnLogin;
    CheckBox checkBox;
    MyDatabaseHelper databaseHelper;
    SessionManager sessionManager;
    AccountDataHelper accountDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new MyDatabaseHelper(this);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        checkBox=findViewById(R.id.checkBox);
        txtForgetPassword=findViewById(R.id.txtForgetPassword);
        txtSignUp=findViewById(R.id.txtSignUp);
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

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = edtUsername.getText().toString().trim();
                String pword = edtPassword.getText().toString().trim();
                accountDataHelper= new AccountDataHelper(LoginActivity.this);
                sessionManager= new SessionManager(LoginActivity.this);
                String[] roleHolder = new String[1];
                boolean loginSuccessful = databaseHelper.checkLogin(uname, pword, roleHolder);
                if (loginSuccessful) {
                    // Login successful, show a pop-up message
                    Account account=accountDataHelper.getAccountByName(uname);
                    String role = roleHolder[0];
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Đăng nhập thành công")
                            .setMessage("Chào mừng đến với app của chúng tôi!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sessionManager.createLoginSession(uname,account.getId());
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
                    Toast.makeText(LoginActivity.this, "username hoặc mật khẩu của bạn không không tồn tại ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}