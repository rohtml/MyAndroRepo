package com.vpapps.fooddelivery;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.fragments.ProductInformationFragment;

public class ProductInformationActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        toolbar=findViewById(R.id.toolbar_productInfo);
        frameLayout=findViewById(R.id.frame_productInfo);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("Product");


        ProductInformationFragment productInformationFragment=new ProductInformationFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_productInfo,productInformationFragment).commit();


    }
}
