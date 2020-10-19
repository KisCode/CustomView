package com.kiscode.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kiscode.ui.view.PathView;


public class PathDemoActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, PathDemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathView(this));
    }
}