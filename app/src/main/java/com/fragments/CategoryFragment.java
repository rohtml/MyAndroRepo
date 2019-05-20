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
import com.models.CategoryPojo;
import com.vpapps.adapter.AdapterMenuExpandableCategory;
import com.vpapps.adapter.AdaptetCategory;
import com.vpapps.fooddelivery.R;
import com.vpapps.utils.ApiClient;
import com.vpapps.utils.ProgressDialogHelper;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
RecyclerView recyclerView;
AdaptetCategory adapter;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView=view.findViewById(R.id.recycler_caegoryFragment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        Log.e(TAG, "onCreateView: inside category fragment" );
        submitCategory();
        return view;
    }
    private void submitCategory() {

        try {
            ProgressDialogHelper.showDialog(getContext());


            CategoryInterface apiService = ApiClient.getClient().create(CategoryInterface.class);

            Call<CategoryPojo> call = apiService.getCategory();
            call.enqueue(new Callback<CategoryPojo>() {
                @Override
                public void onResponse(Call<CategoryPojo> call, retrofit2.Response<CategoryPojo> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: " + response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {

                                adapter = new AdaptetCategory(getContext(),response.body().getData());
                                recyclerView.setAdapter(adapter);
                                //strNonAppUserImage="";
                                Log.d("AT RESPONSE", "onResponse: " + response);

                               /* strInvitePhoto = "";
                                displayView(0);*/
                               // Toast.makeText(getContext(), "Events..", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<CategoryPojo> call, Throwable t) {
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
