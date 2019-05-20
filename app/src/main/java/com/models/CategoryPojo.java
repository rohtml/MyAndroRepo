
package com.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryPojo {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<CategoryResponse> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<CategoryResponse> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryResponse> data) {
        this.data = data;
    }

}
