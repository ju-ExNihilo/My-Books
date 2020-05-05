package fr.julien.mybooks.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import fr.julien.mybooks.accueil.BooksViewModel;
import fr.julien.mybooks.mysavebooks.MySaveBooksViewModel;
import fr.julien.mybooks.repositories.BooksRepository;
import fr.julien.mybooks.repositories.MyBooksDataRepository;
import fr.julien.mybooks.repositories.UserDataRepository;
import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private MyBooksDataRepository myBooksDataRepository;
    private UserDataRepository userDataRepository;
    private Executor executor;
    /** retrofit **/
    private BooksRepository booksRepository;

    public ViewModelFactory(MyBooksDataRepository myBooksDataRepository, UserDataRepository userDataRepository, Executor executor) {
        this.myBooksDataRepository = myBooksDataRepository;
        this.userDataRepository = userDataRepository;
        this.executor = executor;
    }

    public ViewModelFactory(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MySaveBooksViewModel.class)) {
            return (T) new MySaveBooksViewModel(myBooksDataRepository, userDataRepository, executor);
        }else if (modelClass.isAssignableFrom(BooksViewModel.class)) {
            return (T) new BooksViewModel(booksRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
