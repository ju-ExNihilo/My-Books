package fr.julien.mybooks.details;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import fr.julien.mybooks.R;
import fr.julien.mybooks.accueil.BooksListActivity;
import fr.julien.mybooks.models.MyBooks;

public class DetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.configureToolbar();
        this.showBooksDetailsFragment();
    }

    private void showBooksDetailsFragment(){
        DetailsFragment booksDetailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.books_details_frame_layout);
        if (booksDetailsFragment == null ) {
            booksDetailsFragment = DetailsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.books_details_frame_layout, booksDetailsFragment).commit();
        }
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ((ActionBar) ab).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_activity_save :
                BooksListActivity.navigate(this);
                break;
            case android.R.id.home :
                finish();
                break;
        }
        return true;
    }

    /** Used to navigate to this activity **/
    public static void navigate(FragmentActivity activity, MyBooks book, View viewClicked) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(DetailsFragment.KEY_MEETING, book);
        // Start Animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, viewClicked, activity.getString(R.string.transition_animation));
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } else {
            ActivityCompat.startActivity(activity, intent, null);
        }

    }
}
