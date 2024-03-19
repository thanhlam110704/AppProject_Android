package com.example.appproject.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.adapter.ChapterLvDetailAdapter;
import com.example.appproject.db.AccountDataHelper;
import com.example.appproject.db.ChapterDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.db.SessionManager;
import com.example.appproject.model.Account;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailComicActivity extends AppCompatActivity {
    AccountDataHelper accountDataHelper;
    boolean isFollowed;
    String id_user;
    SessionManager sessionManager;
    ChapterLvDetailAdapter adapter;
    ImageView comic_avatar;
    ChapterDataHelper chapterDataHelper;
    TextView comic_name, comic_description, comic_genres, comic_author, comic_dateupdate,comic_status,comic_view;
    ListView listViewchapter;
    MyDatabaseHelper dbHelper;
    List<Chapter> chapters;
    List<String> genres;
    List<String> name_chapters;
    Button btnfollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        // Lấy dữ liệu truyền qua Intent
        Comic comic = (Comic) getIntent().getSerializableExtra("comic");
        sessionManager= new SessionManager(DetailComicActivity.this);
        accountDataHelper= new AccountDataHelper(DetailComicActivity.this);
        // Kiểm tra xem comic có null hay không trước khi sử dụng
        if (comic != null) {
            addControls();
            // Hiển thị thông tin chi tiết của truyện và danh sách chapter
            byte[] imageByteArray = comic.getAvatar();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            chapterDataHelper = new ChapterDataHelper(DetailComicActivity.this);
            chapters = chapterDataHelper.getChaptersByComicId(comic.getId());
            dbHelper = new MyDatabaseHelper(DetailComicActivity.this);
            genres = dbHelper.getGenresByComicId(comic.getId());
            name_chapters = new ArrayList<>();
            int viewer = chapterDataHelper.getTotalViewsByComicId(comic.getId());
            List<String> genres = dbHelper.getGenresByComicId(comic.getId());
            // Hiển thị danh sách chapter
            for (Chapter chapter : chapters) {
                name_chapters.add(chapter.getNameChap());
            }

            // Hiển thị danh sách thể loại
            StringBuilder genreText = new StringBuilder();
            for (String genre : genres) {
                genreText.append(genre).append(", ");
            }
            // Xoá dấu "," cuối cùng nếu có
            if (genreText.length() > 0) {
                genreText.deleteCharAt(genreText.length() - 2);
            }

            // Truyền dữ liệu để hiển thị
            comic_avatar.setImageBitmap(bitmap);
            comic_name.setText(comic.getName());
            comic_description.setText(comic.getDescription());
            comic_genres.setText(genreText.toString());
            comic_status.setText(comic.getStatus());
            comic_author.setText(comic.getAuthor());
            comic_dateupdate.setText(comic.getDateUpdate());
            comic_view.setText(String.valueOf(viewer));
            // Khởi tạo và thiết lập Adapter cho listViewchapter
            adapter = new ChapterLvDetailAdapter(DetailComicActivity.this, chapters, comic);
            listViewchapter.setAdapter(adapter);
            HashMap<String, String> userDetails = sessionManager.getUserDetails();
            // Chức năng theo dõi
            // Chức năng theo dõi
            if (userDetails.get(SessionManager.KEY_IDUSER) != null) {
                id_user = userDetails.get(SessionManager.KEY_IDUSER);
                if (id_user != null && !id_user.equals("null")) {
                    isFollowed = dbHelper.isSaved(comic.getId(), Integer.parseInt(id_user));
                }
            }


            // Thiết lập trạng thái ban đầu của nút và hiển thị văn bản phù hợp
            if (isFollowed) {
                btnfollow.setText("Đã theo dõi");
                btnfollow.setEnabled(false); // Vô hiệu hóa nút khi đã theo dõi
            } else {
                btnfollow.setText("Theo dõi");
                btnfollow.setEnabled(true);
            }
            btnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Kiểm tra xem id_user có giá trị là null
                    if (id_user == null || id_user.equals("null")) { // Thêm kiểm tra id_user.equals("0")
                        showLoginDialog();
                    } else {
                        // Kiểm tra xem cặp id_comic và id_account đã được lưu hay chưa
                        if (!isFollowed) { // Nếu chưa được lưu
                            // Thêm cặp id_comic và id_account vào bảng save
                            dbHelper.addSave(comic.getId(), Integer.parseInt(id_user));
                            // Cập nhật trạng thái của nút và văn bản của nút
                            btnfollow.setText("Đã theo dõi");
                            btnfollow.setEnabled(false); // Vô hiệu hóa nút sau khi đã theo dõi
                            // Cập nhật trạng thái của biến cờ
                            isFollowed = true;
                            // Hiển thị thông báo cho người dùng
                            Toast.makeText(DetailComicActivity.this, "Truyện đã được theo dõi", Toast.LENGTH_SHORT).show();
                        } else {
                            // Nếu đã được lưu, không làm gì cả vì đã xử lý trạng thái này ở phần ban đầu
                        }
                    }
                }
            });
        }
        }
    // Hàm để hiển thị hộp thoại xác nhận yêu cầu đăng nhập
    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailComicActivity.this);
        builder.setMessage("Bạn cần đăng nhập để thực hiện thao tác này.")
                .setCancelable(false)
                .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(DetailComicActivity.this, LoginActivity.class);
                       startActivity(intent);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addControls() {
        comic_avatar = findViewById(R.id.comic_avatar_img_detail);
        comic_name = findViewById(R.id.comic_name_txt_detail);
        comic_description = findViewById(R.id.comic_description_txt_detail);
        comic_genres = findViewById(R.id.comic_genres_txt_detail);
        comic_view= findViewById(R.id.comic_view_txt_detail);
        comic_author = findViewById(R.id.comic_author_txt_detail);
        comic_status=findViewById(R.id.comic_status_txt_detail);
        comic_dateupdate = findViewById(R.id.comic_dateupdate_txt_detail);
        btnfollow=findViewById(R.id.btnfollow_detail);
        listViewchapter = findViewById(R.id.lvchapter);
    }
}
