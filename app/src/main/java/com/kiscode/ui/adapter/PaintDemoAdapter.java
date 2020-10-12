package com.kiscode.ui.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kiscode.ui.fragment.RectFragment;
import com.kiscode.ui.fragment.XfermodeFragment;
import com.kiscode.ui.fragment.XfermodesFragment;
import com.kiscode.ui.pojo.TabItem;


/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/12 23:05
 */
public class PaintDemoAdapter extends FragmentPagerAdapter {

    private static final TabItem[] TAB_ITEMS = new TabItem[]{
            new TabItem("XfermodeMode All", XfermodesFragment.class.getName()),
            new TabItem("XfermodeMode", XfermodeFragment.class.getName()),
            new TabItem("Rect2", RectFragment.class.getName()),
            new TabItem("Rect", RectFragment.class.getName()),
            new TabItem("Rect6", RectFragment.class.getName()),
            new TabItem("Rect7", RectFragment.class.getName()),
            new TabItem("Circle", RectFragment.class.getName())
    };
    private FragmentManager mFm;

    public PaintDemoAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mFm = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String className = TAB_ITEMS[position].getClassName();
        return mFm.getFragmentFactory().instantiate(getClass().getClassLoader(), className);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_ITEMS[position].getTitle();
    }

    @Override
    public int getCount() {
        return TAB_ITEMS.length;
    }
}
