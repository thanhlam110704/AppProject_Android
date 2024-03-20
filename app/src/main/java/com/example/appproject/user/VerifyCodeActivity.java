package com.example.appproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import android.widget.Toast;
import com.example.appproject.R;

public class VerifyCodeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    Button btnCon2;
    EditText fpCode;
    private String verificationId,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_code);
        firebaseAuth = FirebaseAuth.getInstance();
        btnCon2=findViewById(R.id.btnCon2);
        fpCode=findViewById(R.id.fpCode);
        Intent intent = getIntent();
        phone = intent.getStringExtra("mobile");
        verificationId = getIntent().getStringExtra("verificationId");
        btnCon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();

            }
        });
    }
    private void verifyCode() {
        String code = fpCode.getText().toString().trim();

        if (!code.isEmpty()) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential,phone);
        } else {
            Toast.makeText(this, "Please enter the verification code.", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final String phone) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Verification successful, proceed to the password reset or any other action
                        Toast.makeText(VerifyCodeActivity.this, "Verification successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerifyCodeActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("mobile", phone);
                        startActivity(intent);
                        // ...

                    } else {
                        // Verification failed
                        Toast.makeText(VerifyCodeActivity.this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}