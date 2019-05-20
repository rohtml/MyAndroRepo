
package com.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subcat")
    @Expose
    private ArrayList<SubcategoryResponse> subcat = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SubcategoryResponse> getSubcat() {
        return subcat;
    }

    public void setSubcat(ArrayList<SubcategoryResponse> subcat) {
        this.subcat = subcat;
    }

}
