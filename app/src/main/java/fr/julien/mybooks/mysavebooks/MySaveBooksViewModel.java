package fr.julien.mybooks.mysavebooks;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.models.User;
import fr.julien.mybooks.repositories.MyBooksDataRepository;
import fr.julien.mybooks.repositories.UserDataRepository;
import java.util.List;
import java.util.concurrent.Executor;

public class MySaveBooksViewModel extends ViewModel {

    // REPOSITORIES
    private final MyBooksDataRepository myBooksDataRepository;
    private final UserDataRepository userDataRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<User> currentUser;

    public MySaveBooksViewModel(MyBooksDataRepository myBooksDataRepository, UserDataRepository userDataRepository, Executor executor) {
        this.myBooksDataRepository = myBooksDataRepository;
        this.userDataRepository = userDataRepository;
        this.executor = executor;
    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = userDataRepository.getUser(userId);
    }

    // -------------
    // FOR USER
    // -------------

    /** GET **/
    public LiveData<User> getUser(long userId) { return this.currentUser;  }

    /** INSERT **/
    public void createUser(User user) {
        executor.execute(() -> {
            userDataRepository.createUser(user);
        });
    }

    // -------------
    // FOR BOOKS
    // -------------

    /** GET **/
    public LiveData<List<MyBooks>> getReadListBooks(long userId,boolean readList){
        return myBooksDataRepository.getReadListBooks(userId, readList);
    }

    public LiveData<List<MyBooks>> getByListBooks(long userId,boolean byList){
        return myBooksDataRepository.getByListBooks(userId, byList);
    }

    public LiveData<MyBooks> getOneBook(String idApi){
        return myBooksDataRepository.getOneBook(idApi);
    }

    /** UPDATE **/
    public void updateBookFromReadList(boolean readList, String id) {
        executor.execute(() -> {
            myBooksDataRepository.updateBookFromReadList(readList, id);
        });
    }

    public void updateBookFromByList(boolean byList, String id) {
        executor.execute(() -> {
            myBooksDataRepository.updateBookFromByList(byList, id);
        });
    }

    /** INSERT **/
    public void insertBook(MyBooks myBooks) {
        executor.execute(() -> {
            myBooksDataRepository.insertBook(myBooks);
        });
    }

    /** DELETE **/
    public void deleteBook(String bookId) {
        executor.execute(() -> {
            myBooksDataRepository.deleteBook(bookId);
        });
    }
}