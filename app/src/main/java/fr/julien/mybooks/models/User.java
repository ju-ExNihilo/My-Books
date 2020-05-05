package fr.julien.mybooks.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private long id;
    private String username;
    private String urlPicture;

    public User(long id, String username, String urlPicture) {
        this.id = id;
        this.username = username;
        this.urlPicture = urlPicture;
    }

    // --- GETTER ---

    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }

}
