package com.example.appproject.admin;

import android.content.ClipData;
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
import com.example.appproject.db.MyDatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddChapterActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;
    List<Uri> selectedImageUris;
    List<ImageView> imgInputs;
    EditText chapter_input, date_publish_input, viewer_input,idcomic_input;

    Button add_button,upload_button_chapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        chapter_input=findViewById(R.id.chapter_input);
        date_publish_input=findViewById(R.id.date_publish_input);
        viewer_input=findViewById(R.id.viewer_input);
        idcomic_input=findViewById(R.id.idcomic_input);
        imgInputs = new ArrayList<>();
        selectedImageUris = new ArrayList<>();
        imgInputs.add(findViewById(R.id.img1_input));
        imgInputs.add(findViewById(R.id.img2_input));
        imgInputs.add(findViewById(R.id.img3_input));
        imgInputs.add(findViewById(R.id.img4_input));
        imgInputs.add(findViewById(R.id.img5_input));
        upload_button_chapter=findViewById(R.id.button_uploadfile);
        add_button=findViewById(R.id.add_button1);
        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddChapterActivity.this);
            String chapter = chapter_input.getText().toString().trim();
            String datePublish = date_publish_input.getText().toString().trim();
            String viewer = viewer_input.getText().toString().trim();
            String idComic = idcomic_input.getText().toString().trim();

            if (selectedImageUris != null && !selectedImageUris.isEmpty()) {
                if (!chapter.isEmpty() && !datePublish.isEmpty() && !viewer.isEmpty() && !idComic.isEmpty()) {
                    List<byte[]> imgBytesList = new ArrayList<>();
                    for (Uri selectedImageUri : selectedImageUris) {
                        byte[] imgBytes = getBytesFromUri(selectedImageUri);
                        imgBytesList.add(imgBytes);
                    }
                    myDB.addChapter(chapter, viewer, datePublish, imgBytesList, idComic);
                } else {
                    Toast.makeText(AddChapterActivity.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddChapterActivity.this, "Không có hình ảnh nào được chọn", Toast.LENGTH_SHORT).show();
            }
        });




        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                ClipData clipData = result.getData().getClipData();
                int currentImageIndex = 0;
                if (clipData != null) {
                    // Reset danh sách hình ảnh đã chọn
                    selectedImageUris.clear();

                    currentImageIndex = 0;

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();

                        // Kiểm tra xem có ImageView trống không
                        if (currentImageIndex < imgInputs.size()) {
                            ImageView currentImageView = imgInputs.get(currentImageIndex);
                            currentImageView.setImageURI(imageUri);
                            selectedImageUris.add(imageUri);

                            // Tăng giá trị của biến đếm
                            currentImageIndex++;
                        } else {
                            Toast.makeText(AddChapterActivity.this, "Không có ImageView trống", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else {
                    Uri imageUri = result.getData().getData();

                    // Kiểm tra xem có ImageView trống không
                    if (currentImageIndex < imgInputs.size()) {
                        ImageView currentImageView = imgInputs.get(currentImageIndex);
                        currentImageView.setImageURI(imageUri);
                        selectedImageUris.add(imageUri);
                    } else {
                        Toast.makeText(AddChapterActivity.this, "Không có ImageView trống", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        upload_button_chapter.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            resultLauncher.launch(intent);
        });
    }


    private byte[] getBytesFromUri(Uri uri) {
        try {
            Log.d("AddChapterActivity", "Uri: " + uri);

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
                        Log.d("AddChapterActivity", "Avatar bytes length: " + (byteArray != null ? byteArray.length : 0));
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