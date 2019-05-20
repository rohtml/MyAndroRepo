package com.vpapps.utils.Interfaces;


import com.vpapps.utils.PaymentSuccessResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PaymentSuccessInterface {





    @POST("success.php")
    @FormUrlEncoded
    Call<PaymentSuccessResponse> getAddSuccessAid(@Field("aid") String aid


    );
}
