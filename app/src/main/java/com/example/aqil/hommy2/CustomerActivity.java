package com.example.aqil.hommy2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.aqil.hommy2.Entity.TrashEntitiy;
import com.example.aqil.hommy2.Entity.UserAccount;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rv;
    rvAdapter adapter;
    UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_home2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SearchView searchView = findViewById(R.id.search);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ArrayList<TrashEntitiy> homeEntity = new ArrayList<>();
        homeEntity.add(new TrashEntitiy("Pondok Margonda","http://thewowstyle.com/wp-content/uploads/2015/01/house-architecture-photography-hd-wallpaper-1920x1200-9237.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuwCMKVr6J0VMWs18cg_COifjMfL8qrLcTVOn72U_799ICtj1c", "Prabowo", 23,"r_ysdHivHYQ"));
        homeEntity.add(new TrashEntitiy("Ciputra Palace","https://brandbox3.weberhaus.de/rest.api?request=image&path=share%2Fpublic%2FStorage%2FBilder%2F2%2F24755-M.tif&resize=%7B%22width%22%3A%222000%22%2C%22height%22%3A%221200%22%2C%22cropx%22%3A%220%22%2C%22cropy%22%3A%2284%22%2C%22cropw%22%3A%222480%22%2C%22croph%22%3A%221488%22%7D&function=getImageCropBySize", "data:image/jpeg;base64,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", "Ayunda", 23,"U7HayaMxOCg"));
        homeEntity.add(new TrashEntitiy("Pantai Kapuk","https://cdn7.bigcommerce.com/s-sryqni/product_images/uploaded_images/home.jpg", "data:image/jpeg;base64,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", "Widodo", 23,"gPs1ifA1mHo"));


        rv = findViewById(R.id.home_rv_arch);
        adapter = new rvAdapter(this);
        adapter.setListMvContent(homeEntity);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                              @Override
                                              public boolean onQueryTextSubmit(String s) {
                                                  String input = s.toLowerCase();
                                                  ArrayList<TrashEntitiy> searchList = new ArrayList<>();
                                                  Log.d("TAG", "onQueryTextSubmit: " + input);
                                                  for (int i = 0; i < homeEntity.size(); i++) {
                                                      //    Log.d("TAG", "onQueryTextSubmit: "+homeEntity.get(i).categoryno;
                                                      if (input.equals(homeEntity.get(i).namePath.toLowerCase()) || input.equals(String.valueOf(homeEntity.get(i).getCategoryno()))) {
                                                          searchList.add(homeEntity.get(i));

                                                      }
                                                  }
                                                  adapter.setListMvContent(searchList);
                                                  adapter.notifyDataSetChanged();
                                                  return true;
                                              }

                                              @Override
                                              public boolean onQueryTextChange(String s) {
                                                  return false;
                                              }
                                          }

        );


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.setListMvContent(homeEntity);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        userAccount = getIntent().getParcelableExtra("USER ACCOUNT");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithqsEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("ProfileData", userAccount);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Log.d("TAG", "onNavigationItemSelected: " + id);

        return true;
    }
}
