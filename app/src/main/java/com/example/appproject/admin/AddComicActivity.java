package com.example.appproject.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appproject.R;
import com.example.appproject.db.ComicDataActivity;
import com.example.appproject.db.MyDatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddComicActivity extends AppCompatActivity {

    ActivityResultLauncher<String> resultLauncher;
    EditText namecomic_input, author_input, date_update_input, status_input, description_input;
    Button add_button, button_upload;
    ImageView avatar_input;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comic);
        namecomic_input = findViewById(R.id.namecomic_input);
        author_input = findViewById(R.id.author_input);
        date_update_input = findViewById(R.id.dateupdate);
        status_input = findViewById(R.id.status_input);
        add_button = findViewById(R.id.add_button);
        description_input = findViewById(R.id.description_input);
        button_upload = findViewById(R.id.button_upload);
        avatar_input = findViewById(R.id.avatar_input);
        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddComicActivity.this);
            if (selectedImageUri!= null) {
                byte[] avatarBytes = getBytesFromUri(selectedImageUri);
                myDB.addComic(namecomic_input.getText().toString().trim(),
                        description_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        status_input.getText().toString().trim(),
                        date_update_input.getText().toString().trim(),
                        avatarBytes);
                Intent intent = new Intent(AddComicActivity.this, ComicDataActivity.class);
                // Gắn vào Intent (nếu cần) bất kỳ dữ liệu bổ sung nào bạn muốn truyền đi
                // Ví dụ: intent.putExtra("key", value);
                startActivity(intent);
            }
            else {
                Toast.makeText(AddComicActivity.this,"ko hop le",Toast.LENGTH_LONG).show();
            }

            finish();
        });
        resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        avatar_input.setImageURI(result);
                        selectedImageUri = result;
                    }
                });



        button_upload.setOnClickListener(view -> {
            // Thay đổi cách gọi Intent.ACTION_PICK thành ResultLauncher
            resultLauncher.launch("image/*");
        });



    }
    private byte[] getBytesFromUri(Uri uri) {
        try {
            Log.d("AddComicActivity", "Uri: " + uri);

            if (uri != null) {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                if (inputStream != null) {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int bufferSize = 1024;
                        byte[] buffer = new byte[bufferSize];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            byteArrayOutputStream.write(buffer, 0, len);
                        }

                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        Log.d("AddComicActivity", "Avatar bytes length: " + (byteArray != null ? byteArray.length : 0));
                        return byteArray;
                    } finally {
                        inputStream.close();
                    }
                }
            }

            return new byte[0];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}