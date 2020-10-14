package com.kiscode.ui.view.canvas;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/14 22:49
 */

public class CanvasDemoView extends View {
    private Paint mPaint;

    public CanvasDemoView(Context context) {
        this(context, null, 0);
    }

    public CanvasDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_baidu);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Log.i("onDraw", bitmapHeight + "-" + bitmapWidth);

  /*      //裁剪区域
        canvas.save();
        canvas.clipRect(0, 0, bitmapWidth / 2, bitmapHeight / 2);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        canvas.drawRect(bitmapWidth / 2, bitmapHeight / 2, bitmapWidth,bitmapHeight, mPaint);
        */

/*
        //画布拉伸
        canvas.save();
//        canvas.scale(2,2);
        canvas.scale(2,2,bitmapWidth / 2, bitmapHeight / 2);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        canvas.drawRect(0, 0, bitmapWidth,bitmapHeight, mPaint);
*/

/*
        //画布平移
        canvas.save();
        canvas.translate(100,200);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
*/

/*
        //画布旋转
        canvas.save();
        //从原点坐标旋转
//        canvas.rotate(45);
        //从指定中心点旋转
        canvas.rotate(45, 100, 100);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        */

        //画布
        canvas.save();
        //x轴，y轴
        canvas.skew(0.5f,0);
//        canvas.rotate(45, 100, 100);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

    }
}
