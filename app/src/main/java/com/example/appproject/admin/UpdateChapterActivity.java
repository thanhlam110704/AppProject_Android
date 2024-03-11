package com.example.appproject.admin;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appproject.R;
import com.example.appproject.db.MyDatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UpdateChapterActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;
    List<Uri> selectedImageUris;
    List<ImageView> chapter_imgs;
    EditText chapter_input, date_publish_input,viewer_input,idcomic_input;
    Button update_button, delete_button, upload_button_chapter;
    String id, chapter, datepublish,viewer, idcomic;
    List<byte[]> imgBytesList,listimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chapter);
        chapter_input=findViewById(R.id.chapter_input2);
        date_publish_input=findViewById(R.id.date_publish_input2);
        viewer_input=findViewById(R.id.viewer_input2);
        idcomic_input=findViewById(R.id.idcomic_input2);
        chapter_imgs=new ArrayList<>();
        chapter_imgs.add(findViewById(R.id.img1_input2));
        chapter_imgs.add(findViewById(R.id.img2_input2));
        chapter_imgs.add(findViewById(R.id.img3_input2));
        chapter_imgs.add(findViewById(R.id.img4_input2));
        chapter_imgs.add(findViewById(R.id.img5_input2));
        update_button=findViewById(R.id.update_button2);
        delete_button=findViewById(R.id.delete_button2);
        upload_button_chapter=findViewById(R.id.button_uploadfile2);
        getandsetIntentData();
        ActionBar ab= getSupportActionBar();
        if(ab!=null)
        {
            ab.setTitle(chapter);
        }
        update_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateChapterActivity.this);
            try {
                // Kiểm tra giá trị của các biến trước khi thực hiện cập nhật
                if (!chapter.isEmpty() && !viewer.isEmpty() && !datepublish .isEmpty() && !idcomic.isEmpty()) {

                    // Nếu có ảnh mới, cập nhật avatar từ ảnh mới
                    if (selectedImageUris!=null) {
                        imgBytesList = new ArrayList<>();
                        for (Uri selectedImageUri : selectedImageUris) {
                            byte[] imgBytes = getBytesFromUri(selectedImageUri);
                            imgBytesList.add(imgBytes);
                        }
                        listimg=imgBytesList;
                    }
                    chapter = chapter_input.getText().toString().trim();
                    viewer = viewer_input.getText().toString().trim();
                    datepublish = date_publish_input.getText().toString().trim();
                    idcomic = idcomic_input.getText().toString().trim();
                    // Thực hiện cập nhật dữ liệu vào cơ sở dữ liệu
                    myDB.updateData_chapter(id, chapter, viewer, datepublish, imgBytesList ,idcomic);
                    finish();

                } else {
                    // Hiển thị thông báo nếu có trường dữ liệu bị rỗng
                    Toast.makeText(UpdateChapterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                // Xử lý ngoại lệ khi có vấn đề với cơ sở dữ liệu
                e.printStackTrace();
                Toast.makeText(UpdateChapterActivity.this, "Error updating data. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        delete_button.setOnClickListener(view -> confirmDialog());
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
                        if (currentImageIndex < chapter_imgs.size()) {
                            ImageView currentImageView = chapter_imgs.get(currentImageIndex);
                            currentImageView.setImageURI(imageUri);
                            selectedImageUris.add(imageUri);

                            // Tăng giá trị của biến đếm
                            currentImageIndex++;
                        } else {
                            Toast.makeText(UpdateChapterActivity.this, "Không có ImageView trống", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else {
                    Uri imageUri = result.getData().getData();

                    // Kiểm tra xem có ImageView trống không
                    if (currentImageIndex < chapter_imgs.size()) {
                        ImageView currentImageView = chapter_imgs.get(currentImageIndex);
                        currentImageView.setImageURI(imageUri);
                        selectedImageUris.add(imageUri);
                    } else {
                        Toast.makeText(UpdateChapterActivity.this, "Không có ImageView trống", Toast.LENGTH_SHORT).show();
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

    public void getandsetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("chapter")&& getIntent().hasExtra("viewer")
                &&getIntent().hasExtra("datepublish")&&getIntent().hasExtra("idcomic") && getIntent().hasExtra("img")){
            // Lấy dữ liệu từ Intent
            id = getIntent().getStringExtra("id");
            chapter=getIntent().getStringExtra("chapter");
            viewer=getIntent().getStringExtra("viewer");
            datepublish=getIntent().getStringExtra("datepublish");
            idcomic=getIntent().getStringExtra("idcomic");
            listimg = (List<byte[]>) getIntent().getSerializableExtra("img");


            // Setting dữ liệu từ Intent
            chapter_input.setText(chapter);
            viewer_input.setText(viewer);
            date_publish_input.setText(datepublish);
            idcomic_input.setText(idcomic);
            if (listimg != null && !listimg.isEmpty()) {
                // Set images to ImageView
                for (int i = 0; i < Math.min(listimg.size(), chapter_imgs.size()); i++) {
                    byte[] imgBytes = listimg.get(i);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                    chapter_imgs.get(i).setImageBitmap(bitmap);
                }
            }


            Log.d("stev", chapter+" "+viewer+" "+datepublish+" "+idcomic+" "+chapter_imgs);

        } else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + chapter+ " ?");
        builder.setMessage("Are you sure you want to delete " + chapter+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateChapterActivity.this);
                mydb.deleteOneRow_chapter(id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
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