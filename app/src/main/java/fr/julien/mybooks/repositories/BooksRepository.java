package fr.julien.mybooks.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import fr.julien.mybooks.models.Books;
import fr.julien.mybooks.networking.BooksApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BooksRepository {

    private final BooksApi booksApi;
    MutableLiveData<Books> booksAllData = new MutableLiveData<>();

    public BooksRepository(BooksApi booksApi){
        this.booksApi = booksApi;
    }

    public MutableLiveData<Books> getBooks(String source){
        MutableLiveData<Books> booksData = new MutableLiveData<>();
        booksApi.getBooks(source).enqueue(new Callback<Books>() {
            @Override
            public void onResponse(@NonNull Call<Books> call,
                                   @NonNull Response<Books> response) {
                if (response.isSuccessful()){
                    booksData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Books> call, @NonNull Throwable t) {
                booksData.setValue(null);
            }
        });
        return booksData;
    }

    public MutableLiveData<Books> getAllBooks(String source){

        booksApi.getAllBooks(source,0,40).enqueue(new Callback<Books>() {
            @Override
            public void onResponse(@NonNull Call<Books> call,
                                   @NonNull Response<Books> response) {
                if (response.isSuccessful()){
                    booksAllData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Books> call, @NonNull Throwable t) {
                booksAllData.setValue(null);
            }
        });
        return booksAllData;
    }
}