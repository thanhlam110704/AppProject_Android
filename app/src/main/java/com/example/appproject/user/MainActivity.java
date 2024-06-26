package com.example.appproject.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.adapter.ComicAdapter;
import com.example.appproject.adapter.ComicArrayAdapter;
import com.example.appproject.adapter.ItemSearchAdapter;
import com.example.appproject.admin.AdminActivity;
import com.example.appproject.db.ComicDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.db.SessionManager;
import com.example.appproject.model.Comic;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ComicAdapter comicAdapter;
    SessionManager sessionManager;
    ComicArrayAdapter comicArrayAdapter;
    List<Comic> comicList,comicList2;
    ComicDataHelper comic_helper,comic_helper2;
    TextView textView;
    DrawerLayout drawerLayout;
    ListView listView;
    ArrayList<Comic> arrayList_search;
    ItemSearchAdapter adaptersearch;
    NavigationView navigationView;
    Toolbar toolbar;
    GridView gdvDSTruyen;
    RecyclerView rcvCategory;
    public boolean isSearching = false;
    Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Trang chủ");
        addEventBanner();
        addEventMenu();
        addEventProduct();
        addMenuSearch();




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    /*Xử lý danh sách truyện tranh để cử */
    private void addEventBanner() {
        comic_helper= new ComicDataHelper(MainActivity.this);
        rcvCategory = findViewById(R.id.rcv_category);
        comicList= comic_helper.getComicsByMonth();
        comicAdapter = new ComicAdapter(comicList,MainActivity.this);
        rcvCategory.setAdapter(comicAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCategory.setLayoutManager((linearLayoutManager));
    }
    /*Xử lý danh sách truyện tranh để cử */
    /*Xử lý danh sách truyện tranh */
    private void addEventProduct() {
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
        comic_helper2= new ComicDataHelper(MainActivity.this);
        comicList2= comic_helper2.getAllComics();
        comicArrayAdapter = new ComicArrayAdapter(this,0,comicList2);
        gdvDSTruyen.setAdapter(comicArrayAdapter);
    }
    /*Xử lý danh sách truyện tranh */

    //Xử lý menu và search
    private void addEventMenu() {
        /*Hook*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        textView=findViewById(R.id.textViewyeuthich);
        /*Toolbar*/
        setSupportActionBar(toolbar);
        menu=navigationView.getMenu();
        menu.findItem(R.id.nav_Logout).setVisible(false);
        menu.findItem(R.id.nav_rate).setVisible(false);
        menu.findItem(R.id.nav_Profile).setVisible(false);
        menu.findItem(R.id.nav_Favor).setVisible(false);
        sessionManager= new SessionManager(MainActivity.this);
        if(sessionManager.isLoggedIn())
        {
            menu.findItem(R.id.nav_Profile).setVisible(true);
            menu.findItem(R.id.nav_Logout).setVisible(true);
            menu.findItem(R.id.nav_rate).setVisible(true);
            menu.findItem(R.id.nav_Login).setVisible(false);
            menu.findItem(R.id.nav_Register).setVisible(false);
            menu.findItem(R.id.nav_Favor).setVisible(true);
        }

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getMenuInflater().inflate(R.menu.main_menu, toolbar.getMenu());
        navigationView.setCheckedItem(R.id.nav_home);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm ở đây");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    isSearching = false; // Not searching, show TextView
                    textView.setVisibility(View.VISIBLE);
                    rcvCategory.setVisibility(View.VISIBLE);
                    gdvDSTruyen.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    isSearching = true; // Searching, show ListView
                    textView.setVisibility(View.GONE);
                    rcvCategory.setVisibility(View.GONE);
                    gdvDSTruyen.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                    // Filter the arrayList_search based on the story name and genre
                    ArrayList<Comic> filteredList = new ArrayList<>();
                    for (Comic comic : arrayList_search) {
                        if (comic.getName().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(comic);
                        } else {
                            MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
                            List<String> genres = dbHelper.getGenresByComicId(comic.getId());
                            for (String genre : genres) {
                                if (genre.toLowerCase().contains(newText.toLowerCase())) {
                                    filteredList.add(comic);
                                    break; // Break the loop once a match is found for the genre
                                }
                            }
                        }
                    }

                    // Update the adapter with the filtered list
                    adaptersearch = new ItemSearchAdapter(MainActivity.this, R.layout.layout_item_search, filteredList);
                    listView.setAdapter(adaptersearch);
                }
                return false;
            }
        });

        return true;
    }



    private void addMenuSearch() {
        listView = findViewById(R.id.listViewitem);
        arrayList_search = new ArrayList<>();
        comic_helper2= new ComicDataHelper(MainActivity.this);
        arrayList_search=  comic_helper2.getAllComics();
        rcvCategory.setVisibility(View.VISIBLE);
        gdvDSTruyen.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        adaptersearch = new ItemSearchAdapter(this, R.layout.layout_item_search, arrayList_search);
        listView.setAdapter(adaptersearch);

    }
    //Xử lý menu và search





    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.nav_home){
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_Login){
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_Register){
            Intent intent= new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_rate){
            Intent intent= new Intent(this,RatingActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_fanpage){
            Intent intent= new Intent(this,FanpageActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_Profile){
            Intent intent= new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_Favor){
            Intent intent= new Intent(this,SaveActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.nav_Logout){
            sessionManager.logoutUser();
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }







}




