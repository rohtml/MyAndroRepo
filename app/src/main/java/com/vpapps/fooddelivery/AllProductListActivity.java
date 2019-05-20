package com.vpapps.fooddelivery;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.fragments.AllProductListFragment;
import com.vpapps.adapter.AdapterHotelList;

public class AllProductListActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frameLayout;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product_list);
        toolbar=findViewById(R.id.toolbarall);
        frameLayout=findViewById(R.id.frame_allproductllist);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        manager=getSupportFragmentManager();
        AllProductListFragment dailyUpdatesMainFragment=new AllProductListFragment();
        manager.beginTransaction().replace(R.id.frame_allproductllist,dailyUpdatesMainFragment).commit();



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
