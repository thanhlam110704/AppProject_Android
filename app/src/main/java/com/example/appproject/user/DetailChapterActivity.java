package com.example.appproject.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.adapter.ImageListAdapter;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;

import java.util.ArrayList;

public class DetailChapterActivity extends AppCompatActivity {
    ListView listView;
    TextView name_chapter;
    ArrayList<byte[]> images;
    Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chapter);

        Chapter chapter = (Chapter) getIntent().getSerializableExtra("chapter");
        comic = (Comic) getIntent().getSerializableExtra("comic");
        setTitle(chapter.getNameChap());
        addControls();
        if (chapter != null) {
            images= new ArrayList<>();
            images.add(chapter.getImg1());
            images.add(chapter.getImg2());
            images.add(chapter.getImg3());
            images.add(chapter.getImg4());
            images.add(chapter.getImg5());
            ImageListAdapter adapter = new ImageListAdapter(DetailChapterActivity.this, images);
            listView.setAdapter(adapter);
            name_chapter.setText(chapter.getNameChap());

        } else {
        }
    }

    private void addControls() {
        listView = findViewById(R.id.lvimg_detailchapter);
        name_chapter=findViewById(R.id.name_chapter_detailchapter);
    }

    @Override
    public void onBackPressed() {
        // Tạo một Intent mới
        Intent intent = new Intent(this, DetailComicActivity.class);
        intent.putExtra("comic", comic);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Xử lý khi nhấn nút back trên menu thông qua parentActivity
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
