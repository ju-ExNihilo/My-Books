package fr.julien.mybooks.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("authors")
    @Expose
    private List<String> authors = null;
    @SerializedName("imageLinks")
    @Expose
    private ImageLinks imageLinks;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("description")
    @Expose
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public ImageLinks getImageLinks() {
        if (imageLinks == null){
            imageLinks = new ImageLinks();
            imageLinks.setSmallThumbnail("");
        }
        return imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

}