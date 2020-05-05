package fr.julien.mybooks.networking;

import fr.julien.mybooks.models.Books;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksApi {
    @GET("volumes")
    Call<Books> getBooks(@Query("q") String id);

    @GET("volumes")
    Call<Books> getAllBooks(@Query("q") String id, @Query("startIndex") int startIndex, @Query("maxResults") int maxResults);
}
