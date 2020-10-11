package com.kiscode.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description: 遍历枚举PorterDuff.Mode.values()中图层混合模式叠加
 * Author: keno
 * CreateDate: 2020/10/11 17:08
 */

public class PorterDuffView extends View {
    private final int COUNT_COLUM = 4;
    private Paint mPaint;
    private int mWith;
    private int mHeight;
    private int mItemSize;

    public PorterDuffView(Context context) {
        this(context, null, 0);
    }

    public PorterDuffView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PorterDuffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWith = getMeasuredWidth();
        mItemSize = Math.min(mHeight, mWith) / COUNT_COLUM;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);  //抗锯齿
    }

    private Bitmap rectBitmap() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF66AAFF);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(mItemSize, mItemSize, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawRect(new RectF(mItemSize / 3, mItemSize / 3, mItemSize, mItemSize), paint);
        return bm;
    }

    private Bitmap circleBitmap() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFCC44);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(mItemSize, mItemSize, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(mItemSize / 3, mItemSize / 3, mItemSize / 3, paint);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("onDraw", "onDraw");
        //离屏缓冲
        int saveCount = canvas.saveLayer(0, 0, mWith, mHeight, mPaint);
        int left = 0;
        int top = 0;
        //遍历PorterDuff.Mode枚举
//        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
        for (int i = 0; i < PorterDuff.Mode.values().length; i++) {
            if (i % COUNT_COLUM == 0) {
                //换行时 top增加
                top = i / COUNT_COLUM * mItemSize;
            }
            left = i % COUNT_COLUM * mItemSize;
            PorterDuff.Mode mode = PorterDuff.Mode.values()[i];

            mPaint.setXfermode(null);
            canvas.drawText(mode.name(), left, top + mItemSize / 2, mPaint);

            canvas.drawBitmap(rectBitmap(), left, top, mPaint);
            //设置图层叠加模式
            mPaint.setXfermode(new PorterDuffXfermode(mode));
            canvas.drawBitmap(circleBitmap(), left, top, mPaint);
        }
        //及时清除Xfermode
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(saveCount);
    }
}


