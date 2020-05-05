package fr.julien.mybooks.database;

import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.models.User;

@Database(entities = {MyBooks.class, User.class}, version = 1, exportSchema = false)
public abstract class MyBooksDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MyBooksDatabase INSTANCE;

    // --- DAO ---
    public abstract MyBooksDao myBooksDao();
    public abstract UserDao userDao();

    // --- INSTANCE ---
    public static MyBooksDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyBooksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyBooksDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Julien");
                contentValues.put("urlPicture", "https://i.pravatar.cc/150?u=a042581f4e29026704f");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}