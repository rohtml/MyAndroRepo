
package com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinCodeResponse {

    @SerializedName("cost")
    @Expose
    private String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
