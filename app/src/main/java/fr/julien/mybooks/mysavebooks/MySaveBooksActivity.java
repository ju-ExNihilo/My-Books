package fr.julien.mybooks.mysavebooks;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import fr.julien.mybooks.R;
import fr.julien.mybooks.accueil.BooksListActivity;


public class MySaveBooksActivity extends AppCompatActivity {

    private ListBooksPageAdapter listBooksPageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_save_books);
        this.configureToolbar();
        tabLayout = (TabLayout) findViewById(R.id.table_layout);
        viewPager = (ViewPager) findViewById(R.id.container);

        listBooksPageAdapter = new ListBooksPageAdapter(getSupportFragmentManager(),1);
        viewPager.setAdapter(listBooksPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_save_activity);
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
            case android.R.id.home :
                finish();
                break;
            case R.id.menu_activity_save :
                BooksListActivity.navigate(this);
                break;
        }
        return true;
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, MySaveBooksActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
