package com.Interfaces;


import com.models.AllProductsPojo;
import com.models.PinCodePojo;
import com.models.PinCodePojoRenew;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PinCodeInterface {
    @POST("pincode.php/")
@FormUrlEncoded
    Call<PinCodePojoRenew> getPinCode(@Field("pin") String pin);

}
