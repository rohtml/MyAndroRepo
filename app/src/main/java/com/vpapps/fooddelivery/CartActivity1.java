package com.vpapps.fooddelivery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Interfaces.AllProductInterface;
import com.Interfaces.LoginInterface;
import com.Interfaces.SuccessInterface;
import com.google.gson.Gson;
import com.models.AllProductsResponse;
import com.models.CartProducts;

import com.models.LoginPojo;
import com.models.SuccessPojo;
import com.vpapps.adapter.CustomerCartAdapter;
import com.vpapps.utils.ApiClient;
import com.vpapps.utils.ProgressDialogHelper;
import com.vpapps.utils.SaveSharedPreference;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;

import static com.vpapps.adapter.AdapterAllProducts.MyPRODUCTS;

public class CartActivity1 extends AppCompatActivity {

    RecyclerView recyclerView;
    SharedPreferences product_sharedpreferences;
    CustomerCartAdapter adapter;
    public static TextView cartPrice;
    Button btnCancel;
    String prodName="Test";
    String pname,pid,pqty,pprice;
SaveSharedPreference saveSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_cart);


    saveSharedPreference=new SaveSharedPreference(getApplicationContext());

        Button btn_checkout=findViewById(R.id.btn_checkout);
        btnCancel=findViewById(R.id.btn_cancel);

        recyclerView = findViewById(R.id.cart_recyclerview);
        cartPrice = findViewById(R.id.txt_total_amount);

        product_sharedpreferences = getSharedPreferences(MyPRODUCTS, Context.MODE_PRIVATE);
        Gson gson1 = new Gson();
        String json1 = product_sharedpreferences.getString("MySharedProduct", "");
      //  final String prodName = product_sharedpreferences.getString("productName", "");

        final int cPrice = product_sharedpreferences.getInt("totalCartPrice", 0);
        Log.e("hEreits", "CPRICE SHAREDGETING"+cPrice+""+cartPrice.getText().toString() );

        CartProducts cartProducts = gson1.fromJson(json1, CartProducts.class);

        Log.d("CART PRice",""+ CustomerCartAdapter.cart_price);
        cartPrice.setText("");
        cartPrice.setText(""+cPrice);
        List<AllProductsResponse> products = new ArrayList<>();

        if(cartProducts == null){

        }else {
            for (int i = 0; i < cartProducts.getCartsize(); i++) {
                products.add(cartProducts.getProducts(i));
            }
            adapter = new CustomerCartAdapter(getApplicationContext(), products);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setItemAnimator(null);
        }

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitSuccess(saveSharedPreference.getLoggerId(),saveSharedPreference.getPId(),saveSharedPreference.getPname(),
                        saveSharedPreference.getPqty(),saveSharedPreference.getPrate(), String.valueOf(cPrice),saveSharedPreference.getLoggerName(),saveSharedPreference.getMobile());
                Intent i=new Intent(CartActivity1.this, PaymentActivity.class);
                i.putExtra("puraskarta",prodName);
                i.putExtra("totalAmount",cPrice);
                Log.e("here", "onClick: FINAL CARTPRICE INTENT"+cPrice );
                startActivity(i);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(CartActivity1.this,MainActivity.class);
               startActivity(i);
            }
        });
    }


    private void submitSuccess(String struid,String strpid,String strpname,String strpqty,String strprate,String strtotal,String strcname,String strcemail) {

        try {
            ProgressDialogHelper.showDialog(CartActivity1.this);


            SuccessInterface apiService = ApiClient.getClient().create(SuccessInterface.class);

            Call<SuccessPojo> call = apiService.getSSuccess(struid,strpid,strpname,strpqty,strprate,strtotal,strcname,strcemail );
            call.enqueue(new Callback<SuccessPojo>() {
                @Override
                public void onResponse(Call<SuccessPojo> call, retrofit2.Response<SuccessPojo> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: " + response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {
                                //strNonAppUserImage="";
                                Log.e("AT RESPONSE", "onResponse: " + response.body().getData());



                                //saveSharedPreference.setLoggerId(response.body().getUid());

                                // loginDatum = new Gson().fromJson(String.valueOf(response), LoginDatum.class);


                               // Log.e("inside logins bro", "onResponse: " +saveSharedPreference.getLoggerId());



                               /* strInvitePhoto = "";
                                displayView(0);*/
                                Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
                            } else {
                                //strInvitePhoto = "";
                                Toast.makeText(getApplicationContext(),"outofloop", Toast.LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(), response.body().getCode(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error", e.toString());
                            //strInvitePhoto = "";
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        ProgressDialogHelper.hideDialog();
                    }
                }


                @Override
                public void onFailure(Call<SuccessPojo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
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
