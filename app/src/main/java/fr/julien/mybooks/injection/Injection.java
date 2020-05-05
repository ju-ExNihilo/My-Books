package fr.julien.mybooks.injection;

import android.content.Context;
import fr.julien.mybooks.database.MyBooksDatabase;
import fr.julien.mybooks.factory.ViewModelFactory;
import fr.julien.mybooks.networking.BooksApi;
import fr.julien.mybooks.networking.RetrofitService;
import fr.julien.mybooks.repositories.BooksRepository;
import fr.julien.mybooks.repositories.MyBooksDataRepository;
import fr.julien.mybooks.repositories.UserDataRepository;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static MyBooksDataRepository provideItemDataSource(Context context) {
        MyBooksDatabase database = MyBooksDatabase.getInstance(context);
        return new MyBooksDataRepository(database.myBooksDao());
    }

    public static UserDataRepository provideUserDataSource(Context context) {
        MyBooksDatabase database = MyBooksDatabase.getInstance(context);
        return new UserDataRepository(database.userDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideDaoViewModelFactory(Context context) {
        MyBooksDataRepository dataSourceItem = provideItemDataSource(context);
        UserDataRepository dataSourceUser = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, dataSourceUser, executor);
    }

    /** retrofit **/
    public static BooksRepository provideBooksRetrofitSource(){
        BooksApi booksApi = RetrofitService.createService(BooksApi.class);
        return new BooksRepository(booksApi);
    }

    public static ViewModelFactory provideRetrofitViewModelFactory(){
        BooksRepository booksRepository = provideBooksRetrofitSource();
        return new ViewModelFactory(booksRepository);
    }
}
