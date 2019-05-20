package com.vpapps.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Interfaces.AllProductInterface;
import com.Interfaces.PinCodeInterface;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.models.AllProductsPojo;
import com.models.AllProductsResponse;
import com.models.CartProducts;
import com.models.PinCodePojo;
import com.models.PinCodePojoRenew;
import com.models.SubcategoryResponse;
import com.squareup.picasso.Picasso;
import com.vpapps.fooddelivery.CartActivity;
import com.vpapps.fooddelivery.CartActivity1;
import com.vpapps.fooddelivery.R;
import com.vpapps.utils.ApiClient;
import com.vpapps.utils.ProgressDialogHelper;
import com.vpapps.utils.SaveSharedPreference;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;

public class AdapterAllProducts extends RecyclerView.Adapter<AdapterAllProducts.ItemVIewHolder> {

    private ArrayList<AllProductsResponse> eventsData;
    private ArrayList<PinCodePojoRenew> pinCodeData;
    SharedPreferences product_sharedpreferences;
    public static final String MyPRODUCTS = "MyProducts" ;
    private Context adaptContext;
    public String charges;
    static public int productTotal;
    private static int count=0;

SaveSharedPreference saveSharedPreference;

    public AdapterAllProducts(Context adaptContext, ArrayList<AllProductsResponse> eventsData) {
        this.adaptContext=adaptContext;
        this.eventsData=eventsData;

    }

    @NonNull
    @Override
    public ItemVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(adaptContext);
        View view = inflater.inflate(R.layout.adapter_all_products, null);
        saveSharedPreference=new SaveSharedPreference(adaptContext);

        //View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_daily_update_main,viewGroup,false);
        return new ItemVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterAllProducts.ItemVIewHolder holder, final int position) {
        final AllProductsResponse mainItem = eventsData.get(position);
//        final PinCodePojoRenew pinItem=pinCodeData.get(position);


        holder.txt_Title.setText(mainItem.getName());
        Picasso.get().load(mainItem.getLink()).placeholder(R.drawable.placeholder_hotel).into(holder.imgDailyUpdate);
       // Picasso.with(adaptContext).load(mainItem.getImg()).placeholder(R.drawable.app_icon).into(holder.imgDailyUpdate);

        holder.llMenuCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveSharedPreference.setVillageId(mainItem.getId());



                final Dialog dialog_product_info1 = new Dialog(adaptContext,R.style.AppTheme);
                //dialog_product_info.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                dialog_product_info1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog_product_info1.getWindow().setGravity(Gravity.BOTTOM);
                dialog_product_info1.setContentView(R.layout.activity_click_product_layout);
                dialog_product_info1.setCancelable(true);


                //final SubcategoryResponse subCat=subcategoryResponses.get(position);

                TextView txt_Title=dialog_product_info1.findViewById(R.id.tv_list_name);
                TextView txtShowPrice=dialog_product_info1.findViewById(R.id.txt_showPrice);
                TextView txtOfferPrice=dialog_product_info1.findViewById(R.id.txt_showOfferPrice);
                TextView txtDescription=dialog_product_info1.findViewById(R.id.txt_productDescription);
                final Button btnAddToCart=dialog_product_info1.findViewById(R.id.button_quantity_add2cart);
                RoundedImageView roundedImageView=dialog_product_info1.findViewById(R.id.iv_list);
                final EditText edtEnterPinCode=dialog_product_info1.findViewById(R.id.edt_enterPinCode);
                Button btnVhckPin=dialog_product_info1.findViewById(R.id.button_checkPin);

                btnVhckPin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        submitPinCode(edtEnterPinCode.getText().toString().trim());
/*
                        if(saveSharedPreference.getPinResponse().equals("") ){
                            Log.e(TAG, "onClick: inside check button if"+saveSharedPreference.getPinResponse());
                            btnAddToCart.setVisibility(View.GONE);


                        }else{
                            btnAddToCart.setVisibility(View.VISIBLE);
                            Log.e(TAG, "onClick: inside check button else"+saveSharedPreference.getPinResponse());

                        }*/

                    }
                });


                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Dialog dialog_product_info = new Dialog(adaptContext,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        //dialog_product_info.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                        dialog_product_info.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        // dialog_product_info1.getWindow().setGravity(Gravity.BOTTOM);
                        dialog_product_info.setContentView(R.layout.layout_quantity);
                        dialog_product_info.setCancelable(true);

                        TextView tvQuantityMinus=dialog_product_info.findViewById(R.id.tv_quantity_minus);
                        TextView tvQuantityPlus=dialog_product_info.findViewById(R.id.tv_quantity_plus);
                        final TextView tcQuantityCount=dialog_product_info.findViewById(R.id.tv_quantity_count);
                        Button btnQuantityAddToCart=dialog_product_info.findViewById(R.id.button_quantity_add2cart);

                        dialog_product_info.show();
                        tvQuantityMinus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count--;
                                tcQuantityCount.setText(String.valueOf(count));
                            }
                        });
                        tvQuantityPlus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count++;
                                tcQuantityCount.setText(String.valueOf(count));
                            }
                        });
                        btnQuantityAddToCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Log.d("Add","to cart");
                                product_sharedpreferences = adaptContext.getSharedPreferences(MyPRODUCTS, Context.MODE_PRIVATE);
                                Gson gson1 = new Gson();
                                // product_sharedpreferences.edit().clear().commit();
                                String json1 = product_sharedpreferences.getString("MySharedProduct", "");
                                productTotal = product_sharedpreferences.getInt("totalCartPrice",0);
                                CartProducts obj = gson1.fromJson(json1, CartProducts.class);
                                if(obj==null){
                                    final CartProducts cartProducts = new CartProducts();
                                    //int quantity = Integer.parseInt(holder.textQuantity.getText().toString());
                                    int quantity=Integer.parseInt(tcQuantityCount.getText().toString());
                                    int price = Integer.parseInt((eventsData.get(position).getPCurrentPrice()));
                                    eventsData.get(position).setPQty(String.valueOf(quantity));
                                    //    Log.d("QUNTITY","QU "+quantity);
                                    // productList.get(position).setQuantity(String.valueOf(quantity));
                                    cartProducts.setProducts(eventsData.get(position));

                                    int Total =  price*quantity;
                                    Log.d("Quantity",""+Total);
                                    productTotal=0;
                                    productTotal = Total ;

                                    Log.d("ProductsList", "onBindViewHolder: " + cartProducts);
                                    Log.d("ProductSize", "" + cartProducts.getCartsize());
                                    SharedPreferences.Editor prefsEditor = product_sharedpreferences.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(cartProducts);
                                    prefsEditor.putString("MySharedProduct", json);
                                    prefsEditor.putInt("totalCartPrice", productTotal);
                                    prefsEditor.putInt("quantity",quantity);
                                    prefsEditor.commit();
                                    Toast.makeText(adaptContext, "Item Added Successfully, check your cart !", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(adaptContext, CartActivity1.class);
                                    adaptContext.startActivity(i);

                                }else{
                                    Log.d("Product in list",""+obj.CheckProductInCart(eventsData.get(position)));
                                    if(obj.CheckProductInCart(eventsData.get(position))){
                                        Toast.makeText(adaptContext, "Products Already Added", Toast.LENGTH_LONG).show();
                                    }else {
                                        Log.d("ProductSizeShared", "" + obj.getCartsize());
//                        int quantity = Integer.parseInt(holder.textQuantity.getText().toString());

                                        // int quantity = Integer.parseInt(productList.get(position).getQuantity());
                                        int quantity = Integer.parseInt(tcQuantityCount.getText().toString());
                                        int price = Integer.parseInt((eventsData.get(position).getPCurrentPrice()));
                                        // Log.d("QUNTITY","QU "+quantity);
                                        // productList.get(position).setQuantity(String.valueOf(quantity));
                                        // String prodName=txt_product_title.getText().toString();
                                        eventsData.get(position).setPQty(String.valueOf(quantity));
                                        int Total = price * quantity;
                                        Log.d("Quantity", "" + Total);
                                        productTotal = productTotal + Total;
                                        obj.setProducts(eventsData.get(position));
                                        Log.d("ProductsListShared", "onBindViewHolder: " + obj);
                                        Log.d("ProductSizeShared", "" + obj.getCartsize());
                                        SharedPreferences.Editor prefsEditor = product_sharedpreferences.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(obj);
                                        prefsEditor.putString("MySharedProduct", json);
                                        //  prefsEditor.putString("productName", prodName);
                                        prefsEditor.putInt("totalCartPrice", productTotal);
                                        prefsEditor.putInt("quantity", quantity);
                                        prefsEditor.commit();
                                        Toast.makeText(adaptContext, "Item Added Successfully, check your cart !", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(adaptContext, CartActivity1.class);
                                        adaptContext.startActivity(i);


                                    }}

                            }
                        });



                    }
                });



                txt_Title.setText(mainItem.getName());
                txtShowPrice.setText(mainItem.getPOldPrice());
                txtOfferPrice.setText(mainItem.getPCurrentPrice());
                txtDescription.setText(mainItem.getPDescription());
                Picasso.get().load(mainItem.getLink()).placeholder(R.drawable.placeholder_hotel).into(roundedImageView);

                txtShowPrice.setPaintFlags(txtShowPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                Toolbar toolbar=dialog_product_info1.findViewById(R.id.toolbar_all_click);
                LinearLayout ll_backButonToolbar=dialog_product_info1.findViewById(R.id.ll_backBtn);
                //toolbar.setTitle(R.string.rv_work_done);
                TextView txttitle=dialog_product_info1.findViewById(R.id.txtTitletoolbar);
                //ImageView imgClick=dialog_product_info.findViewById(R.id.image_click);
                txttitle.setText(txt_Title.getText().toString().trim());

                ll_backButonToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_product_info1.dismiss();
                    }
                });


                //txt_Title.setText(subCat.getName());
               // Picasso.with(adaptContext).load(mainItem.getImg()).placeholder(R.drawable.app_icon).into(imgDailyUpdate);
                dialog_product_info1.show();

            }

       });

    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class ItemVIewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgDailyUpdate;

        CardView cardView;
        TextView txt_Title;
        LinearLayout llMenuCat;

        public ItemVIewHolder(@NonNull View itemView) {
            super(itemView);
          //  cardView=itemView.findViewById(R.id.card_view);

            cardView=itemView.findViewById(R.id.cardview);
            txt_Title=itemView.findViewById(R.id.tv_latest_home_name);
            llMenuCat=itemView.findViewById(R.id.ll_home_main);
            imgDailyUpdate=itemView.findViewById(R.id.iv_latest_home);

        }
    }
    private void submitPinCode(String id) {

        try {
            ProgressDialogHelper.showDialog(adaptContext);


            PinCodeInterface apiService = ApiClient.getClient().create(PinCodeInterface.class);

            Call<PinCodePojoRenew> call = apiService.getPinCode(id);
            call.enqueue(new Callback<PinCodePojoRenew>() {
                @Override
                public void onResponse(Call<PinCodePojoRenew> call, retrofit2.Response<PinCodePojoRenew> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: " + response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {

                            saveSharedPreference.setPinResponse(response.body().getData());


                               // charges=response.body().getData().toString().trim();
                                Log.e(TAG, "onResponse: inside pincode adapter all product"+saveSharedPreference.getPinResponse() );

                              /*  adapter = new AdapterAllProducts(getContext(),response.body().getData());
                                recyclerView.setAdapter(adapter);*/
                                //strNonAppUserImage="";
                                Log.d("AT RESPONSE", "onResponse: " + response);

                               /* strInvitePhoto = "";
                                displayView(0);*/
                                Toast.makeText(adaptContext, "SubCategory..", Toast.LENGTH_SHORT).show();
                            } else {

                                saveSharedPreference.setPinResponse(response.body().getData());
                                Log.e(TAG, "onResponse: inside pincode adapter all product"+saveSharedPreference.getPinResponse() );



                                //strInvitePhoto = "";
                                Toast.makeText(adaptContext, "Connection Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("inside suggestion", e.toString());
                            //strInvitePhoto = "";
                        }

                    } else {
                        Toast.makeText(adaptContext, response.message(), Toast.LENGTH_SHORT).show();
                        ProgressDialogHelper.hideDialog();
                    }
                }


                @Override
                public void onFailure(Call<PinCodePojoRenew> call, Throwable t) {
                    Toast.makeText(adaptContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
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

