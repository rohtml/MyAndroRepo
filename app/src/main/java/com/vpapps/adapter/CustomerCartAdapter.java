package com.vpapps.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.models.AllProductsResponse;
import com.models.CartProducts;
import com.squareup.picasso.Picasso;

import com.vpapps.fooddelivery.CartActivity1;
import com.vpapps.fooddelivery.R;
import com.vpapps.utils.SaveSharedPreference;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

/*import com.ecomputerkingdom.sabjimarket.Activity.CustomerTabActivity;
import com.ecomputerkingdom.sabjimarket.Data.Model.Request.ProductActiveListRequest.CartProducts;
import com.ecomputerkingdom.sabjimarket.Data.Model.Request.ProductActiveListRequest.Prodcut;
import com.ecomputerkingdom.sabjimarket.R;*/

public class CustomerCartAdapter extends RecyclerView.Adapter<CustomerCartAdapter.ProductViewHolder> {

    private Context context;
    private List<AllProductsResponse> cartProducts;
    View view;

    SaveSharedPreference saveSharedPreference;
    static public int cart_price;

   public String temp1="",temp2="",temp3="",temp4="";
    SharedPreferences product_sharedpreferences;
    public static final String MyPRODUCTS = "MyProducts" ;

    public CustomerCartAdapter(Context context, List<AllProductsResponse> cartProducts) {
        this.context = context;
        this.cartProducts = cartProducts;
    }

    @Override
    public CustomerCartAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_item_cart,parent, false);

        saveSharedPreference=new SaveSharedPreference(context);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerCartAdapter.ProductViewHolder holder, final int position) {
        Picasso.get()
                .load(R.drawable.icon_delete_26dp)
                .into(holder.deleteProduct);
        Picasso.get().load(cartProducts.get(position).getLink()).into(holder.productImg);
        holder.productName.setText(cartProducts.get(position).getName());


        temp1=temp1+","+cartProducts.get(position).getName();
        temp2=temp2+","+cartProducts.get(position).getPid();
        temp3=temp3+","+cartProducts.get(position).getPCurrentPrice();
        temp4=temp4+","+cartProducts.get(position).getPQty();


        saveSharedPreference.setPname(temp1);
        saveSharedPreference.setPId(temp2);
        saveSharedPreference.setPrate(temp3);
        saveSharedPreference.setPqty(temp4);
        Log.e(TAG, "onBindViewHolder: pname kasjfaksfjskfj;"+saveSharedPreference.getPname() );




        //  temp=temp+cartProducts.get(position).getPid()+"-"+cartProducts.get(position).getName()+"_"+cartProducts.get(position).getPQty();
        Log.e(TAG, "onBindViewHolder: "+temp1+temp2+temp3+temp4);
        Log.d("Quantity",""+cartProducts.get(position).getPQty());
        holder.productQuantity.setText(""+cartProducts.get(position).getPQty());
//        int quantity = Integer.parseInt(cartProducts.get(position).getQuantity());
        final int price = Integer.parseInt((cartProducts.get(position).getPCurrentPrice()));
        final int total_price =  price*Integer.parseInt((cartProducts.get(position).getPQty()));

        Log.d("TOTAL_price",""+total_price);
        holder.productPrice.setText(""+total_price);

        product_sharedpreferences = context.getSharedPreferences(MyPRODUCTS, Context.MODE_PRIVATE);
        final Gson gson1 = new Gson();
        final String json1 = product_sharedpreferences.getString("MySharedProduct", "");
        int cPrice = product_sharedpreferences.getInt("totalCartPrice", 0);
        cPrice = cPrice - total_price;
        Log.d("CART Price",""+cart_price);

       // cartPrice.setText(""+cart_price);

        final int finalCPrice = cPrice;
        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();*/
                                //////
                                ////
                                ///
                                int priceM = Integer.parseInt(cartProducts.get(position).getPCurrentPrice());
                                cartProducts.remove(cartProducts.get(position));
                                notifyDataSetChanged();

                              /*  int p = finalCPrice;

                                p= p-priceM;
*/




                                CartProducts cartProducts1 = gson1.fromJson(json1, CartProducts.class);

                                cartProducts1.removeProduct(cartProducts1.getProducts(position));

                                SharedPreferences.Editor prefsEditor = product_sharedpreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(cartProducts1);
                                prefsEditor.putString("MySharedProduct", json);
                                prefsEditor.putInt("totalCartPrice", finalCPrice);
                                prefsEditor.commit();
                                notifyDataSetChanged();
                                ////
                                //////
                                Intent i = new Intent(context, CartActivity1.class);
                                context.startActivity(i);

                      /*      }
                        })
                        .show();*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity;
        ImageView productImg, deleteProduct;


        public ProductViewHolder(View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.txt_mycartName);
            this.productPrice = itemView.findViewById(R.id.txt_mycartPrice);
            this.productQuantity = itemView.findViewById(R.id.txt_mycartQuality);
            this.productImg = itemView.findViewById(R.id.img_cart);
            this.deleteProduct = itemView.findViewById(R.id.img_myCartDelete);
        }
    }
}
