package com.vpapps.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.models.AllProductsResponse;
import com.squareup.picasso.Picasso;
import com.vpapps.fooddelivery.R;
import com.vpapps.interfaces.ClickListener;
import com.vpapps.items.ItemRestaurant;
import com.vpapps.utils.Constant;
import com.vpapps.utils.DBHelper;

import java.util.ArrayList;


public class AdapterHotelList extends RecyclerView.Adapter<AdapterHotelList.MyViewHolder> {

    private ArrayList<AllProductsResponse> arrayList;
    private ArrayList<AllProductsResponse> filteredArrayList;
    private NameFilter filter;
    private Context context;
    private DBHelper dbHelper;
    private ClickListener clickListener;
    private Animation anim;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title, textView_address, textView_tot_rating;
        RoundedImageView imageView;
        ImageView imageView_hotel_type, imageView_fav;
        RatingBar rating;
        LinearLayout ll_home_main;

        MyViewHolder(View view) {
            super(view);
            textView_title = view.findViewById(R.id.tv_list_name);
            textView_address = view.findViewById(R.id.tv_list_address);
            textView_tot_rating = view.findViewById(R.id.tv_latest_list_tot_rating);
            imageView = view.findViewById(R.id.iv_list);
            imageView_hotel_type = view.findViewById(R.id.iv_hotel_type);
            rating = view.findViewById(R.id.rating_list_latest);
            ll_home_main = view.findViewById(R.id.ll_main);
            imageView_fav = view.findViewById(R.id.iv_fav);
        }
    }

    public AdapterHotelList(Context context, ArrayList<AllProductsResponse> arrayList) {
        this.arrayList = arrayList;
        this.filteredArrayList = arrayList;
        this.context = context;
       // this.clickListener = clickListener;
        dbHelper = new DBHelper(context);
        anim = AnimationUtils.loadAnimation(context, R.anim.scale_up_fav);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_hotel, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

      /*  if(dbHelper.isFav(arrayList.get(holder.getAdapterPosition()).getId())) {
            holder.imageView_fav.setImageResource(R.mipmap.fav_hover);
        } else {
            holder.imageView_fav.setImageResource(R.mipmap.fav);
        }

        if(arrayList.get(position).getType().equals(Constant.TAG_VEG)) {
            holder.imageView_hotel_type.setImageResource(R.mipmap.veg);
        } else if(arrayList.get(position).getType().equals(Constant.TAG_NONVEG)) {
            holder.imageView_hotel_type.setImageResource(R.mipmap.nonveg);
        }*/

        holder.textView_title.setText(arrayList.get(position).getName());
       // holder.textView_address.setText(arrayList.get(position).getAddress());
       // holder.textView_tot_rating.setText("("+arrayList.get(position).getTotalRating()+")");
       // holder.rating.setRating(arrayList.get(position).getAvgRatings());
        Picasso.get()
                .load(arrayList.get(position).getLink())
                .placeholder(R.drawable.placeholder_hotel)
                .into(holder.imageView);

        holder.imageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageView_fav.startAnimation(anim);
              /*  if(dbHelper.addtoFavourite(arrayList.get(holder.getAdapterPosition()))) {
                    holder.imageView_fav.setImageResource(R.mipmap.fav_hover);
                } else {
                    holder.imageView_fav.setImageResource(R.mipmap.fav);
                }*/
            }
        });

        holder.ll_home_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog_product_info1 = new Dialog(context,R.style.AppTheme);
                //dialog_product_info.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                dialog_product_info1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog_product_info1.getWindow().setGravity(Gravity.BOTTOM);
                dialog_product_info1.setContentView(R.layout.activity_click_product_layout);
                dialog_product_info1.setCancelable(true);
                TextView txt_Title=dialog_product_info1.findViewById(R.id.tv_list_name);
                TextView txtShowPrice=dialog_product_info1.findViewById(R.id.txt_showPrice);
                TextView txtOfferPrice=dialog_product_info1.findViewById(R.id.txt_showOfferPrice);
                TextView txtDescription=dialog_product_info1.findViewById(R.id.txt_productDescription);
                final Button btnAddToCart=dialog_product_info1.findViewById(R.id.button_quantity_add2cart);
                RoundedImageView roundedImageView=dialog_product_info1.findViewById(R.id.iv_list);
                final EditText edtEnterPinCode=dialog_product_info1.findViewById(R.id.edt_enterPinCode);
                Button btnVhckPin=dialog_product_info1.findViewById(R.id.button_checkPin);

                //clickListener.onClick(holder.getAdapterPosition());
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

    public String getID(int pos) {
        return arrayList.get(pos).getPid();
    }

    public Filter getFilter() {
        if (filter == null){
            filter  = new NameFilter();
        }
        return filter;
    }

    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<AllProductsResponse> filteredItems = new ArrayList<>();

                for (int i = 0, l = filteredArrayList.size(); i < l; i++) {
                    String nameList = filteredArrayList.get(i).getName();
                    if (nameList.toLowerCase().contains(constraint))
                        filteredItems.add(filteredArrayList.get(i));
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = filteredArrayList;
                    result.count = filteredArrayList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            arrayList = (ArrayList<AllProductsResponse>) results.values;
            notifyDataSetChanged();
        }
    }
}