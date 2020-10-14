package com.kiscode.ui.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kiscode.ui.fragment.PaintSampleFragment;
import com.kiscode.ui.pojo.TabItem;


/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/12 23:05
 */
public class PaintDemoAdapter extends FragmentPagerAdapter {
    private TabItem[] mItemArr;
    private FragmentManager mFm;

    public PaintDemoAdapter(@NonNull FragmentManager fm, int behavior, com.kiscode.ui.pojo.TabItem[] tabItem) {
        super(fm, behavior);
        mItemArr = tabItem;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String className = mItemArr[position].getViewClasName();
//        return mFm.getFragmentFactory().instantiate(getClass().getClassLoader(), className);
        return PaintSampleFragment.newInstance(className);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mItemArr[position].getTitle();
    }

    @Override
    public int getCount() {
        return mItemArr.length;
    }
}
