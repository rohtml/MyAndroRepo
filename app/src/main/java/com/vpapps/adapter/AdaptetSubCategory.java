package com.vpapps.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.models.CategoryResponse;
import com.models.SubcategoryResponse;
import com.vpapps.fooddelivery.AllProductsActivity;
import com.vpapps.fooddelivery.R;
import com.vpapps.utils.SaveSharedPreference;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class AdaptetSubCategory extends RecyclerView.Adapter<AdaptetSubCategory.ItemVIewHolder> {

    private ArrayList<SubcategoryResponse> eventsData;
    private Context adaptContext;

SaveSharedPreference saveSharedPreference;

    public AdaptetSubCategory(Context adaptContext, ArrayList<SubcategoryResponse> eventsData) {
        this.adaptContext=adaptContext;
        this.eventsData=eventsData;

    }

    @NonNull
    @Override
    public ItemVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(adaptContext);
        View view = inflater.inflate(R.layout.layout_categorys, null);
        saveSharedPreference=new SaveSharedPreference(adaptContext);

        //View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_daily_update_main,viewGroup,false);
        return new ItemVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptetSubCategory.ItemVIewHolder holder, final int position) {
        final SubcategoryResponse mainItem = eventsData.get(position);



        holder.txt_Title.setText(mainItem.getName());
        //Picasso.with(adaptContext).load(mainItem.getImg()).placeholder(R.drawable.app_icon).into(holder.imgDailyUpdate);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveSharedPreference.setRespCount(mainItem.getSid());
                saveSharedPreference.setRespCount(mainItem.getSid());
                Log.e(TAG, "onClick: inside product id" +saveSharedPreference.getRespCount() );

                Intent intent=new Intent(adaptContext, AllProductsActivity.class);
                intent.putExtra("subCategoryToolbar",holder.txt_Title.getText().toString().trim());
                adaptContext.startActivity(intent);


             /*   final Dialog dialog_product_info1 = new Dialog(adaptContext,R.style.AppTheme);
                //dialog_product_info.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                dialog_product_info1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog_product_info1.getWindow().setGravity(Gravity.BOTTOM);
                dialog_product_info1.setContentView(R.layout.layout_menucat);
                dialog_product_info1.setCancelable(true);

                //final SubcategoryResponse subCat=subcategoryResponses.get(position);

                TextView txt_Title=dialog_product_info1.findViewById(R.id.tv_menucat_name);
*//*
                Toolbar toolbar=dialog_product_info1.findViewById(R.id.toolbar_all_click);
                LinearLayout ll_backButonToolbar=dialog_product_info1.findViewById(R.id.ll_backBtn);
                //toolbar.setTitle(R.string.rv_work_done);
                TextView txttitle=dialog_product_info1.findViewById(R.id.txtTitletoolbar);
                //ImageView imgClick=dialog_product_info.findViewById(R.id.image_click);
                txttitle.setText(R.string.events);

                ll_backButonToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_product_info1.dismiss();
                    }
                });*//*


                //txt_Title.setText(subCat.getName());
               // Picasso.with(adaptContext).load(mainItem.getImg()).placeholder(R.drawable.app_icon).into(imgDailyUpdate);
                dialog_product_info1.show();*/

            }

       });

    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class ItemVIewHolder extends RecyclerView.ViewHolder {
       // ImageView imgDailyUpdate;
        CardView cardView;
        TextView txt_Title;
        LinearLayout llMenuCat;

        public ItemVIewHolder(@NonNull View itemView) {
            super(itemView);
          //  cardView=itemView.findViewById(R.id.card_view);

            cardView=itemView.findViewById(R.id.cardview);
            txt_Title=itemView.findViewById(R.id.tv_cat1);

        }
    }
}
