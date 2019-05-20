package com.vpapps.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by Sagar on 30-01-2019.
 */

public class CheckNetConnectivity {

    private static Dialog dialog;
    private static Context mContext;

    public static boolean isNetworkAvailable(Context context) {
        mContext = context;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void showNoInternetConnPopUp(Context context){
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //((Activity) mContext).finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
