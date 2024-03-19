package com.example.appproject.user;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.admin.UpdateComicActivity;
import com.example.appproject.db.AccountDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.db.SessionManager;
import com.example.appproject.model.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    ActivityResultLauncher<String> resultLauncher;// ảnh
    Uri selectedImageUri;
    SessionManager sessionManager;
    AccountDataHelper accountDataHelper;
    CircleImageView circleImageView;
    Account account;
    Button btnUpdate;
    EditText edtfullname, edtphone,edtemail;
    String id,username, phone,email;
    byte[] avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        circleImageView= findViewById(R.id.imgavar);
        edtfullname=findViewById(R.id.edtfullname);
        edtphone=findViewById(R.id.edtnumber);
        edtemail=findViewById(R.id.edtemail);
        btnUpdate=findViewById(R.id.btnUpdateProfile);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            // Xử lý kết quả khi người dùng chọn ảnh
            if (uri != null) {
                selectedImageUri = uri;
                // Hiển thị ảnh đã chọn trong ImageView
                circleImageView.setImageURI(uri);
            }
        });
        sessionManager = new SessionManager(ProfileActivity.this);
        accountDataHelper = new AccountDataHelper(ProfileActivity.this);
        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        id = userDetails.get(SessionManager.KEY_IDUSER);
        account = accountDataHelper.getAccountById(Integer.parseInt(id));
        getandsetDataAccount();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi cách gọi Intent.ACTION_PICK thành ResultLauncher
                resultLauncher.launch("image/*");
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= edtemail.getText().toString().trim();
                phone = edtphone.getText().toString().trim();
                MyDatabaseHelper myDB = new MyDatabaseHelper(ProfileActivity.this);
                try {
                    // Kiểm tra giá trị của các biến trước khi thực hiện cập nhật
                    if (!email.isEmpty() && !phone.isEmpty()) {
                        // Kiểm tra định dạng email
                        if (isValidEmail(email)) {
                            // Kiểm tra độ dài số điện thoại
                            if (phone.length() == 11) {
                                // Nếu có ảnh mới, cập nhật avatar từ ảnh mới
                                if (selectedImageUri != null) {
                                    byte[] avatarBytes = getBytesFromUri(selectedImageUri);
                                    avatar = avatarBytes;
                                    Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
                                    circleImageView.setImageBitmap(avatarBitmap);
                                }
                                // Thực hiện cập nhật dữ liệu vào cơ sở dữ liệu
                                myDB.updateData_account(id, email, phone, avatar);
                            } else {
                                Toast.makeText(ProfileActivity.this, "Số điện thoại phải có đủ 11 số.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Sai format email .", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Hiển thị thông báo nếu có trường dữ liệu bị rỗng
                        Toast.makeText(ProfileActivity.this, "Làm ơn đừng bỏ trống thông tin.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // Xử lý ngoại lệ khi có vấn đề với cơ sở dữ liệu
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Error updating data. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        // Biểu thức chính quy kiểm tra định dạng email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
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
    private void getandsetDataAccount() {
        if(account!=null)
        {

            // get dữ liệu
            username= account.getUsername();
            email=account.getEmail();
            phone=account.getPhone();
            avatar= account.getAvatar();

            //set dữ liệu
            edtemail.setText(email);
            edtfullname.setText(username);
            edtphone.setText(phone);
            Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            circleImageView.setImageBitmap(avatarBitmap);
        }
        else {
            Toast.makeText(ProfileActivity.this, "Data acccount is null", Toast.LENGTH_SHORT).show();
        }


    }

}