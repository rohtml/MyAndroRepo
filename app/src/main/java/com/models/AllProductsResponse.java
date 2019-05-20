
package com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllProductsResponse {

    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("p_old_price")
    @Expose
    private String pOldPrice;
    @SerializedName("p_current_price")
    @Expose
    private String pCurrentPrice;
    @SerializedName("p_qty")
    @Expose
    private String pQty;
    @SerializedName("p_description")
    @Expose
    private String pDescription;
    @SerializedName("link")
    @Expose
    private String link;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPOldPrice() {
        return pOldPrice;
    }

    public void setPOldPrice(String pOldPrice) {
        this.pOldPrice = pOldPrice;
    }

    public String getPCurrentPrice() {
        return pCurrentPrice;
    }

    public void setPCurrentPrice(String pCurrentPrice) {
        this.pCurrentPrice = pCurrentPrice;
    }

    public String getPQty() {
        return pQty;
    }

    public void setPQty(String pQty) {
        this.pQty = pQty;
    }

    public String getPDescription() {
        return pDescription;
    }

    public void setPDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
