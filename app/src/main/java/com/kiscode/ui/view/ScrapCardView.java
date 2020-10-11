package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/****
 * Description: 刮刮卡
 * Author:  keno
 * CreateDate: 2020/10/11 17:15
 */
public class ScrapCardView extends View {
    private Paint mPaint;
    private Bitmap mDstBitmap, mSrcBitmap;
    private int mWidth, mHeight;
    private int mBitmapWidth, mBitmapHeight;

    public ScrapCardView(Context context) {
        this(context, null, 0);
    }

    public ScrapCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrapCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL);

        //初始化图片
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.scraping_card);
        mSrcBitmap = bitmapDrawable.getBitmap();
        mBitmapWidth = mSrcBitmap.getWidth();
        mBitmapHeight = mSrcBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //二次采样
        /*
       mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scraping_card, options);
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;
        mBitmapWidth = options.outWidth;
        mBitmapHeight = options.outHeight;

        Log.i("onMeasure", bitmapHeight + "-" + bitmapWidth + "\t" + mWidth + "-" + mHeight);
        //使用屏幕和图片的宽高  可以计算一个图片的显示大小
        int scaleX = bitmapWidth / mWidth;
        int scaleY = bitmapHeight / mHeight;
        if (scaleX >= scaleY && scaleX >= 1) {
            options.inSampleSize = scaleX * 2;
        }
        if (scaleY >= scaleX && scaleY >= 1) {
            options.inSampleSize = scaleY * 2;
        }

//        options.inSampleSize = 2;

        options.inJustDecodeBounds = false;
        mDstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scraping_card, options);*/

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("onSizeChanged", "onSizeChanged,w = " + w + ",h = " + h + ",mBitWidth = " + mBitmapWidth + ",mBitHeight = " + mBitmapHeight);
        mWidth = w;
        mHeight = h;
        float scaleX = (float) mBitmapWidth / mWidth;
        float scaleY = (float) mBitmapHeight / mHeight;
        float scacle = 1;
        if (scaleX >= 1 && scaleX >= scaleY) {
            scacle = 1 / scaleX;
        } else if (scaleY >= 1 && scaleY >= scaleX) {
            scacle = 1 / scaleY;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scacle, scacle);
        mDstBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mBitmapWidth, mBitmapHeight, matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("onDraw", mBitmapWidth + "-" + mBitmapHeight + "\tcanvas:" + canvas);
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
