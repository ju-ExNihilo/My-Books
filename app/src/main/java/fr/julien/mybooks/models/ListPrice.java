package fr.julien.mybooks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPrice {

    @SerializedName("amount")
    @Expose
    private Double amount;

    public Double getAmount() {
        return amount;
    }

}
