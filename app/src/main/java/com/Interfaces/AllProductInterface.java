package com.Interfaces;


import com.models.AllProductsPojo;
import com.models.SubCategoryPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AllProductInterface {
    @POST("product.php/")
@FormUrlEncoded
    Call<AllProductsPojo> getAllProducts(@Field("id") String id);

}
