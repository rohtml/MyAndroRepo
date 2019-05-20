package com.vpapps.fooddelivery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Interfaces.LoginInterface;
import com.models.LoginExample;
import com.models.LoginPojo;
import com.vpapps.asyncTask.LoadLoginLocal;
import com.vpapps.interfaces.LoginListener;
import com.vpapps.sharedPref.SharePref;
import com.vpapps.utils.ApiClient;
import com.vpapps.utils.CheckNetConnectivity;
import com.vpapps.utils.Constant;
import com.vpapps.utils.JsonUtils;
import com.vpapps.utils.Methods;
import com.vpapps.utils.ProgressDialogHelper;
import com.vpapps.utils.SaveSharedPreference;

import java.net.HttpURLConnection;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;
import retrofit2.Call;
import retrofit2.Callback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.vpapps.utils.ProgressDialogHelper.pDialog;

public class LoginActivity extends AppCompatActivity {

    LoadLoginLocal loadLoginLocal;
    SharePref sharePref;
    EditText editText_email, editText_password;
    Button button_login, button_skip;
    TextView textView_register, textView_forgotpass;
    Methods methods;
    ProgressDialog progressDialog;
    LinearLayout ll_checkbox;
    SmoothCheckBox cb_rememberme;
    List<LoginPojo> mListItem;
    SaveSharedPreference saveSharedPreference;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        saveSharedPreference=new SaveSharedPreference(getApplicationContext());
        sharePref = new SharePref(this);
        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());
        methods.setStatusColor(getWindow(), null);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.login_in));
        progressDialog.setCancelable(false);

        ll_checkbox = findViewById(R.id.ll_checkbox);
        cb_rememberme = findViewById(R.id.cb_rememberme);
        editText_email = findViewById(R.id.et_login_email);
        editText_password = findViewById(R.id.et_login_password);
        button_login = findViewById(R.id.button_login);
        button_skip = findViewById(R.id.button_skip);
        textView_register = findViewById(R.id.tv_login_signup);
        textView_forgotpass = findViewById(R.id.tv_forgotpass);

        TextView tv_welcome = findViewById(R.id.tv);

        tv_welcome.setTypeface(tv_welcome.getTypeface(), Typeface.BOLD);
        textView_forgotpass.setTypeface(textView_forgotpass.getTypeface(), Typeface.BOLD);
        textView_register.setTypeface(textView_register.getTypeface(), Typeface.BOLD);
        button_login.setTypeface(button_login.getTypeface(), Typeface.BOLD);
        button_skip.setTypeface(button_skip.getTypeface(), Typeface.BOLD);

        ll_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_rememberme.setChecked(!cb_rememberme.isChecked(), true);
            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textView_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getMobile = editText_email.getText().toString().trim();
                String getPassword = editText_password.getText().toString().trim();

/*

                for (int i = 0; i < mListItem.size(); i++) {
                    categories1.add(mListItem.get(i).getUid());}
*/


                if (getMobile.equals("") || getMobile.length() < 10) {
                    editText_email.requestFocus();
                    editText_email.setError("enter valid phone number");
                    return;
                } else if (getPassword.equals("")) {
                    editText_password.requestFocus();
                    editText_password.setError("enter password");
                    return;

                }

/*
                if (!validation.isInputEditTextFilled(suggestionName, "Enter Name")) {
                    suggestionName.requestFocus();
                    return;
                }
                else if (!validation.isInputEditTextFilled(suggestionMobile, "Enter Mobile No")) {
                    suggestionMobile.requestFocus();
                    return;
                }
               else if (!validation.isInputEditTextFilled(suggestionSubject, "Enter Password")) {
                    suggestionSubject.requestFocus();
                    return;
                }*/ /*else if (!validation.isValidEmail(etAddFamily_EmailId.getText().toString().trim())) {
                    etAddFamily_EmailId.setError("Invalid Email");
                    etAddFamily_EmailId.requestFocus();
                    return;
                }else if (!etAddFamily_Password.getText().toString().trim().equals(etAddFamily_CPassword.getText().toString().trim())) {
                    Toast.makeText(AddFamilyMembersActivity.this,"Password Mismatch",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!Validation.isValidPassword(etAddFamily_CPassword.getText().toString().trim())) {
                    etAddFamily_CPassword.setError(getResources().getString(R.string.password_error_field));
                    return;
                }*/

                else if (CheckNetConnectivity.isNetworkAvailable(LoginActivity.this)) {
                  /*  strMobile = edtMobile.getText().toString();
                    strPassword = edtPassword.getText().toString();*/
                   /* saveSharedPreference.setMobile(strMobile);
                    saveSharedPreference.setPasscode(strPassword);*/

                    submitLogin(getMobile,getPassword);

                } else {
                    CheckNetConnectivity.showNoInternetConnPopUp(LoginActivity.this);
                }
                //  validator.validateAsync();
            }
        });

    }



    private void submitLogin(final String strMobile,final String strPassword ) {

        try {
            ProgressDialogHelper.showDialog(LoginActivity.this);


            LoginInterface apiService = ApiClient.getClient().create(LoginInterface.class);

            Call<LoginExample> call = apiService.getLogin(strMobile,strPassword );
            call.enqueue(new Callback<LoginExample>() {
                @Override
                public void onResponse(Call<LoginExample> call, retrofit2.Response<LoginExample> response) {
                    ProgressDialogHelper.hideDialog();

                    int statusCode = response.code();
                    Log.d("AT RESPONSE", "onResponse: " + response);
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        ProgressDialogHelper.hideDialog();
                        try {
                            if (response.body().getCode().equals("200")) {
                                //strNonAppUserImage="";
                                Log.e("AT RESPONSE", "onResponse: " + response.body().getUid());



                                //saveSharedPreference.setLoggerId(response.body().getUid());
                                saveSharedPreference.setLoggerId(response.body().getUid());
                                saveSharedPreference.setMobile(strMobile);
                                saveSharedPreference.setPasscode(strPassword);
                                saveSharedPreference.setLoggerName(response.body().getName());
                                // loginDatum = new Gson().fromJson(String.valueOf(response), LoginDatum.class);


                                Log.e("inside logins bro", "onResponse: " +saveSharedPreference.getLoggerId());
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);


                               /* strInvitePhoto = "";
                                displayView(0);*/
                                Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
                            } else {
                                //strInvitePhoto = "";
                                Toast.makeText(getApplicationContext(),"outofloop", Toast.LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(), response.body().getCode(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error", e.toString());
                            //strInvitePhoto = "";
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        ProgressDialogHelper.hideDialog();
                    }
                }


                @Override
                public void onFailure(Call<LoginExample> call, Throwable t) {
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

    /*public void uploadData() {

        strEmail = edtEmail.getText().toString();
        strPassword = edtPassword.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", strEmail);
        params.put("password", strPassword);

        client.get(Constant.LOGIN_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dismissProgressDialog();
                String result = new String(responseBody);
                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.ARRAY_NAME);
                    JSONObject objJson;
                    saveSharedPreference.setLoggerName(strName);
                    Log.d("inside login call", "onSuccess: "+saveSharedPreference.getLoggerName());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        if (objJson.has(Constant.MSG)) {
                            strMessage = objJson.getString(Constant.MSG);
                            Constant.GET_SUCCESS_MSG = objJson.getInt(Constant.SUCCESS);
                        } else {
                            Constant.GET_SUCCESS_MSG = objJson.getInt(Constant.SUCCESS);
                            strName = objJson.getString(Constant.USER_NAME);
                            strPassengerId = objJson.getString(Constant.USER_ID);
                            strIsProvider = objJson.getString(Constant.USER_TYPE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setResult();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dismissProgressDialog();
            }

        });
    }


    public void showDialog(){
        final String[] langauges = {"मराठी","English"};

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignInActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(langauges, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){

                    Log.e("ERRORRORO", "onClick: "+which );
                    setLocale("mr");
                    recreate();
                }else if(which == 1){

                    Log.e("ERROR ON new1", "onClick: "+which );
                    setLocale("en");
                    recreate();
                }
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    private void setLocale(String lang) {
        Log.d("Language",""+lang);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("lang_setting",MODE_PRIVATE).edit();
        editor.putString("lang",lang);
        //editor.putString("mob",mob_no);
        editor.apply();

    }

    public void loadLoacale(){
        SharedPreferences preferences = getSharedPreferences("lang_setting", MODE_PRIVATE);
        String language = preferences.getString("lang","");
        setLocale(language);
    }*/
/////////////////////////////////////////
   /* public void setResult() {

        if (Constant.GET_SUCCESS_MSG == 0) {
            showToast("Opps. \n" + strMessage);

        } else {
            MyApp.saveIsLogin(true);
            MyApp.saveIsJobProvider(strIsProvider.equals("2"));
            MyApp.saveLogin(strPassengerId, strName, strMobile);

            Intent i = new Intent(LoginActivity.this, MyApp.getIsJobProvider() ? JobProviderMainActivity.class : MainActivity.class);
            startActivity(i);
            finish();
        }*/
   // }

    public void showToast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void dismissProgressDialog() {
        pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

/*
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (methods.isNetworkAvailable()) {
                    attemptLogin();
                } else {
                    methods.showToast(getString(R.string.net_not_conn));
                }
            }
        });
    }

    private void attemptLogin() {
        editText_email.setError(null);
        editText_password.setError(null);

        // Store values at the time of the login attempt.
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !methods.isPasswordValid(password)) {
            editText_password.setError(getString(R.string.error_invalid_password));
            focusView = editText_password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editText_email.setError(getString(R.string.error_field_required));
            focusView = editText_email;
            cancel = true;
        } else if (!methods.isEmailValid(email)) {
            editText_email.setError(getString(R.string.error_invalid_email));
            focusView = editText_email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
           // loadLogin();
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void loadLogin() {
        loadLoginLocal = new LoadLoginLocal(this, new LoginListener() {
            @Override
            public void onStart() {
                progressDialog.show();
            }

            @Override
            public void onEnd(String success, String message) {
                progressDialog.dismiss();
                if (success.equals("true")) {
                    if (message.equals("0")) {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.email_pass_nomatch), Toast.LENGTH_SHORT).show();
                    } else {
                        Constant.isLogged = true;
                        sharePref.setSharedPreferences(editText_email.getText().toString(), editText_password.getText().toString());
                        methods.showToast(getString(R.string.login_success));
                        openMainActivity();
                    }
                } else {
                    methods.showToast(getString(R.string.error_login));
                }
            }
        });

        loadLoginLocal.execute(Constant.URL_LOGIN_1 + editText_email.getText().toString() + Constant.URL_LOGIN_2 + editText_password.getText().toString());
    }
}*/
