
package com.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryPojo {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<SubcategoryResponse> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<SubcategoryResponse> getData() {
        return data;
    }

    public void setData(ArrayList<SubcategoryResponse> data) {
        this.data = data;
    }

}
