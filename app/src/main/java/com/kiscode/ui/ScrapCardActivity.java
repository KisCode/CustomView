package com.kiscode.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Description: 刮刮卡实例
 * Author: keno
 * CreateDate: 2020/10/11 17:13
 */
public class ScrapCardActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ScrapCardActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_card);
    }
}