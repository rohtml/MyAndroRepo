package com.vpapps.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.vpapps.fooddelivery.R;


/**
 * Created by Sagar on 06-02-2019.
 */

public class ProgressDialogHelper {
    public static ProgressDialog pDialog;
    public static Context mContext;

    public static void showDialog(Context context){
        mContext = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("please wait");
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public static void hideDialog(){
                pDialog.dismiss();
    }

    public static ProgressDialogHelper mShowProgress;
    public static Dialog mDialog;

    public static ProgressDialogHelper getInstance() {
        if (mShowProgress== null) {
            mShowProgress= new ProgressDialogHelper();
        }
        return mShowProgress;
    }
}
