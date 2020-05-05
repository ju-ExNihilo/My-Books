package fr.julien.mybooks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessInfo {

    @Expose
    private String webReaderLink;
    @SerializedName("accessViewStatus")


    public String getWebReaderLink() {
        return webReaderLink;
    }


}