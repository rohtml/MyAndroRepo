package com.Interfaces;


import com.models.CategoryPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryInterface {
    @GET("category.php/")

    Call<CategoryPojo> getCategory();

}
