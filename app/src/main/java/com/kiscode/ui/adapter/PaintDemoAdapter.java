package com.kiscode.ui.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kiscode.ui.fragment.PaintSampleFragment;
import com.kiscode.ui.pojo.TabItem;
import com.kiscode.ui.view.paint.BitmapShaderView;
import com.kiscode.ui.view.paint.LinearGradientShaderView;
import com.kiscode.ui.view.XfermodesView;
import com.kiscode.ui.view.paint.RadialGradientShaderView;
import com.kiscode.ui.view.paint.SweepGradientShaderView;


/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/12 23:05
 */
public class PaintDemoAdapter extends FragmentPagerAdapter {

    private static final TabItem[] TAB_ITEMS = new TabItem[]{
            new TabItem("XfermodeMode", XfermodesView.class.getName()),
            new TabItem("LinearGradient", LinearGradientShaderView.class.getName()),
            new TabItem("RadialGradient", RadialGradientShaderView.class.getName()),
            new TabItem("SweepGradient", SweepGradientShaderView.class.getName()),
            new TabItem("BitmapShader", BitmapShaderView.class.getName()),
            new TabItem("RadialGradient", RadialGradientShaderView.class.getName())
    };
    private FragmentManager mFm;

    public PaintDemoAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mFm = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String className = TAB_ITEMS[position].getViewClasName();
//        return mFm.getFragmentFactory().instantiate(getClass().getClassLoader(), className);
        return PaintSampleFragment.newInstance(className);
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
