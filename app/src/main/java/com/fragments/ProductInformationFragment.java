package com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vpapps.fooddelivery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductInformationFragment extends Fragment {

    ImageView ivList;
    TextView txtShowPrice,txtShowOfeerPrice,txtProductDescription,txtTitleName;
    EditText edtEnterPinCode;

    public ProductInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_click_product_layout, container, false);

        ivList=view.findViewById(R.id.iv_list);
        txtProductDescription=view.findViewById(R.id.txt_productDescription);
        txtShowOfeerPrice=view.findViewById(R.id.txt_showOfferPrice);
        txtShowPrice=view.findViewById(R.id.txt_showPrice);
        txtTitleName=view.findViewById(R.id.tv_list_name);
        edtEnterPinCode=view.findViewById(R.id.edt_enterPinCode);

        return view;
    }

}
