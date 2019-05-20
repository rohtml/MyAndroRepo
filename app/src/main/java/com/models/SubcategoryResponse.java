
package com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubcategoryResponse {

    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("name")
    @Expose
    private String name;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
