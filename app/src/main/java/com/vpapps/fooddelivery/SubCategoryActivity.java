package com.vpapps.fooddelivery;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.fragments.SubCategoryFragmnet;

public class SubCategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frameLayout;
    FragmentManager manager;
    android.support.v4.app.FragmentManager fm;
    String toolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        toolbar = findViewById(R.id.toolbar_subCat);

        Intent intent=getIntent();
       toolName= intent.getStringExtra("categoryToolbar");
        Log.e("asdfadf", "onCreate: toolbar"+toolName );
        toolbar.setTitle(toolName);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        fm = getSupportFragmentManager();

        SubCategoryFragmnet subCategoryFragmnet=new SubCategoryFragmnet();
        fm.beginTransaction().replace(R.id.frame_subCat,subCategoryFragmnet).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
}
