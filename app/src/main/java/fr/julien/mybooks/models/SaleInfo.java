package fr.julien.mybooks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleInfo {

    @SerializedName("saleability")
    @Expose
    private String saleability;
    @SerializedName("listPrice")
    @Expose
    private ListPrice listPrice;
    @SerializedName("buyLink")
    @Expose
    private String buyLink;


    public String getSaleability() {
        return saleability;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

}