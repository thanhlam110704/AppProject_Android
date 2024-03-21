package com.example.appproject.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.R;


public class ResetPasswordActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText repassword,rrpword;
    private Button btnrpword;
    private String phone;
    private CheckBox rpcheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        rrpword=findViewById(R.id.rrpword);
        dbHelper = new MyDatabaseHelper(this);
        repassword = findViewById(R.id.rpword);
        btnrpword = findViewById(R.id.btnrpword);
        rpcheckBox=findViewById(R.id.rpcheckBox);
        Intent intent= getIntent();
        phone = intent.getStringExtra("mobile");
        rpcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show/hide the password based on the checkbox state
                if (isChecked) {
                    repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    rrpword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    rrpword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Move the cursor to the end of the text to ensure it's always visible
                repassword.setSelection(repassword.getText().length());
                rrpword.setSelection(rrpword.getText().length());
            }
        });
        btnrpword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String newPassword = repassword.getText().toString().trim();
        // Assuming you have stored the phone number in a variable named phoneNumber

        dbHelper.updatePassword(phone, newPassword);
        Toast.makeText(this, "Password updated successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish the activity after password reset
    }
}