package fr.julien.mybooks.accueil;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import fr.julien.mybooks.R;
import fr.julien.mybooks.allbooks.AllBooksActivity;
import fr.julien.mybooks.databinding.FragmentBooksBinding;
import fr.julien.mybooks.details.DetailsActivity;
import fr.julien.mybooks.factory.MyBookProxy;
import fr.julien.mybooks.factory.ViewModelFactory;
import fr.julien.mybooks.injection.Injection;
import fr.julien.mybooks.models.Item;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.networking.ConnexionInternet;
import fr.julien.mybooks.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**  A simple {@link Fragment} subclass. **/
public class BooksListFragment extends Fragment implements BooksAdapterRV.OnBooksItemClickListener{

    private BooksViewModel booksViewModel;
    private FragmentBooksBinding binding;
    private String contentOfSearch = "";
    public static final String ROMAN_BOOK = "morgan+rice";
    public static final String OTHER_BOOK = "sherlock+holmes";

    public static BooksListFragment newInstance() {
        return new BooksListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.configureRecyclerView();
        this.configureViewModel();
        this.listenerForGetAllBooks();

        try {
            if (ConnexionInternet.isConnected()){
                this.getContentOfSearch();
                this.getRomanBooks();
                this.getOthersBooks();
                binding.searchBooks.getEditText().setText(BooksViewModel.contentOfSearch.replace("+", " "));
                this.getBooksFromSearch(BooksViewModel.contentOfSearch);
                Log.i("DEBUG","connected");
            }else{
                binding.titleTextSearch.setText(R.string.no_wifi);
                binding.titleForSearch.setVisibility(View.VISIBLE);
                binding.cardBookOfTheDay.setVisibility(View.INVISIBLE);
                binding.romanTitle.setVisibility(View.INVISIBLE);
                binding.otherTitle.setVisibility(View.INVISIBLE);
                Log.i("DEBUG","no wifi");
            }
        } catch (InterruptedException | IOException e) { e.printStackTrace();}

        return view;
    }

    private void listenerForGetAllBooks(){
        binding.itemNavigateButton.setOnClickListener(v -> AllBooksActivity.navigate(getActivity(),ROMAN_BOOK));
        binding.itemNavigateButton2.setOnClickListener(v -> AllBooksActivity.navigate(getActivity(),OTHER_BOOK));
        binding.itemNavigateButton3.setOnClickListener(v -> AllBooksActivity.navigate(getActivity(),BooksViewModel.contentOfSearch));
    }

    /** Configuring ViewModel **/
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideRetrofitViewModelFactory();
        this.booksViewModel = new ViewModelProvider(this, mViewModelFactory).get(BooksViewModel.class);
        this.booksViewModel.init(ROMAN_BOOK,OTHER_BOOK);
    }

    private void getBooksFromSearch(String contentOfSearch){
        booksViewModel.getBooksFromSearch(contentOfSearch);
        booksViewModel.getBooksFromSearchRepository().observe(getViewLifecycleOwner(), books -> {
            List<Item> booksItems = books.getItems();
            getResultOfSearch(booksItems);
        });
    }

    private void getResultOfSearch(List<Item> itemList){
        if (itemList.contains(null)){
            binding.titleTextSearch.setText(R.string.no_result);
            binding.titleForSearch.setVisibility(View.VISIBLE);
        }else{
            binding.titleForSearch.setVisibility(View.VISIBLE);
            binding.listOfSearch.setAdapter(new BooksAdapterRV(MyBookProxy.getAllMyBooks(itemList),this));
        }
    }

    private void getRomanBooks(){
        booksViewModel.getRomanBooksRepository().observe(getViewLifecycleOwner(), books -> {
            List<Item> booksItems = books.getItems();
            binding.listBookRoman.setAdapter(new BooksAdapterRV(MyBookProxy.getAllMyBooks(booksItems),this));
        });
    }

    private void getOthersBooks(){
        booksViewModel.getOthersBooksRepository().observe(getViewLifecycleOwner(), books -> {
            List<Item> booksItems = books.getItems();
            this.configureBookOfTheDay(MyBookProxy.getAllMyBooks(booksItems).get(this.generateRandomNumber()));
            binding.listBookAutres.setAdapter(new BooksAdapterRV(MyBookProxy.getAllMyBooks(booksItems),this));
        });
    }

    private void configureBookOfTheDay(MyBooks book){
        binding.cardBookOfTheDay.setOnClickListener(v -> onClickBookItem(book,this.getView()));
        binding.titleBookOfTheDay.setText(book.getTitle());
        binding.authorBookOfTheDay.setText(book.getAuthor());
        binding.descBookOfTheDay.setText(book.getDescription());
        Utils.enableScroll(binding.descBookOfTheDay);
        if (book.getPic().startsWith("http")){
            Glide.with(binding.bookImageOfTheDay.getContext())
                    .load(book.getPic())
                    .into(binding.bookImageOfTheDay);
        }else{
            binding.bookImageOfTheDay.setImageResource(R.drawable.book);
        }
    }

    private int generateRandomNumber(){
        Random random = new Random();
        int nb;
        nb = random.nextInt(9);
        return nb;
    }

    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.listOfSearch.setLayoutManager(layoutManager);
        binding.listBookRoman.setLayoutManager(layoutManager2);
        binding.listBookAutres.setLayoutManager(layoutManager3);
    }

    private void getContentOfSearch(){
        binding.searchBtn.setOnClickListener(v -> {
            this.contentOfSearch = binding.searchBooks.getEditText().getText().toString().replace(" ", "+");
            getBooksFromSearch(contentOfSearch);
        });
    }

    @Override
    public void onClickBookItem(MyBooks book, View viewClicked) { DetailsActivity.navigate(this.getActivity(),book,viewClicked);}
}