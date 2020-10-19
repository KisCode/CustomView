package com.kiscode.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPaint, btnCanvans,btnPath, btnSplash, btnDrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnPaint = findViewById(R.id.btn_paint);
        btnCanvans = findViewById(R.id.btn_canvas);
        btnSplash = findViewById(R.id.btn_splash);
        btnDrag = findViewById(R.id.btn_drag);
        btnPath = findViewById(R.id.btn_path);

        btnPaint.setOnClickListener(this);
        btnCanvans.setOnClickListener(this);
        btnSplash.setOnClickListener(this);
        btnDrag.setOnClickListener(this);
        btnPath.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_paint:
                PaintDemoActivity.start(this);
                break;
            case R.id.btn_canvas:
                CanvasDemoActivity.start(this);
//                TabActivity.start(this);
                break;
            case R.id.btn_splash:
                SplashActivity.start(this);
                break;
            case R.id.btn_drag:
                DragBadgeActivity.start(this);
                break;
            case R.id.btn_path:
                PathDemoActivity.start(this);
                break;
        }
    }
}