package fr.julien.mybooks.allbooks;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.julien.mybooks.accueil.BooksViewModel;
import fr.julien.mybooks.databinding.ActivityAllBooksBinding;
import fr.julien.mybooks.details.DetailsActivity;
import fr.julien.mybooks.factory.MyBookProxy;
import fr.julien.mybooks.factory.ViewModelFactory;
import fr.julien.mybooks.injection.Injection;
import fr.julien.mybooks.models.Item;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.mysavebooks.MySaveBooksAdapterRV;
import fr.julien.mybooks.networking.ConnexionInternet;
import java.io.IOException;
import java.util.List;

public class AllBooksActivity extends AppCompatActivity implements MySaveBooksAdapterRV.OnSaveBooksItemClickListener{

    private ActivityAllBooksBinding binding;
    private BooksViewModel booksViewModel;
    private String subject = "";
    public static final String KEY_SUBJECT = "KEY_SUBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllBooksBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.configureToolbar();
        this.configureRecyclerView();
        this.fromBottomAnimation(binding.allListBook,200);

        Intent intent = this.getIntent();
        if (intent != null) {
            if (intent.hasExtra(KEY_SUBJECT)) {
                subject = intent.getStringExtra(KEY_SUBJECT);
                this.configureViewModel();
                try {
                    if (ConnexionInternet.isConnected()){
                        this.getBooksFromSearch();
                        Log.i("DEBUG","connected");
                    }else{

                        Log.i("DEBUG","no wifi");
                    }
                } catch (InterruptedException | IOException e) { e.printStackTrace();}
            }
        }
        setContentView(view);
    }

    private void configureToolbar(){
        setSupportActionBar(binding.toolbarAllBook);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ((ActionBar) ab).setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                break;
        }
        return true;
    }

    /** Configuring RecyclerView **/
    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.allListBook.setLayoutManager(layoutManager);

    }

    /** Configuring ViewModel **/
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideRetrofitViewModelFactory();
        this.booksViewModel = new ViewModelProvider(this, mViewModelFactory).get(BooksViewModel.class);
        this.booksViewModel.init(subject);
    }

    private void getBooksFromSearch(){
        booksViewModel.getAllBooksRepository().observe(this, books -> {
            List<Item> booksItems = books.getItems();
            binding.allListBook.setAdapter(new MySaveBooksAdapterRV(MyBookProxy.getAllMyBooks(booksItems),this,false));
        });
    }

    private void fromBottomAnimation(View view, int startDelay) {
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setStartOffset(startDelay);
        view.startAnimation(animation);
    }

    @Override
    public void onClickBookItem(MyBooks book, View viewClicked) { DetailsActivity.navigate(this,book,viewClicked);}

    @Override
    public void onDeleteClick(MyBooks books) { }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity, String subject) {
        Intent intent = new Intent(activity, AllBooksActivity.class);
        intent.putExtra(KEY_SUBJECT, subject);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
