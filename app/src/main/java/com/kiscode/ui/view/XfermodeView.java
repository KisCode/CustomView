package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/10 21:07
 */

public class XfermodeView extends View {
    private Paint mPaint;
    private int mWidth, mHeight;

    public XfermodeView(Context context) {
        this(context, null, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);

        //离屏绘制
        int layerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint);
        //绘制DST
        canvas.drawBitmap(createRectBitmap(mWidth, mHeight), 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        //绘制SRC
        canvas.drawBitmap(createCircleBitmap(mWidth, mHeight), 0, 0, mPaint);

        //清除混合模式
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerCount);
    }

    //创建矩形的Bitmap对象 DST
    private Bitmap createRectBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        Rect rect = new Rect(width / 20, height / 3, width * 2 / 3, height * 19 / 20);
        canvas.drawRect(rect, paint);
        return bitmap;
    }

    //创建圆形的Bitmap对象 SRC
    private Bitmap createCircleBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawCircle(width / 3, height / 3, width / 3, paint);
        return bitmap;
    }
}
