package com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Interfaces.AllProductInterface;
import com.models.AllProductsPojo;
import com.vpapps.adapter.AdapterAllProducts;
import com.vpapps.adapter.AdapterHotelList;
import com.vpapps.fooddelivery.R;
import com.vpapps.utils.ApiClient;
import com.vpapps.utils.ProgressDialogHelper;
import com.vpapps.utils.SaveSharedPreference;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductListFragment extends Fragment {
    RecyclerView recyclerView;
SaveSharedPreference saveSharedPreference;
AdapterAllProducts adapterAllProducts;

    public AllProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_product_list, container, false);

        saveSharedPreference=new SaveSharedPreference(getContext());
        recyclerView=view.findViewById(R.id.rv_allProductList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        submitAllProduct(saveSharedPreference.getRespCount());
        return view;
    }
    private void submitAllProduct(String id) {

        try {
            ProgressDialogHelper.showDialog(getContext());


            AllProductInterface apiService = ApiClient.getClient().create(AllProductInterface.class);

            Call<AllProductsPojo> call = apiService.getAllProducts(id);
            call.enqueue(new Callback<AllProductsPojo>() {
                @Override
                public void onResponse(Call<AllProductsPojo> call, retrofit2.Response<AllProductsPojo> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: " + response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {

                                adapterAllProducts = new AdapterAllProducts(getContext(),response.body().getData());
                                recyclerView.setAdapter(adapterAllProducts);
                                //strNonAppUserImage="";
                                Log.d("AT RESPONSE", "onResponse: " + response);

                               /* strInvitePhoto = "";
                                displayView(0);*/
                                Toast.makeText(getContext(), "SubCategory..", Toast.LENGTH_SHORT).show();
                            } else {

                                //strInvitePhoto = "";
                                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("inside suggestion", e.toString());
                            //strInvitePhoto = "";
                        }

                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        ProgressDialogHelper.hideDialog();
                    }
                }


                @Override
                public void onFailure(Call<AllProductsPojo> call, Throwable t) {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    ProgressDialogHelper.hideDialog();
                    Log.e("suggestion failure", t.toString());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            ProgressDialogHelper.hideDialog();
        }
    }
}
