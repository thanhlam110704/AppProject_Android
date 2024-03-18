package com.example.appproject.db;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.adapter.CustomChapterAdapter;
import com.example.appproject.admin.AddChapterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChapterDataActivity extends AppCompatActivity {

    CustomChapterAdapter customChapterAdapter;
    ImageView empty_imageview2;
    TextView no_data2;

    RecyclerView recyclerView2;
    FloatingActionButton add_button2;
    MyDatabaseHelper mydb;
    ArrayList<String> chapter_id ,chapter_name,chapter_viewer,chapter_datepulish,chapter_idcomic;
    ArrayList<List<byte[]>> chapter_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_data);
        recyclerView2 = findViewById(R.id.recyclerView2);
        add_button2 = findViewById(R.id.add_button2);
        empty_imageview2 = findViewById(R.id.empty_imageview2);
        no_data2 = findViewById(R.id.no_data2);
        add_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapterDataActivity.this, AddChapterActivity.class);
                startActivity(intent);
            }
        });
        mydb = new MyDatabaseHelper(ChapterDataActivity.this);
        chapter_id = new ArrayList<>();
        chapter_name = new ArrayList<>();
        chapter_viewer = new ArrayList<>();
        chapter_datepulish = new ArrayList<>();
        chapter_idcomic = new ArrayList<>();

        storeDataInArrays();

        customChapterAdapter = new CustomChapterAdapter(ChapterDataActivity.this,this, chapter_id ,chapter_name,chapter_viewer,chapter_datepulish,chapter_img,chapter_idcomic
        );
        recyclerView2.setAdapter(customChapterAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(ChapterDataActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    public void storeDataInArrays() {
        Cursor cursor = mydb.readAllDataChapter();

        try {
            // Kiểm tra xem cursor có dữ liệu hay không
            if (cursor != null && cursor.getCount() > 0) {
                // Kiểm tra và khởi tạo chapter_img nếu nó là null
                if (chapter_img == null) {
                    chapter_img = new ArrayList<>();
                }

                while (cursor.moveToNext()) {
                    List<byte[]> singleChapterImages = new ArrayList<>();
                    chapter_img.add(singleChapterImages);
                    chapter_id.add(cursor.getString(0));
                    chapter_name.add(cursor.getString(1));
                    chapter_viewer.add(cursor.getString(2));
                    chapter_datepulish.add(cursor.getString(3));
                    chapter_idcomic.add(cursor.getString(9));

                    // Tạo danh sách để chứa dữ liệu BLOB từ các cột IMG1 đến IMG5

                    // Lấy giá trị BLOB từ cột IMG1
                    byte[] img1Bytes = cursor.getBlob(4);
                    if (img1Bytes != null) {
                        singleChapterImages.add(img1Bytes);
                    }

                    // Lấy giá trị BLOB từ cột IMG2
                    byte[] img2Bytes = cursor.getBlob(5);
                    if (img2Bytes != null) {
                        singleChapterImages.add(img2Bytes);
                    }

                    // Lấy giá trị BLOB từ cột IMG3
                    byte[] img3Bytes = cursor.getBlob(6);
                    if (img3Bytes != null) {
                        singleChapterImages.add(img3Bytes);
                    }

                    // Lấy giá trị BLOB từ cột IMG4
                    byte[] img4Bytes = cursor.getBlob(7);
                    if (img4Bytes != null) {
                        singleChapterImages.add(img4Bytes);
                    }

                    // Lấy giá trị BLOB từ cột IMG5
                    byte[] img5Bytes = cursor.getBlob(8);
                    if (img5Bytes != null) {
                        singleChapterImages.add(img5Bytes);
                    }

                    // Thêm danh sách ảnh của chương vào chapter_img
                }

                // Ẩn thông báo không có dữ liệu
                empty_imageview2.setVisibility(View.GONE);
                no_data2.setVisibility(View.GONE);
            } else {
                // Hiển thị thông báo không có dữ liệu
                empty_imageview2.setVisibility(View.VISIBLE);
                no_data2.setVisibility(View.VISIBLE);
            }
        } finally {
            // Đảm bảo rằng cursor sẽ được đóng ngay cả khi có ngoại lệ xảy ra
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // Tiếp tục xử lý hoặc hiển thị dữ liệu nếu cần
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.delete_all){
            Toast.makeText(this, "Xóa dữ liệu", Toast.LENGTH_SHORT).show();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn xóa tất cả ?");
        builder.setMessage("Bạn có chắc là muốn xóa hết tất cả dữ liệu ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB= new MyDatabaseHelper(ChapterDataActivity.this);
                myDB.deleteAllData();
                Intent intent= new Intent(ChapterDataActivity.this,ChapterDataActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}