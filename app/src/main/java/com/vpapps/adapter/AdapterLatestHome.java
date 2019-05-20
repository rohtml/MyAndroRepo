package com.vpapps.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vpapps.fooddelivery.MainActivity;
import com.vpapps.fooddelivery.R;
import com.vpapps.interfaces.ClickListener;
import com.vpapps.items.ItemRestaurant;
import com.vpapps.utils.Constant;
import com.vpapps.utils.DBHelper;

import java.util.ArrayList;


public class AdapterLatestHome extends RecyclerView.Adapter<AdapterLatestHome.MyViewHolder> {

    private ArrayList<ItemRestaurant> arrayList;
    private Context context;
    private DBHelper dbHelper;
    private ClickListener clickListener;
    private Animation anim;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title, textView_address, textView_tot_rating;
        ImageView imageView, imageView_type, imageView_fav;
        RatingBar rating;
        LinearLayout ll_home_main;

        MyViewHolder(View view) {
            super(view);
            textView_title = view.findViewById(R.id.tv_latest_home_name);
           // textView_address = view.findViewById(R.id.tv_latest_home_address);
           // textView_tot_rating = view.findViewById(R.id.tv_latest_home_tot_rating);
            imageView = view.findViewById(R.id.iv_latest_home);
            //imageView_type = view.findViewById(R.id.iv_latest_home_type);
           // imageView_fav = view.findViewById(R.id.iv_home_fav);
           // rating = view.findViewById(R.id.rating_home_latest);
            ll_home_main = view.findViewById(R.id.ll_home_main);
        }
    }

    public AdapterLatestHome(Context context, ArrayList<ItemRestaurant> arrayList, ClickListener clickListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickListener = clickListener;
        dbHelper = new DBHelper(context);

        anim = AnimationUtils.loadAnimation(context, R.anim.scale_up_fav);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_latest_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
/*

        if(dbHelper.isFav(arrayList.get(holder.getAdapterPosition()).getId())) {
            holder.imageView_fav.setImageResource(R.mipmap.fav_hover);
        } else {
            holder.imageView_fav.setImageResource(R.mipmap.fav);
        }
*/

       /* if (arrayList.get(position).getType().equals(Constant.TAG_VEG)) {
            holder.imageView_type.setImageResource(R.mipmap.veg);
        } else if (arrayList.get(position).getType().equals(Constant.TAG_NONVEG)) {
            holder.imageView_type.setImageResource(R.mipmap.nonveg);
        }*/

        holder.textView_title.setText(arrayList.get(position).getName());
       /* holder.textView_address.setText(arrayList.get(position).getAddress());
        holder.textView_tot_rating.setText("(" + arrayList.get(position).getTotalRating() + ")");
        holder.rating.setRating(arrayList.get(position).getAvgRatings());*/
        Picasso.get()
                .load(arrayList.get(position).getImage())
                .placeholder(R.drawable.placeholder_hotel)
                .into(holder.imageView);
      /*  holder.imageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageView_fav.startAnimation(anim);
                if(dbHelper.addtoFavourite(arrayList.get(holder.getAdapterPosition()))) {
                    holder.imageView_fav.setImageResource(R.mipmap.fav_hover);
                } else {
                    holder.imageView_fav.setImageResource(R.mipmap.fav);
                }
            }
        });*/

        holder.ll_home_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog_product_info1 = new Dialog(context, R.style.AppTheme);
                //dialog_product_info.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                dialog_product_info1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog_product_info1.getWindow().setGravity(Gravity.BOTTOM);
                dialog_product_info1.setContentView(R.layout.activity_click_product_layout);
                dialog_product_info1.setCancelable(true);
               ImageView ivList=dialog_product_info1.findViewById(R.id.iv_list);
               TextView txtProductDescription=dialog_product_info1.findViewById(R.id.txt_productDescription);
                TextView txtShowOfeerPrice=dialog_product_info1.findViewById(R.id.txt_showOfferPrice);
                TextView txtShowPrice=dialog_product_info1.findViewById(R.id.txt_showPrice);
                TextView txtTitleName=dialog_product_info1.findViewById(R.id.tv_list_name);
                TextView txtDelivertext=dialog_product_info1.findViewById(R.id.txt_checkDeliveryLocation);

                txtDelivertext.setTypeface(null, Typeface.ITALIC);

                txtShowPrice.setPaintFlags(txtShowPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                Picasso.get().load(arrayList.get(position).getImage()).placeholder(R.drawable.placeholder_hotel).into(ivList);
                txtProductDescription.setText(arrayList.get(position).getName());
                txtShowPrice.setText("200");
                txtTitleName.setText(arrayList.get(position).getName());
                Toast.makeText(context,"here it is",Toast.LENGTH_LONG).show();
/*
                Toolbar toolbar=dialog_product_info1.findViewById(R.id.toolbar_all_click);
                LinearLayout ll_backButonToolbar=dialog_product_info1.findViewById(R.id.ll_backBtn);
                //toolbar.setTitle(R.string.rv_work_done);
                TextView txttitle=dialog_product_info1.findViewById(R.id.txtTitletoolbar);
                //ImageView imgClick=dialog_product_info.findViewById(R.id.image_click);
                txttitle.setText("Product Info");

                ll_backButonToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_product_info1.dismiss();
                    }
                });*/
                dialog_product_info1.show();
               // clickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}