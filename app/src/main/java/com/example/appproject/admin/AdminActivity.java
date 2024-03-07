package com.example.appproject.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.appproject.R;
import com.example.appproject.db.ChapterDataActivity;
import com.example.appproject.db.ComicDataActivity;

public class AdminActivity extends AppCompatActivity {

    CardView cardComic,cardChapter,cardAccount,cardGenres,cardComments,cardLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        cardComic = findViewById(R.id.cardComic);
        cardChapter = findViewById(R.id.cardChapter);
        cardAccount = findViewById(R.id.cardAccount);
        cardGenres = findViewById(R.id.cardGenres);
        cardComments = findViewById(R.id.cardComments);
        cardLogout = findViewById(R.id.cardLogout);

        cardComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ComicDataActivity.class);
                startActivity(intent);
            }
        });
        cardChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ChapterDataActivity.class);
                startActivity(intent);
            }
        });
        cardAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Logout Clicked");
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}