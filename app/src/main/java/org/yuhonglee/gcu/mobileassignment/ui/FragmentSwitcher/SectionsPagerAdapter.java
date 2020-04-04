package org.yuhonglee.gcu.mobileassignment.ui.FragmentSwitcher;
//Name.Yu Hong Lee
//StD No.S1620580

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.yuhonglee.gcu.mobileassignment.Fragments.Frag1;
import org.yuhonglee.gcu.mobileassignment.Fragments.Frag2;
import org.yuhonglee.gcu.mobileassignment.Fragments.Frag3;
import org.yuhonglee.gcu.mobileassignment.Fragments.Frag4;
import org.yuhonglee.gcu.mobileassignment.R;

//where I set the tabs of the fragments
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Frag1();
                break;
            case 1:
                fragment = new Frag2();
                break;
            case 2:
                fragment = new Frag3();
                break;
            case 3:
                fragment = new Frag4();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}