package com.Interfaces;

import com.models.LoginExample;
import com.models.LoginPojo;
import com.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {
    @POST("login.php")
    @FormUrlEncoded
    Call<LoginExample> getLogin(
            @Field("mobile") String mobile,
            @Field("password") String password


    );
}
