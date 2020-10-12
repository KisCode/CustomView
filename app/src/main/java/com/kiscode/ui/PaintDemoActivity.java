package com.kiscode.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kiscode.ui.adapter.PaintDemoAdapter;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class PaintDemoActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, PaintDemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_demo);
        setTitle("PaintDemoActivity");

        ViewPager viewPager = findViewById(R.id.viewpager_paint_demo);

        PaintDemoAdapter adapter = new PaintDemoAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tablayout_paint_demo);
        tabs.setupWithViewPager(viewPager);
    }
}