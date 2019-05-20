package com.Interfaces;


import com.models.CategoryPojo;
import com.models.SubCategoryPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SubCategoryInterface {
    @POST("subcategory.php/")
@FormUrlEncoded
    Call<SubCategoryPojo> getSubCategory(@Field("id") String id);

}
