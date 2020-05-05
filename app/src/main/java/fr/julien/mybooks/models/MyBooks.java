package fr.julien.mybooks.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId"))
public class MyBooks implements Parcelable {


    @PrimaryKey
    @NonNull private String idApi;
    private String title;
    private String author;
    private String pic;
    private String language;
    private String price;
    private String ebookLink;
    private String byLink;
    private String description;
    private boolean byList;
    private boolean readList;
    private long userId;

    public MyBooks(String id, String title, String author, String pic, String language, String price, String ebookLink, String byLink, String description) {
        this.idApi = id;
        this.title = title;
        this.author = author;
        this.pic = pic;
        this.language = language;
        this.price = price;
        this.ebookLink = ebookLink;
        this.byLink = byLink;
        this.description = description;
    }

    public MyBooks(String idApi, String title, String author, String pic, String language, String price,
                   String ebookLink, String byLink, String description, boolean byList, boolean readList, long userId) {
        this.idApi = idApi;
        this.title = title;
        this.author = author;
        this.pic = pic;
        this.language = language;
        this.price = price;
        this.ebookLink = ebookLink;
        this.byLink = byLink;
        this.description = description;
        this.byList = byList;
        this.readList = readList;
        this.userId = userId;
    }

    public String getIdApi() {
        return idApi;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPic() {
        return pic;
    }

    public String getLanguage() {
        return language;
    }

    public String getPrice() {
        return price;
    }

    public String getEbookLink() {
        return ebookLink;
    }

    public String getByLink() {
        return byLink;
    }

    public String getDescription() {
        return description;
    }

    public boolean isByList() {
        return byList;
    }

    public void setByList(boolean byList) {
        this.byList = byList;
    }

    public boolean isReadList() {
        return readList;
    }

    public void setReadList(boolean readList) {
        this.readList = readList;
    }

    public long getUserId() {
        return userId;
    }

    /** Constructor for Parcelable MyBook **/
    protected MyBooks(Parcel in) {
        this.idApi = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.pic = in.readString();
        this.language = in.readString();
        this.price = in.readString();
        this.ebookLink = in.readString();
        this.byLink = in.readString();
        this.description = in.readString();
    }

    /** For Parcelable MyBook **/
    public static final Creator<MyBooks> CREATOR = new Creator<MyBooks>() {
        @Override
        public MyBooks createFromParcel(Parcel in) {
            return new MyBooks(in);
        }

        @Override
        public MyBooks[] newArray(int size) {
            return new MyBooks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idApi);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(pic);
        parcel.writeString(language);
        parcel.writeString(price);
        parcel.writeString(ebookLink);
        parcel.writeString(byLink);
        parcel.writeString(description);
    }
    /** ********************** **/
}
