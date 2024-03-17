package com.example.appproject.user;

import androidx.appcompat.app.AppCompatActivity;

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
            int viewer= chapterDataHelper.getTotalViewsByComicId(comic.getId());
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
            adapter = new ChapterLvDetailAdapter(DetailComicActivity.this, chapters,comic);
            listViewchapter.setAdapter(adapter);

            // Chức năng theo dõi
            HashMap<String, String> userDetails = sessionManager.getUserDetails();
            String id_user  = userDetails.get(SessionManager.KEY_IDUSER);
            btnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.addSave(comic.getId(),Integer.parseInt(id_user));
                }
            });

        } else {
            Toast.makeText(DetailComicActivity.this,"dữ liệu comic null",Toast.LENGTH_LONG).show();
        }
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
