package com.vpapps.asyncTask;

import android.os.AsyncTask;

import com.vpapps.interfaces.MenuCatListener;
import com.vpapps.items.ItemMenu;
import com.vpapps.items.ItemMenuCat;
import com.vpapps.utils.Constant;
import com.vpapps.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadMenuCat extends AsyncTask<String, String, Boolean> {

    private MenuCatListener menuCatListener;
    private ArrayList<ItemMenuCat> arrayList;

    public LoadMenuCat(MenuCatListener menuCatListener) {
        this.menuCatListener = menuCatListener;
        arrayList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        menuCatListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String url = strings[0];
        String json = JsonUtils.okhttpGET(url);
        try {
            JSONObject jOb = new JSONObject(json);
            JSONArray jsonArray = jOb.getJSONArray(Constant.TAG_ROOT);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                String id = c.getString(Constant.TAG_CAT_ID);
                String name = c.getString(Constant.TAG_CAT_NAME);
                String hotel_id = c.getString(Constant.TAG_REST_ID);

                ArrayList<ItemMenu> arrayListMenu = new ArrayList<>();
                if(c.has("menu_list")) {
                    JSONArray jA = c.getJSONArray("menu_list");
                    for (int j = 0; j < jA.length(); j++) {
                        JSONObject jsonObject = jA.getJSONObject(j);

                        String menu_id = jsonObject.getString(Constant.TAG_MENU_ID);
                        String menu_name = jsonObject.getString(Constant.TAG_MENU_NAME);
                        String menu_type = jsonObject.getString(Constant.TAG_MENU_TYPE);
                        String desc = jsonObject.getString(Constant.TAG_MENU_DESC);
                        String price = jsonObject.getString(Constant.TAG_MENU_PRICE);
                        String image = jsonObject.getString(Constant.TAG_MENU_IMAGE);
                        String cat_id = jsonObject.getString(Constant.TAG_MENU_CAT);
                        String res_id = jsonObject.getString(Constant.TAG_MENU_REST_ID);

                        ItemMenu itemMenu = new ItemMenu(menu_id, menu_name, menu_type, image, desc, price, res_id, cat_id);
                        arrayListMenu.add(itemMenu);
                    }
                }

                ItemMenuCat itemMenuCat = new ItemMenuCat(id, name, hotel_id, arrayListMenu);
                arrayList.add(itemMenuCat);

            }

            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean s) {
        menuCatListener.onEnd(String.valueOf(s), arrayList);
        super.onPostExecute(s);
    }
}