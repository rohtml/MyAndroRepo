package com.vpapps.fooddelivery;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.fragments.AllProductsFragment;
import com.fragments.SubCategoryFragmnet;

public class AllProductsActivity extends AppCompatActivity {


    Toolbar toolbar;
    FrameLayout frameLayout;
    FragmentManager manager;
    android.support.v4.app.FragmentManager fm;
    String toolName123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        toolbar=findViewById(R.id.toolbar_allProducts123);

        Intent intent=getIntent();
        toolName123=intent.getStringExtra("subCategoryToolbar");
        Log.e("allproductsactivity", "onCreate: "+toolName123 );
        toolbar.setTitle(toolName123);
        setSupportActionBar(toolbar);


        frameLayout=findViewById(R.id.frame_allProducts);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        fm = getSupportFragmentManager();

        AllProductsFragment allProductsFragment=new AllProductsFragment();
        fm.beginTransaction().replace(R.id.frame_allProducts,allProductsFragment).commit();

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

