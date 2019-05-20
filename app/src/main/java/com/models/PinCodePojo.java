
package com.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinCodePojo {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<PinCodeResponse> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<PinCodeResponse> getData() {
        return data;
    }

    public void setData(ArrayList<PinCodeResponse> data) {
        this.data = data;
    }

}
