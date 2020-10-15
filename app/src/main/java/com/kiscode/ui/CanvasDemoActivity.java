package com.kiscode.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.kiscode.ui.adapter.PaintDemoAdapter;
import com.kiscode.ui.pojo.TabItem;
import com.kiscode.ui.view.ParticleView;
import com.kiscode.ui.view.XfermodesView;
import com.kiscode.ui.view.canvas.CanvasDemoView;
import com.kiscode.ui.view.paint.BitmapShaderView;
import com.kiscode.ui.view.paint.BlurMaskFilterView;
import com.kiscode.ui.view.paint.EmbossMaskFilterView;
import com.kiscode.ui.view.paint.LinearGradientShaderView;
import com.kiscode.ui.view.paint.RadialGradientShaderView;
import com.kiscode.ui.view.paint.SweepGradientShaderView;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class CanvasDemoActivity extends AppCompatActivity {

    private static final TabItem[] TAB_ITEMS = new TabItem[]{
            new TabItem("Canvas", CanvasDemoView.class.getName()),
            new TabItem("粒子特效", ParticleView.class.getName())
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, CanvasDemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_demo);
        setTitle("CanvasDemoActivity");

        ViewPager viewPager = findViewById(R.id.viewpager_canvas_demo);

        PaintDemoAdapter adapter = new PaintDemoAdapter(getSupportFragmentManager()
                , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                , TAB_ITEMS);
        viewPager.setAdapter(adapter);
        TabLayout tabs = findViewById(R.id.tablayout_canvas_demo);
        tabs.setupWithViewPager(viewPager);
    }
}