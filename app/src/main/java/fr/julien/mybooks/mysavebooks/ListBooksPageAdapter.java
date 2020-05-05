package fr.julien.mybooks.mysavebooks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ListBooksPageAdapter extends FragmentPagerAdapter {
    public ListBooksPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyByListFragment.newInstance();
            case 1:
                return MyReadListFragment.newInstance();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
