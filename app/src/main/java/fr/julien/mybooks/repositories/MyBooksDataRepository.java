package fr.julien.mybooks.repositories;

import androidx.lifecycle.LiveData;
import fr.julien.mybooks.database.MyBooksDao;
import fr.julien.mybooks.models.MyBooks;
import java.util.List;

public class MyBooksDataRepository {

    private final MyBooksDao myBooksDao;

    public MyBooksDataRepository(MyBooksDao myBooksDao){this.myBooksDao = myBooksDao;}

    /** GET **/
    public LiveData<List<MyBooks>> getReadListBooks(long userId, boolean readList){return this.myBooksDao.getReadListBooks(userId, readList);}

    public LiveData<List<MyBooks>> getByListBooks(long userId, boolean byList){return this.myBooksDao.getByListBooks(userId ,byList);}

    public LiveData<MyBooks> getOneBook(String idApi){return this.myBooksDao.getOneBooks(idApi);}

    /** UPDATE **/
    public void updateBookFromReadList(boolean readList, String id){this.myBooksDao.updateBookFromReadList(readList,id);}

    public void updateBookFromByList(boolean byList, String id){this.myBooksDao.updateBookFromByList(byList,id);}

    /** INSERT **/
    public void insertBook(MyBooks myBooks){this.myBooksDao.insertBook(myBooks);}

    /** DELETE **/
    public void deleteBook(String bookId){this.myBooksDao.deleteBook(bookId);}
}
