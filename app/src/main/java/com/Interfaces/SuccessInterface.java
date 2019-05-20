package com.Interfaces;

import com.models.LoginPojo;
import com.models.SuccessPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SuccessInterface {
    @POST("success.php")
    @FormUrlEncoded
    Call<SuccessPojo> getSSuccess(
            @Field("uid") String uid,
            @Field("pid") String pid,
            @Field("pname") String pname,
            @Field("pqty") String pqty,
            @Field("prate") String prate,
            @Field("total") String total,
            @Field("cname") String cname,
            @Field("cemail") String cemail



    );
}
