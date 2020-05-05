package fr.julien.mybooks.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import fr.julien.mybooks.R;
import fr.julien.mybooks.accueil.BooksViewModel;
import fr.julien.mybooks.databinding.FragmentDetailsBinding;
import fr.julien.mybooks.factory.MyBookProxy;
import fr.julien.mybooks.factory.ViewModelFactory;
import fr.julien.mybooks.injection.Injection;
import fr.julien.mybooks.models.Item;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.mysavebooks.MySaveBooksActivity;
import fr.julien.mybooks.mysavebooks.MySaveBooksViewModel;
import fr.julien.mybooks.repositories.UserDataRepository;
import fr.julien.mybooks.utils.Utils;

import java.util.List;

public class DetailsFragment extends Fragment implements AdapterDetailBook.OnBooksItemClickListener{

    private FragmentDetailsBinding binding;
    private MySaveBooksViewModel mySaveBooksViewModel;
    private BooksViewModel booksViewModel;
    public static final String KEY_MEETING = "KEY_MEETING";
    private MyBooks book;
    private MyBooks myBooks;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.configureSaveBookViewModel();
        this.configureBottomView();
        this.configureRecyclerView();

        Intent intent = this.getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(KEY_MEETING)) {
                this.book = intent.getParcelableExtra(KEY_MEETING);
                this.configureBookViewModel(book.getAuthor());
                binding.titleDetails.setText(book.getTitle());
                binding.detailAuthor.setText(book.getAuthor());
                binding.detailPrice.setText(book.getPrice());
                binding.detailEbook.setText(book.getEbookLink());
                binding.detailByLink.setText(book.getByLink());
                binding.descriptionBookDetail.setText(book.getDescription());
                Utils.enableScroll(binding.descriptionBookDetail);
                if (book.getPic().startsWith("http")){
                    Glide.with(binding.bookImageDetails.getContext())
                            .load(book.getPic())
                            .into(binding.bookImageDetails);
                }else{
                    binding.bookImageDetails.setImageResource(R.drawable.book);
                }
                this.ifBookAlreadySave(book.getIdApi());
                this.getSimilarBooks();
            }
        }
        return view;
    }

    /** listener for Bottom Navigation View **/
    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.action_add_by_list:
                this.insertInByList();
                break;
            case R.id.action_add_read_list:
                this.insertInReadList();
                break;
            case R.id.action_go_my_book:
                MySaveBooksActivity.navigate(this.getActivity());
                break;
        }
        return true;
    }

    /** Configuring Bottom Navigation View **/
    private void configureBottomView(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    /** look if book is already in dataBase  **/
    private void ifBookAlreadySave(String idApi){
        mySaveBooksViewModel.getOneBook(idApi).observe(getViewLifecycleOwner(), this::configureView);
    }

    /** Configuring SaveBookViewModel **/
    private void configureSaveBookViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideDaoViewModelFactory(this.getContext());
        this.mySaveBooksViewModel = new ViewModelProvider(this, mViewModelFactory).get(MySaveBooksViewModel.class);
        this.mySaveBooksViewModel.init(UserDataRepository.USER_ID);
    }

    /** Configuring BookViewModel **/
    private void configureBookViewModel(String author){
        ViewModelFactory mViewModelFactory = Injection.provideRetrofitViewModelFactory();
        this.booksViewModel = new ViewModelProvider(this, mViewModelFactory).get(BooksViewModel.class);
        this.booksViewModel.init(author);
    }

    /** Configuring RecyclerView **/
    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.listBookSimilar.setLayoutManager(layoutManager2);
    }

    /** Get Similar Books **/
    private void getSimilarBooks(){
        booksViewModel.getSimilarBooksRepository().observe(getViewLifecycleOwner(), books -> {
            List<Item> booksItems = books.getItems();
            binding.listBookSimilar.setAdapter(new AdapterDetailBook(MyBookProxy.getAllMyBooks(booksItems),this));
        });
    }

    /** insert or update if already in data base for read list  **/
    private void insertInReadList(){
        if (binding.bottomNavigation.getMenu().getItem(2).getTitle().equals(getString(R.string.in_read_list))){
            this.myBooks.setReadList(false);
            Toast.makeText(this.getContext(), getString(R.string.sup_read_list_toast), Toast.LENGTH_SHORT).show();
            binding.bottomNavigation.getMenu().getItem(2).setTitle(getString(R.string.add_read_list_menu));
        }else{
            this.myBooks.setReadList(true);
            Toast.makeText(this.getContext(), getString(R.string.add_read_list_toast), Toast.LENGTH_SHORT).show();
        }
        this.mySaveBooksViewModel.insertBook(myBooks);
    }

    /** insert or update if already in data base for by list  **/
    private void insertInByList(){
        if (binding.bottomNavigation.getMenu().getItem(1).getTitle().equals(getString(R.string.in_by_list))){
            this.myBooks.setByList(false);
            Toast.makeText(this.getContext(), getString(R.string.sup_by_list_toast), Toast.LENGTH_SHORT).show();
            binding.bottomNavigation.getMenu().getItem(1).setTitle(getString(R.string.add_by_list_menu));
        }else{
            this.myBooks.setByList(true);
            Toast.makeText(this.getContext(), getString(R.string.add_by_list_toast), Toast.LENGTH_SHORT).show();
        }
        this.mySaveBooksViewModel.insertBook(myBooks);
    }

    /** Configuring Bottom Navigation View and initialize object myBooks **/
    private void configureView(MyBooks books) {
        if (books != null){
            this.myBooks = books;
            if (books.isReadList()){
                binding.bottomNavigation.getMenu().getItem(2).setTitle(getString(R.string.in_read_list));
            }
            if (books.isByList()){
                binding.bottomNavigation.getMenu().getItem(1).setTitle(getString(R.string.in_by_list));
            }
        }else {
            this.myBooks = new MyBooks(book.getIdApi(),book.getTitle(),book.getAuthor(),book.getPic(),book.getLanguage(),book.getPrice(),
                    book.getEbookLink(),book.getByLink(),book.getDescription(),false,false, UserDataRepository.USER_ID);
        }
    }

    @Override
    public void onClickBookItem(MyBooks book, View viewClicked) { DetailsActivity.navigate(this.getActivity(),book,viewClicked);}
}
