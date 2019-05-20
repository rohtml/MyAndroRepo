package com.vpapps.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.models.CategoryResponse;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.vpapps.asyncTask.LoadAddMenu;
import com.vpapps.asyncTask.LoadCart;
import com.vpapps.fooddelivery.HotelDetailsActivity;
import com.vpapps.fooddelivery.R;
import com.vpapps.interfaces.CartListener;
import com.vpapps.interfaces.LoginListener;
import com.vpapps.items.ItemCart;
import com.vpapps.items.ItemMenu;
import com.vpapps.utils.Constant;
import com.vpapps.utils.JsonUtils;
import com.vpapps.utils.Methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterMenuExpandableCategory extends ExpandableRecyclerViewAdapter<AdapterMenuExpandableCategory.MenuViewHolder, AdapterMenuExpandableCategory.MenuItemViewHolder> {

    private Context context;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private Methods methods;


    public AdapterMenuExpandableCategory(Context context,ArrayList<CategoryResponse> cat, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
        progressDialog = new ProgressDialog(context);
        methods = new Methods(context);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setCancelable(false);
    }



    @Override
    public MenuViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_menucat, parent, false);

        return new MenuViewHolder(itemView);
    }

    class MenuViewHolder extends GroupViewHolder {
        private TextView textView_title;
        private ImageView imageView_arrow;

        MenuViewHolder(View view) {
            super(view);
            textView_title = view.findViewById(R.id.tv_menucat_name);
            imageView_arrow = view.findViewById(R.id.iv_menucat_arrow);
        }

        @Override
        public void expand() {
            imageView_arrow.animate().rotation(90).setDuration(250).setInterpolator(new OvershootInterpolator());
        }

        @Override
        public void collapse() {
            imageView_arrow.animate().rotation(0).setDuration(250).setInterpolator(new OvershootInterpolator());
        }
    }

    @Override
    public MenuItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_menu, parent, false);

        return new MenuItemViewHolder(itemView);
    }

    class MenuItemViewHolder extends ChildViewHolder {
        private TextView textView_title, textView_desc, textView_price, textView_incart, tv_currency;
        private LinearLayout linearLayout;
        private Button btnAddCart;
        private ImageView imageView, imageView_incart, imageView_type;

        MenuItemViewHolder(View view) {
            super(view);
            textView_title = view.findViewById(R.id.tv_menu_name);
            textView_desc = view.findViewById(R.id.tv_menu_desc);
            textView_price = view.findViewById(R.id.tv_menu_price);
            textView_incart = view.findViewById(R.id.tv_menu_incart);
            linearLayout = view.findViewById(R.id.ll_menu);
            imageView = view.findViewById(R.id.iv_menu_image);
            imageView_incart = view.findViewById(R.id.iv1);
            imageView_type = view.findViewById(R.id.iv_menu_type);
            tv_currency = view.findViewById(R.id.tv);
            btnAddCart=view.findViewById(R.id.btn_addCart);

            textView_incart.setTypeface(textView_incart.getTypeface(), Typeface.BOLD);
            tv_currency.setTypeface(null, Typeface.BOLD);
        }
    }

    @Override
    public void onBindGroupViewHolder(MenuViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.textView_title.setTypeface(holder.textView_title.getTypeface(), Typeface.BOLD);
        holder.textView_title.setText(group.getTitle());


    }

    @Override
    public void onBindChildViewHolder(final MenuItemViewHolder holder, final int flatPosition, ExpandableGroup group, final int childIndex) {
        final CategoryResponse itemMenu = (CategoryResponse) group.getItems().get(childIndex);

      /*  if (itemMenu.getType().equals(Constant.TAG_VEG)) {
            holder.imageView_type.setImageResource(R.mipmap.veg);
        } else if (itemMenu.getType().equals(Constant.TAG_NONVEG)) {
            holder.imageView_type.setImageResource(R.mipmap.nonveg);
        }*/

        holder.textView_title.setTypeface(holder.textView_title.getTypeface(), Typeface.BOLD);

        holder.textView_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.textView_desc.getMaxLines() == 2) {
                    holder.textView_desc.setMaxLines(500);
                } else {
                    holder.textView_desc.setMaxLines(2);
                }
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectQuantity(itemMenu, flatPosition);
            }
        });

        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectQuantity(itemMenu, flatPosition);
            }
        });

        holder.textView_incart.setText("");
        holder.imageView_incart.setVisibility(View.GONE);
        for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
            if (Constant.arrayList_cart.get(i).getMenuId().equals(itemMenu.getId())) {
                holder.imageView_incart.setVisibility(View.VISIBLE);
                holder.textView_incart.setText(String.valueOf(Constant.arrayList_cart.get(i).getMenuQty()));
                break;
            }
        }

        holder.textView_title.setText(itemMenu.getName());
      //  holder.textView_desc.setText(itemMenu.);

      //  holder.textView_price.setText(" " + itemMenu.getPrice());

       /* Picasso.get()
                .load(itemMenu.getImage())
                .placeholder(R.drawable.placeholder_menu)
                .into(holder.imageView);*/
    }

    private void openSelectQuantity(final CategoryResponse itemMenu, final int pos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new Dialog(context);
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_quantity);

        ImageView iv_close = dialog.findViewById(R.id.iv_quantity_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        AppCompatButton button_add2cart = dialog.findViewById(R.id.button_quantity_add2cart);
        TextView tv_name = dialog.findViewById(R.id.tv_quantity_menu);
        TextView tv_minus = dialog.findViewById(R.id.tv_quantity_minus);
        TextView tv_plus = dialog.findViewById(R.id.tv_quantity_plus);
        final TextView tv_count = dialog.findViewById(R.id.tv_quantity_count);

        tv_name.setText(itemMenu.getName());
        for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
            if (Constant.arrayList_cart.get(i).getMenuId().equals(itemMenu.getId())) {
                tv_count.setText(Constant.arrayList_cart.get(i).getMenuQty());
                button_add2cart.setText(context.getString(R.string.update_cart));
                break;
            }
        }

        tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tv_count.getText().toString());
                if (count > 1) {
                    count = count - 1;
                    tv_count.setText(String.valueOf(count));
                }
            }
        });

        tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tv_count.getText().toString());
                count = count + 1;
                tv_count.setText(String.valueOf(count));
            }
        });

        button_add2cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.isLogged) {
                    if (methods.isNetworkAvailable()) {
                        if (Constant.arrayList_cart.size() == 0 || Constant.arrayList_cart.get(0).getRestId().equals(itemMenu.getSubcat())) {
                            loadAddMenuApi(itemMenu, tv_count.getText().toString(), pos);
                        } else {
                            openClearDialg(itemMenu, tv_count.getText().toString(), pos);
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.not_log), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.net_not_conn), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void openClearDialg(final CategoryResponse itemMenu, final String count, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.cannot_add_menu_diff_restaurant));
        builder.setPositiveButton(context.getString(R.string.clear), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new LoadClearCart(itemMenu, count, pos).execute(Constant.URL_CLEAR_CART + Constant.itemUser.getId());
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private class LoadClearCart extends AsyncTask<String, String, String> {

        CategoryResponse itemMenu;
        String count;
        int pos;
        ProgressDialog progressDialog = new ProgressDialog(context);
        String message = "";

        LoadClearCart(CategoryResponse itemMenu, String count, int pos) {
            this.itemMenu = itemMenu;
            this.count = count;
            this.pos = pos;
            progressDialog.setMessage(context.getString(R.string.clearing_cart));
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String json = JsonUtils.okhttpGET(url);
            try {
                JSONObject jOb = new JSONObject(json);
                JSONArray jsonArray = jOb.getJSONArray(Constant.TAG_ROOT);

                String success = "0";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    success = c.getString(Constant.TAG_SUCCESS);
                    message = c.getString(Constant.TAG_MSG);
                }
                return success;
            } catch (Exception ee) {
                ee.printStackTrace();
                return "0";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (s.equals("1")) {
                Constant.arrayList_cart.clear();
                loadAddMenuApi(itemMenu, count, pos);
            } else {
                Toast.makeText(context, context.getString(R.string.error_server), Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }

    private void loadAddMenuApi(final CategoryResponse itemMenu, final String menu_count, final int pos) {
        LoadAddMenu loadAddMenu = new LoadAddMenu(context, new LoginListener() {
            @Override
            public void onStart() {
                progressDialog.show();
            }

            @Override
            public void onEnd(String success, String message) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (success.equals("1")) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    if (Constant.arrayList_cart.size() > 0) {
                        for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
                            if (Constant.arrayList_cart.get(i).getMenuId().equals(itemMenu.getId())) {
                                Constant.arrayList_cart.get(i).setMenuQty(menu_count);
                                break;
                            } else if (i == (Constant.arrayList_cart.size() - 1)) {
                                loadCartApi(pos);
//                                Constant.arrayList_cart.add(new ItemCart("", itemMenu.getRestId(), restName, itemMenu.getId(), itemMenu.getName(), itemMenu.getImage(), menu_count, itemMenu.getPrice(), menu_count));
                            }
                        }
                    } else {
                        loadCartApi(pos);
//                        Constant.arrayList_cart.add(new ItemCart("", itemMenu.getRestId(), restName, itemMenu.getId(), itemMenu.getName(), itemMenu.getImage(), menu_count, itemMenu.getPrice(), menu_count));
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadAddMenu.execute(Constant.URL_ADD_MENU_1 + Constant.itemUser.getId() + Constant.URL_ADD_MENU_2 + itemMenu.getId() + Constant.URL_ADD_MENU_3 + itemMenu.getId() + Constant.URL_ADD_MENU_4 + itemMenu.getName() + Constant.URL_ADD_MENU_5 + menu_count + Constant.URL_ADD_MENU_6 + itemMenu.getSubcat());
    }

    private void loadCartApi(final int pos) {
        if (methods.isNetworkAvailable()) {
            LoadCart loadCart = new LoadCart(new CartListener() {
                @Override
                public void onStart() {
                    Constant.arrayList_cart.clear();
                    progressDialog.show();
                }

                @Override
                public void onEnd(String success, String message, ArrayList<ItemCart> array) {
                    if (success.equals("true")) {
                        Constant.arrayList_cart.addAll(array);
                        notifyItemChanged(pos);

                    } else {
//                        errr_msg = getString(R.string.error_server);
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                    ((HotelDetailsActivity) context).changeMenu();
                }
            });
            loadCart.execute(Constant.URL_CART + Constant.itemUser.getId());
        } else {
//                errr_msg = getString(R.string.net_not_conn);
//                setAdapter();
        }
    }
}