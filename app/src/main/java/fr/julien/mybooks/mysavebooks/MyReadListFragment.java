package fr.julien.mybooks.mysavebooks;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.julien.mybooks.databinding.FragmentMyReadListBinding;
import fr.julien.mybooks.details.DetailsActivity;
import fr.julien.mybooks.factory.ViewModelFactory;
import fr.julien.mybooks.injection.Injection;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.repositories.UserDataRepository;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReadListFragment extends Fragment implements MySaveBooksAdapterRV.OnSaveBooksItemClickListener{

    private FragmentMyReadListBinding binding;
    private MySaveBooksViewModel mySaveBooksViewModel;

    public static MyReadListFragment newInstance() {
        return new MyReadListFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyReadListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.configureViewModel();
        this.getAllMyBooksFromReadList();
        this.configureRecyclerView();
        this.scaleViewAnimation(binding.saveReadListBook,200);
        return view;
    }

    /** Configuring ViewModel **/
    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideDaoViewModelFactory(this.getContext());
        this.mySaveBooksViewModel = new ViewModelProvider(this, mViewModelFactory).get(MySaveBooksViewModel.class);
        this.mySaveBooksViewModel.init(UserDataRepository.USER_ID);
    }

    /** Configuring RecyclerView **/
    private void configureRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        binding.saveReadListBook.setLayoutManager(layoutManager);
    }

    private void getAllMyBooksFromReadList() {
        this.mySaveBooksViewModel.getReadListBooks(UserDataRepository.USER_ID, true).observe(getViewLifecycleOwner(), this::updateItemsList);
    }

    private void updateItemsList(List<MyBooks> items) {
        binding.saveReadListBook.setAdapter(new MySaveBooksAdapterRV(items, this,true));
    }

    /** Animation **/
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
        this.mySaveBooksViewModel.updateBookFromReadList(false,books.getIdApi());
        this.getAllMyBooksFromReadList();
    }
}
