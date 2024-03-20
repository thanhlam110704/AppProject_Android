package com.example.appproject.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.firebase.auth.PhoneAuthOptions;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.example.appproject.db.MyDatabaseHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import com.example.appproject.R;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.FirebaseException;
import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private MyDatabaseHelper dbHelper;
    Button btnCon;
    EditText fpPhone;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();

        dbHelper = new MyDatabaseHelper(this);
        btnCon=findViewById(R.id.btnCon);
        fpPhone=findViewById(R.id.fpPhone);
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();

            }
        });
    }
    private void sendVerificationCode() {
        String phone = fpPhone.getText().toString().trim();

        if (!phone.isEmpty() && isPhoneExists(phone)) {
            // Start the phone number verification process
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                            .setPhoneNumber("+84" + phone)    // Phone number to verify, with country code
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout duration
                            .setActivity(this) // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                    // Auto-retrieval or instant verification completed successfully
                                    signInWithPhoneAuthCredential(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    // Verification failed
                                    Toast.makeText(ForgotPasswordActivity.this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    // Code has been sent to the provided phone number
                                    verificationId = s;
                                    // Navigate to a new activity where the user can enter the code
                                    Intent intent = new Intent(ForgotPasswordActivity.this, VerifyCodeActivity.class);
                                    intent.putExtra("mobile", phone);
                                    intent.putExtra("verificationId", verificationId);
                                    startActivity(intent);
                                }
                            })
                            .build();

            // Start the verification process
            PhoneAuthProvider.verifyPhoneNumber(options);
        } else {
            Toast.makeText(ForgotPasswordActivity.this, "Invalid phone number. Please enter a registered phone number.", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Successfully signed in
                        Toast.makeText(ForgotPasswordActivity.this, "Verification successful.", Toast.LENGTH_SHORT).show();
                        // Proceed to the password reset or any other action
                        // ...

                    } else {
                        // Verification failed
                        Toast.makeText(ForgotPasswordActivity.this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private boolean isPhoneExists(String phone) {
        // Check if the email exists in the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + MyDatabaseHelper.TABLE_NAME3 + " WHERE " + MyDatabaseHelper.PHONE + "=?",
                new String[]{phone}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }
}