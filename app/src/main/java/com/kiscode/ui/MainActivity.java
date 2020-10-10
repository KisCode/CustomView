package com.kiscode.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kiscode.ui.view.XfermodeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(new XfermodeView(this));
    }
}