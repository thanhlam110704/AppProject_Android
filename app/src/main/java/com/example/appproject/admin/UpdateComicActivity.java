package com.example.appproject.admin;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class UpdateComicActivity extends AppCompatActivity {

    ActivityResultLauncher<String> resultLauncher;

    EditText namecomic_input, author_input, date_update_input,status_input,description_input;
    ImageView avatar_input;
    Button update_button, delete_button,button_upload;
    String id, name, author,description, status, dateupdate;
    byte[] avatar;
    Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comic);
        namecomic_input=findViewById(R.id.namecomic_input2);
        author_input=findViewById(R.id.author_input2);
        date_update_input=findViewById(R.id.dateupdate2);
        status_input=findViewById(R.id.status_input2);
        description_input=findViewById(R.id.description_input2);
        avatar_input = findViewById(R.id.avatar_input2);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);
        getandsetIntentData();
        button_upload = findViewById(R.id.button_upload); // Add this line to initialize button_uploa
        button_upload.setOnClickListener(view -> {
            // Thay đổi cách gọi Intent.ACTION_PICK thành ResultLauncher
            resultLauncher.launch("image/*");
        });
        ActionBar ab= getSupportActionBar();
        if(ab!=null)
        {
            ab.setTitle(name);
        }
        update_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateComicActivity.this);

            // Lấy dữ liệu từ EditText
            name = namecomic_input.getText().toString().trim();
            description = description_input.getText().toString().trim();
            author = author_input.getText().toString().trim();
            status = status_input.getText().toString().trim();
            dateupdate = date_update_input.getText().toString().trim();

            try {
                // Kiểm tra giá trị của các biến trước khi thực hiện cập nhật
                if (!name.isEmpty() && !description.isEmpty() && !author.isEmpty() && !status.isEmpty() && !dateupdate.isEmpty()) {

                    // Nếu có ảnh mới, cập nhật avatar từ ảnh mới
                    if (selectedImageUri != null) {
                        byte[] avatarBytes = getBytesFromUri(selectedImageUri);
                        avatar = avatarBytes;
                    }

                    // Thực hiện cập nhật dữ liệu vào cơ sở dữ liệu
                    myDB.updateData_comic(id, name, description, author, status, dateupdate, avatar);
                    finish();

                } else {
                    // Hiển thị thông báo nếu có trường dữ liệu bị rỗng
                    Toast.makeText(UpdateComicActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                // Xử lý ngoại lệ khi có vấn đề với cơ sở dữ liệu
                e.printStackTrace();
                Toast.makeText(UpdateComicActivity.this, "Error updating data. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });



        delete_button.setOnClickListener(view -> confirmDialog());

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
    public void getandsetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("name")&& getIntent().hasExtra("description")&& getIntent().hasExtra("author")
                &&getIntent().hasExtra("status")&&getIntent().hasExtra("dateupdate") && getIntent().hasExtra("avatar")) {
            // Lấy dữ liệu từ Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");
            author = getIntent().getStringExtra("author");
            status = getIntent().getStringExtra("status");
            dateupdate = getIntent().getStringExtra("dateupdate");
            avatar = getIntent().getByteArrayExtra("avatar");


            // Setting dữ liệu từ Intent
            if (id != null && name != null && description != null && author != null
                    && status != null && dateupdate != null && avatar != null) {

                // Setting dữ liệu từ Intent
                namecomic_input.setText(name);
                author_input.setText(author);
                date_update_input.setText(dateupdate);
                status_input.setText(status);
                description_input.setText(description);

                // Hiển thị ảnh nếu có
                if (avatar.length > 0) {
                    Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
                    avatar_input.setImageBitmap(avatarBitmap);
                } else {
                    // Nếu không có ảnh, ẩn ImageView
                    avatar_input.setVisibility(View.GONE);
                    // Có thể ẩn các EditText tương ứng nếu muốn
                }

                Log.d("stev", name + " " + author + " " + description + " " + status + " " + dateupdate + " " + avatar);

            } else {
                Toast.makeText(this, "Incomplete or invalid data.", Toast.LENGTH_SHORT).show();
                finish(); // Kết thúc Activity nếu dữ liệu không hợp lệ
            }
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc Activity nếu không có dữ liệu
        }
    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateComicActivity.this);
                mydb.deleteOneRow_comic(id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}