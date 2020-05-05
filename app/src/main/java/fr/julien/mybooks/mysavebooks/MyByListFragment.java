package fr.julien.mybooks.mysavebooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.julien.mybooks.databinding.FragmentMyByListBinding;
import fr.julien.mybooks.details.DetailsActivity;
import fr.julien.mybooks.factory.ViewModelFactory;
import fr.julien.mybooks.injection.Injection;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.repositories.UserDataRepository;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyByListFragment extends Fragment implements MySaveBooksAdapterRV.OnSaveBooksItemClickListener{

    private FragmentMyByListBinding binding;
    private MySaveBooksViewModel mySaveBooksViewModel;

    public static MyByListFragment newInstance() {return new MyByListFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyByListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.configureViewModel();
        this.getAllMyBooksFromByList();
        this.configureRecyclerView();
        this.scaleViewAnimation(binding.saveByListBook,200);
        return view;
    }

    /** Configuring ViewModel **/
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideDaoViewModelFactory(this.getContext());
        this.mySaveBooksViewModel = new ViewModelProvider(this, mViewModelFactory).get(MySaveBooksViewModel.class);
        this.mySaveBooksViewModel.init(UserDataRepository.USER_ID);
    }

    /** Configuring RecyclerView **/
    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        binding.saveByListBook.setLayoutManager(layoutManager);

    }

    public void getAllMyBooksFromByList(){
        this.mySaveBooksViewModel.getByListBooks(UserDataRepository.USER_ID, true).observe(getViewLifecycleOwner(), this::updateItemsList);
    }

    private void updateItemsList(List<MyBooks> items){
        binding.saveByListBook.setAdapter(new MySaveBooksAdapterRV(items,this,true));
    }

    /** Animation Diverse **/
    private void scaleViewAnimation(View view, int startDelay){
        // Reset view
        view.setScaleX(0);
        view.setScaleY(0);
        // Animate view
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(startDelay)
                .setDuration(500)
                .start();
    }

    @Override
    public void onClickBookItem(MyBooks book, View viewClicked) {DetailsActivity.navigate(this.getActivity(),book,viewClicked);}

    @Override
    public void onDeleteClick(MyBooks books) {
        this.mySaveBooksViewModel.updateBookFromByList(false,books.getIdApi());
        this.getAllMyBooksFromByList();
    }
}
