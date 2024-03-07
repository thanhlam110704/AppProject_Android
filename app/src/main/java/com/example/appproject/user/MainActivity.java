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

import androidx.annotation.NonNull;
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
import com.example.appproject.adapter.DanhMucAdapter;
import com.example.appproject.adapter.ItemSearchAdapter;
import com.example.appproject.adapter.TruyenTranhAdapter;
import com.example.appproject.fragment.FanpageFragment;
import com.example.appproject.fragment.ProfileFragment;
import com.example.appproject.fragment.RatingFragment;
import com.example.appproject.fragment.RegisterFragment;
import com.example.appproject.object.DanhMuc;
import com.example.appproject.object.ItemSearch;
import com.example.appproject.object.TruyenTranh;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FragMent_Home=1;
    private static final int FragMent_Login=2;
    private static final int FragMent_Favor=3;
    private static final int FragMent_Rating=4;
    private static final int FragMent_Fanpage=5;
    private static final int FragMent_Profile=6;
    private static final int FragMent_Register=7;
    private int currentFrageMent;


    TextView textView;
    DrawerLayout drawerLayout;
    ListView listView;
    ArrayList<ItemSearch> arrayList_search;
    ItemSearchAdapter adaptersearch;
    NavigationView navigationView;
    Toolbar toolbar;
    GridView gdvDSTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    RecyclerView rcvCategory;


    DanhMucAdapter categoryAdapter;
    public boolean isSearching = false;
    private View includedLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        addEventMenu();
        addEventProduct();
        addEventBanner();
        addMenuSearch();

    }




    /*Xử lý danh sách truyện tranh để cử */
    private void addEventBanner() {
        rcvCategory = findViewById(R.id.rcv_category);
        categoryAdapter = new DanhMucAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager((linearLayoutManager));

        List<DanhMuc> data = getListCategory();
        categoryAdapter.setData(data);
        rcvCategory.setAdapter(categoryAdapter);
    }

    public List<DanhMuc> getListCategory() {
        List<DanhMuc> listCategory = new ArrayList<>();

        List<TruyenTranh> listBook = new ArrayList<>();
        listBook.add(new TruyenTranh("Ta Ở Tu Tiên Giới Chỉ Làm Giờ Hành Chính", "Chapter 12", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        listBook.add(new TruyenTranh("Book 2", "Chapter 12", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        listBook.add(new TruyenTranh("Book 3", "Chapter 12", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        listBook.add(new TruyenTranh("Book 4", "Chapter 12", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));


        listCategory.add(new DanhMuc("Truyện mới", listBook));
        return listCategory;
    }

    /*Xử lý danh sách truyện tranh để cử */
    private void addEventProduct() {

        inittruyentranh();
        anhXa();
        setUp();
        setClick();
    }


    /*Xử lý danh sách truyện tranh */
    private void setClick() {
        gdvDSTruyen.setAdapter(adapter);
    }

    private void setUp() {
    }


    private void inittruyentranh() {
        truyenTranhArrayList = new ArrayList<>();
        truyenTranhArrayList.add(new TruyenTranh("Ta Ở Tu Tiên Giới Chỉ Làm Giờ Hành Chính", "Chapter 51.5", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Chuyển Sinh Thành Liễu Đột Biến", "Chapter 166", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Nhân Sinh Thâm Tiềm", "Chapter 4", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Kết Hôn Với Người Quyền Lực Nhất Hành Tinh", "Chapter 35", "https://st.nettruyenclub.com/data/comics/222/chuyen-sinh-thanh-lieu-dot-bien-4917.jpg"));
        adapter = new TruyenTranhAdapter(this, 0, truyenTranhArrayList);
    }

    private void anhXa() {
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
    }




    /*Xử lý thanh menu */
    private void addEventMenu() {
        /*Hook*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        textView=findViewById(R.id.textViewyeuthich);
        /*Toolbar*/
        setSupportActionBar(toolbar);

        Menu menu= navigationView.getMenu();
        menu.findItem(R.id.nav_Logout).setVisible(false);
        menu.findItem(R.id.nav_Profile).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getMenuInflater().inflate(R.menu.main_menu, toolbar.getMenu());
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
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

                    // Filter the arrayList_search based on the story name
                    ArrayList<ItemSearch> filteredList = new ArrayList<>();
                    for (ItemSearch item : arrayList_search) {
                        if (item.getTenTruyen().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
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
        arrayList_search.add(new ItemSearch("Conan","Chap11","Adult",R.drawable.hinh1));
        arrayList_search.add(new ItemSearch("Doraemon","Chap11","Adult",R.drawable.hinh2));
        arrayList_search.add(new ItemSearch("Chú thuật sư","Chap11","Adult",R.drawable.hinh3));
        arrayList_search.add(new ItemSearch("Naruto","Chap11","Adult",R.drawable.hinh4));
        arrayList_search.add(new ItemSearch("Chuyển sinh ","Chap11","Adult",R.drawable.hinh5));
        arrayList_search.add(new ItemSearch("Goblin","Chap11","Adult",R.drawable.hinh6));
        arrayList_search.add(new ItemSearch("Bảy viên ngọc rồng","Chap11","Adult",R.drawable.hinh1));
        arrayList_search.add(new ItemSearch("ABC","Chap11","Adult",R.drawable.hinh2));
        arrayList_search.add(new ItemSearch("Conan3","Chap11","Adult",R.drawable.hinh3));
        arrayList_search.add(new ItemSearch("Conan4","Chap11","Adult",R.drawable.hinh4));
        arrayList_search.add(new ItemSearch("Conan5","Chap11","Adult",R.drawable.hinh5));
        arrayList_search.add(new ItemSearch("Conan6","Chap11","Adult",R.drawable.hinh6));
        arrayList_search.add(new ItemSearch("Conan1","Chap11","Adult",R.drawable.hinh1));
        arrayList_search.add(new ItemSearch("Conan2","Chap11","Adult",R.drawable.hinh2));
        arrayList_search.add(new ItemSearch("Conan3","Chap11","Adult",R.drawable.hinh3));
        arrayList_search.add(new ItemSearch("Conan4","Chap11","Adult",R.drawable.hinh4));
        arrayList_search.add(new ItemSearch("Conan5","Chap11","Adult",R.drawable.hinh5));
        arrayList_search.add(new ItemSearch("Conan6","Chap11","Adult",R.drawable.hinh6));

        rcvCategory.setVisibility(View.VISIBLE);
        gdvDSTruyen.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        adaptersearch = new ItemSearchAdapter(this, R.layout.layout_item_search, arrayList_search);
        listView.setAdapter(adaptersearch);

    }





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

//  if(currentFrageMent!=FragMent_Login) {
//                replaceFragMent(new LoginFragment());
//                currentFrageMent=FragMent_Login;
//                setTitle(item.getTitle());
//
//            }
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

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragMent(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();
    }
    private void navigateToContentFrame() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remove the current fragment
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }

        // Commit the transaction
        fragmentTransaction.commit();
    }






}




