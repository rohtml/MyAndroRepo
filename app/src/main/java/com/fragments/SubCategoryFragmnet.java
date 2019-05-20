package com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Interfaces.CategoryInterface;
import com.Interfaces.SubCategoryInterface;
import com.models.CategoryPojo;
import com.models.SubCategoryPojo;
import com.vpapps.adapter.AdaptetCategory;
import com.vpapps.adapter.AdaptetSubCategory;
import com.vpapps.fooddelivery.R;
import com.vpapps.utils.ApiClient;
import com.vpapps.utils.ProgressDialogHelper;
import com.vpapps.utils.SaveSharedPreference;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragmnet extends Fragment {
    RecyclerView recyclerView;
    SaveSharedPreference saveSharedPreference;
    AdaptetSubCategory adapter;




    public SubCategoryFragmnet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sub_category_fragmnet, container, false);
        recyclerView=view.findViewById(R.id.recycler_subCat);
        saveSharedPreference=new SaveSharedPreference(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        Log.e(TAG, "onCreateView: inside subcategory fragment" );
        submitSubCat(saveSharedPreference.getVillageId());
        return view;
    }
    private void submitSubCat(String id) {

        try {
            ProgressDialogHelper.showDialog(getContext());


            SubCategoryInterface apiService = ApiClient.getClient().create(SubCategoryInterface.class);

            Call<SubCategoryPojo> call = apiService.getSubCategory(id);
            call.enqueue(new Callback<SubCategoryPojo>() {
                @Override
                public void onResponse(Call<SubCategoryPojo> call, retrofit2.Response<SubCategoryPojo> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: " + response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {

                                adapter = new AdaptetSubCategory(getContext(),response.body().getData());
                                recyclerView.setAdapter(adapter);
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
                public void onFailure(Call<SubCategoryPojo> call, Throwable t) {
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
