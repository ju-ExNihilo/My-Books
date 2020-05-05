package fr.julien.mybooks.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import fr.julien.mybooks.models.MyBooks;
import java.util.List;

@Dao
public interface MyBooksDao {

    @Query("SELECT * FROM MyBooks WHERE userId = :userId AND byList = :byList")
    LiveData<List<MyBooks>> getByListBooks(long userId, boolean byList);

    @Query("SELECT * FROM MyBooks WHERE userId = :userId AND readList = :readList")
    LiveData<List<MyBooks>> getReadListBooks(long userId, boolean readList);

    @Query("SELECT * FROM MyBooks WHERE idApi = :idApi")
    LiveData<MyBooks> getOneBooks(String idApi);

    @Query("UPDATE MyBooks SET readList = :readList WHERE idApi =:idApi")
    void updateBookFromReadList(boolean readList, String idApi);

    @Query("UPDATE MyBooks SET byList = :byList WHERE idApi =:idApi")
    void updateBookFromByList(boolean byList, String idApi);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(MyBooks myBooks);

    @Query("DELETE FROM MyBooks WHERE idApi = :bookId")
    void deleteBook(String bookId);
}
