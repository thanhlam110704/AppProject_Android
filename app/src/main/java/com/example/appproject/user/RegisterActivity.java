package com.example.appproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.DialogInterface;
import com.example.appproject.R;
import com.example.appproject.admin.AddComicActivity;
import com.example.appproject.db.MyDatabaseHelper;
import android.database.Cursor;
import android.widget.CompoundButton;
import android.text.InputType;
import android.content.Intent;
public class RegisterActivity extends AppCompatActivity {

    EditText username,password,phone,repassword;
    Button btnRegister;
    CheckBox checkBox2;
    private MyDatabaseHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        dbHelper = new MyDatabaseHelper(this);
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show/hide the password based on the checkbox state
                if (isChecked) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Move the cursor to the end of the text to ensure it's always visible
                password.setSelection(password.getText().length());
                repassword.setSelection(password.getText().length());
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname= username.getText().toString();
                String sdt= phone.getText().toString();
                String pword= password.getText().toString();
                String rpword = repassword.getText().toString();
                validateinfo(uname,sdt,pword,rpword);
                if (!hasError) {
                    dbHelper.addRegister(uname, pword, sdt);
                    showSuccessDialog();
                }


            }
        });
    }
    private boolean hasError = false;

    private void validateinfo(String uname, String sdt, String pword, String rpword) {
        StringBuilder errorMessage = new StringBuilder();
        hasError = false;

        if (uname.length() == 0) {
            errorMessage.append("Vui lòng nhập tên đăng nhập\n");
            username.setError("Vui lòng nhập tên đăng nhập");
            hasError = true;
        }
        if (isUsernameExists(uname)) {
            errorMessage.append("Tên đăng nhập đã tồn tại\n");
            username.setError("Tên đăng nhập đã tồn tại");
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
        if (isPhoneExists(sdt)) {
            errorMessage.append("Email đã được sử dụng\n");
            phone.setError("Email đã được sử dụng");
            hasError = true;
        }
        else if (sdt.length() == 0) {
            errorMessage.append("Vui lòng nhập email\n");
            phone.setError("Vui lòng nhập email");
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
        phone = findViewById(R.id.phone);
        btnRegister = findViewById(R.id.btnRegister);
        repassword = findViewById(R.id.repassword);
        checkBox2= findViewById(R.id.checkBox2);
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration Successful")
                .setMessage("Your registration was successful!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        // You can add any additional actions here if needed
                        dialog.dismiss();
                        // Optionally, you can finish the activity or navigate to another screen
                        finish();
                    }
                })
                .create()
                .show();
    }
    private boolean isUsernameExists(String username) {
        // Check if the username exists in the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + MyDatabaseHelper.TABLE_NAME8 + " WHERE " + MyDatabaseHelper.ID_USERNAME + "=?",
                new String[]{username}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }

    private boolean isPhoneExists(String phone) {
        // Check if the email exists in the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + MyDatabaseHelper.TABLE_NAME8 + " WHERE " + MyDatabaseHelper.ID_PHONE + "=?",
                new String[]{phone}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }
}