package fr.julien.mybooks.accueil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import fr.julien.mybooks.models.Books;
import fr.julien.mybooks.repositories.BooksRepository;

public class BooksViewModel extends ViewModel {

    private final BooksRepository booksRepository;
    public static String contentOfSearch = "";
    public static String similarSearch = "";

    /** DATA **/
    private MutableLiveData<Books> mutableLiveData;
    private MutableLiveData<Books> mutableLiveAllData;
    private MutableLiveData<Books> romanLiveData;
    private MutableLiveData<Books> othersLiveData;
    private MutableLiveData<Books> similarLiveData;

    public BooksViewModel(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void init(String similarSearch){
        if (this.similarLiveData != null && BooksViewModel.similarSearch.equals(similarSearch)){
            return;
        }
        this.similarLiveData = booksRepository.getBooks(similarSearch);
        this.mutableLiveAllData = booksRepository.getAllBooks(similarSearch);
    }

    public void init(String roman, String others){
        if (this.romanLiveData != null && this.othersLiveData != null){
            return;
        }
        this.romanLiveData = booksRepository.getBooks(roman);
        this.othersLiveData = booksRepository.getBooks(others);

    }

    /** Get searching books **/
    public void getBooksFromSearch(String contentOfSearch){
        if (mutableLiveData != null && BooksViewModel.contentOfSearch.equals(contentOfSearch)){
            return;
        }
        BooksViewModel.contentOfSearch = contentOfSearch;
        mutableLiveData = booksRepository.getBooks(contentOfSearch);
    }
    public LiveData<Books> getBooksFromSearchRepository() {return mutableLiveData;}

    /** Get roman books **/
    public LiveData<Books> getRomanBooksRepository() {
        return romanLiveData;
    }

    /** Get similar books **/
    public LiveData<Books> getSimilarBooksRepository() {
        return similarLiveData;
    }

    /** Get others books **/
    public LiveData<Books> getOthersBooksRepository() {
        return othersLiveData;
    }

    /** Get all books **/
    public LiveData<Books> getAllBooksRepository() {
        return mutableLiveAllData;
    }

}
