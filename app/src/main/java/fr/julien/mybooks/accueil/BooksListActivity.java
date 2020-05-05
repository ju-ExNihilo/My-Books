package fr.julien.mybooks.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.navigation.NavigationView;
import fr.julien.mybooks.R;
import fr.julien.mybooks.mysavebooks.MySaveBooksActivity;


public class BooksListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.showBooksListFragment();
        this.drawerLayout = findViewById(R.id.list_book_activity_drawer);
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        this.swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.configureNavigationView();
    }

    @Override
    public void onRefresh() {
        showBooksListFragment();
        new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 1000);
    }

    private void showBooksListFragment(){
        BooksListFragment booksListFragment = BooksListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.books_list_frame_layout, booksListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_activity_main_search :
                if (!drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.openDrawer(Gravity.RIGHT);
                else drawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);

        }else {super.onBackPressed();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.go_to_my_books:
                MySaveBooksActivity.navigate(this);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    private void configureNavigationView(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, BooksListActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
