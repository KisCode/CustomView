package com.kiscode.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DragBadgeActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, DragBadgeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_badge);
    }
}