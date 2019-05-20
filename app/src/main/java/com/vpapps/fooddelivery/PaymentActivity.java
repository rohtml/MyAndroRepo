package com.vpapps.fooddelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;

import com.vpapps.utils.ApiClient;
import com.vpapps.utils.Interfaces.PaymentSuccessInterface;
import com.vpapps.utils.PaymentSuccessResponse;
import com.vpapps.utils.ProgressDialogHelper;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Random;

import datamodels.StaticDataModel;
import retrofit2.Call;
import retrofit2.Callback;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonProceed;
    private String trxnId,productInfo,firstName,email_id,address1,address2,city,state,country,zipcode,phone,salt;
    private String udf1=null,udf2=null,udf3=null,udf4=null,udf5=null,udf6=null,udf7=null,udf8=null,udf9=null,udf10=null;
    private Float amount;
    private String sUrl,fUrl;
    private String key;
    private String merchant_id,payMode;
    private JSONObject split_pay;
    private String title,message;

    public static final String MYAID = "Myaid" ;

    //private List<MyApplicationListDataNew> listData;


    private EditText edittextFirstName,edittextEmail,edittextMobile,edittextAmount,edittextProductInfo;

    RecyclerView recyclerView;
    //private APIService mAPIService;
    //CustomerCartAdapter adapter;
    SharedPreferences product_sharedpreferences;
    public static TextView cartPrice;
    //SaveSharedPreference saveSharedPreference;
    SharedPreferences my_sharedpreferences;
    //int totalAmount;
    private static float totalAmount;
    int nameAid;
    String AidSuccess;
   // MyApplicationListAdapter myApplicationListAdapter;
    public int qwerty;


    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       nameAid = preferences.getInt("Name", 0);

       AidSuccess= String.valueOf(nameAid);
        Log.e("AidSuccess", "onCreate: "+AidSuccess );
       /* if(!name.equalsIgnoreCase(""))
        {
            name = name + "  Sethi";  *//* Edit the value here*//*
        }*/

        initViews();

       // saveSharedPreference=new SaveSharedPreference(PaymentActivity.this);
         //totalAmount = getIntent().getIntExtra("totalAmount",0);
        Log.e("payment", "onCreate: inside pay total amount"+totalAmount);

        Intent i=getIntent();
         totalAmount =i.getIntExtra("totalAmount",0);
        String name = i.getStringExtra("puraskarta");

        Log.e("payment", "onCreate: inside pay total amount"+totalAmount+name);
        //String name = saveSharedPreference.getUsername();
       // String mobile = saveSharedPreference.getMobile();




       // Log.d("TOTAL ERROR", "onCreate: "+totalAmount+mobile);
         //data = getIntent().getIntExtra("totalAmount1",0);
        //edittextAmount.setText(""+data);
        edittextAmount.setText(""+totalAmount);

        edittextFirstName.setText(name);
       // edittextMobile.setText(mobile);

    }

    private void initViews() {
        buttonProceed = findViewById(R.id.button_proceed_merch_form);
        edittextEmail= findViewById(R.id.edit_cust_email);
        edittextMobile= findViewById(R.id.edit_cust_mob_no);
        edittextAmount= findViewById(R.id.edit_pay_amount);
        edittextFirstName= findViewById(R.id.edit_cust_name);
        edittextProductInfo= findViewById(R.id.edit_product_info);
        buttonProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.button_proceed_merch_form:

                Random rand = new Random();

                int  n = rand.nextInt(30000) + 1;

                trxnId = "Chandan"+nameAid+n;
                Log.e("transaction number", "onClick:inside trns "+trxnId+""+nameAid );
                try {
                    amount = Float.parseFloat(String.valueOf(totalAmount));

                }catch(Exception e)
                {
                    amount = 0.0f;
                }


                //    amount = 1.25f;

                productInfo = edittextProductInfo.getText().toString();
                //  productInfo = "mobile111";

                //  firstName = edittextFirstName.getText().toString();
                firstName = edittextFirstName.getText().toString();

                email_id = edittextEmail.getText().toString();
                //  email_id = "ravalkinnary.ec@gmail.com";

                // phone = edittextMobile.getText().toString();
                phone = edittextMobile.getText().toString();

                sUrl="https://www.test.com/success";
                fUrl="https://www.test.com/fail";
                udf1 = "udf1";
                udf2 = "udf2";
                address1="";
                address2="";
                city="ankleshwar";
                state="gujarat";
                country="India";
                zipcode="873498";



                key=" Y8KTQ2NZ37";
                salt="N6GF1WDD99";
                //Note : Please do not use test key,salt for "production" or production key saly for "test" environment.



                payMode="production";
                //make sure when payMode is "production" use production key and salt, When payMode is "test" use test key salt


                setMerchantParameters(trxnId,amount,productInfo,firstName,email_id,phone,sUrl,fUrl,key,udf1,udf2,udf3,udf4,udf5,address1, address2, city, state, country, zipcode,salt,payMode);

                break;

        }
    }

    private void setMerchantParameters(String trxnId, Float amount, String productInfo, String firstName, String email_id, String phone, String sUrl, String fUrl, String key, String udf1, String udf2, String udf3, String udf4, String udf5, String address1, String address2, String city, String state, String country, String zipcode, String salt, String PayMode) {
        Intent intentProceed = new Intent(getBaseContext(), PWECouponsActivity.class);
        intentProceed.putExtra("trxn_id",trxnId); //this should be unique every time
        intentProceed.putExtra("trxn_amount",amount); // this should be float (For Eg: 2000.0f)
        intentProceed.putExtra("trxn_prod_info",productInfo); //Should be string
        intentProceed.putExtra("trxn_firstname",firstName); // customer name
        intentProceed.putExtra("trxn_email_id",email_id); // customers email id
        intentProceed.putExtra("trxn_phone",phone); //customer phone number
        intentProceed.putExtra("trxn_s_url",sUrl); // you can pass empty string here (For Eg : "")
        intentProceed.putExtra("trxn_f_url",fUrl);// you can pass empty string here (For Eg : "")
        intentProceed.putExtra("trxn_key",key);  // Your Merchant key(i.e : Test key for "test" environment, and production key for "production" environment)
        intentProceed.putExtra("trxn_udf1",udf1); // Extra parameter : String
        intentProceed.putExtra("trxn_udf2",udf2);// Extra parameter : String
        intentProceed.putExtra("trxn_udf3",udf3);// Extra parameter : String
        intentProceed.putExtra("trxn_udf4",udf4);// Extra parameter : String
        intentProceed.putExtra("trxn_udf5",udf5);// Extra parameter : String
        intentProceed.putExtra("trxn_udf6","");// Extra parameter : String
        intentProceed.putExtra("trxn_udf7","");// Extra parameter : String
        intentProceed.putExtra("trxn_udf8","");// Extra parameter : String
        intentProceed.putExtra("trxn_udf9","");// Extra parameter : String
        intentProceed.putExtra("trxn_udf10","");// Extra parameter : String
        intentProceed.putExtra("trxn_address1",address1); // Should be string
        intentProceed.putExtra("trxn_address2",address1); // Should be string
        intentProceed.putExtra("trxn_city",city); // Should be string
        intentProceed.putExtra("trxn_state",state); // Should be string
        intentProceed.putExtra("trxn_country",country); // Should be string
        intentProceed.putExtra("trxn_zipcode",zipcode); // Should be string
        intentProceed.putExtra("trxn_is_coupon_enabled",0);
        intentProceed.putExtra("trxn_salt",salt);  // Your Merchant salt(i.e : Test key for "test" environment, and production salt for "production" environment)
        intentProceed.putExtra("pay_mode",PayMode); // "test" for testing and "production" for production

        intentProceed.putExtra("unique_id","11112"); // Cutomers unique id


        startActivityForResult(intentProceed, StaticDataModel.PWE_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null ) {
            String result = data.getStringExtra("result");
            String payment_response = data.getStringExtra("payment_response");

            System.out.println("test result=:"+result);
            System.out.println("test payment response=:"+payment_response);

            try {
                if (result.contains(StaticDataModel.TXN_SUCCESS_CODE)) {
                    showSuccessActivity(payment_response);


                } else if(result.contains(StaticDataModel.TXN_ERROR_RETRY_FAILED_CODE)){

                    showFailActivity(payment_response);

                } else if(result.contains(StaticDataModel.TXN_ERROR_NO_RETRY_CODE)){
                    showFailActivity(payment_response);

                }
                else if(result.contains(StaticDataModel.TXN_TIMEOUT_CODE))
                {
                    showFailActivity(payment_response);
                }
                else if(result.contains(StaticDataModel.TXN_BACKPRESSED_CODE))
                {

                    showFailActivity(payment_response);
                }
                else if(result.contains(StaticDataModel.TXN_USERCANCELLED_CODE))
                {

                    showFailActivity(payment_response);
                }
                else if(result.contains(StaticDataModel.TXN_ERROR_SERVER_ERROR_CODE))
                {
                    showFailActivity(payment_response);
                }
                else if(result.contains(StaticDataModel.TXN_USER_FAILED_CODE))
                {
                    showFailActivity(payment_response);
                }
                else
                {
                    showFailActivity(payment_response);
                }

            }catch (Exception e){
                e.printStackTrace();
                title = "Fail";
                message = "" + result;
            }

        }else{
            Toast.makeText(this, "Payment transaction has been canceled.", Toast.LENGTH_LONG).show();//Could not receive data
        }
    }

    private void showFailActivity(String response) {

//        Intent intentFail = new Intent(PaymentActivity.this,PaymentFailureActivity.class);
//        intentFail.putExtra("response",response);
//        startActivity(intentFail);
//
//
        SubmitSuccess();
        Toast.makeText(this, "Payment failed", Toast.LENGTH_LONG).show();//Could not receive data



    }

    private void showSuccessActivity(String response) {

        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show();//Could not receive data


//        Intent intentSuccess = new Intent(PaymentActivity.this,PaymentSuccessActivity.class);
//        intentSuccess.putExtra("response",response);
//        startActivity(intentSuccess);

     /*   new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)//data.getStringExtra("result")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();  */


    }

    private void SubmitSuccess() {

        try {
            ProgressDialogHelper.showDialog(PaymentActivity.this);

            //String name = etDialogAddChildrenName.getText().toString();
           /* String strsid = saveSharedPreference.getSocietyId();
            String strwing = saveSharedPreference.getWingId();
            String strUid=saveSharedPreference.getLoggerId();
            String strflat= saveSharedPreference.getFlat();*///saveSharedPreference.getLoggerId();

             PaymentSuccessInterface apiService = ApiClient.getClient().create(PaymentSuccessInterface.class);

            Call<PaymentSuccessResponse> call = apiService.getAddSuccessAid(AidSuccess);
            call.enqueue(new Callback<PaymentSuccessResponse>() {
                @Override
                public void onResponse(Call<PaymentSuccessResponse> call, retrofit2.Response<PaymentSuccessResponse> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: "+response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {
                                //strNonAppUserImage="";
                                Log.d("AT RESPONSE", "onResponse: "+response);
                                String statusCoder=response.body().getData();
                                Log.e("hererere", "status CODER: "+statusCoder );
                               /* strInvitePhoto = "";
                                displayView(0);*/
                                Toast.makeText(getApplicationContext(), response.body().getCode(), Toast.LENGTH_SHORT).show();
                            } else {
                                //strInvitePhoto = "";
                                Toast.makeText(getApplicationContext(), response.body().getCode(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("inside suggestion", e.toString());
                            //strInvitePhoto = "";
                        }

                    } else {
                        Toast.makeText(getApplicationContext(),response.message(), Toast.LENGTH_SHORT).show();
                        ProgressDialogHelper.hideDialog();
                    }
                }



                @Override
                public void onFailure(Call<PaymentSuccessResponse> call, Throwable t) {
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
