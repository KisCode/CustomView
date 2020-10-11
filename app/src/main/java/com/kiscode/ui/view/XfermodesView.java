package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/10 21:07
 */

public class XfermodesView extends View {
    private static final String TAG = "XfermodesView";
    private Paint mPaint;
    private final int columeSize = 4;
    private final int paddingSize = 16;
    private int mItemSize, mWidth, mHeight;
    private static final PorterDuff.Mode[] sModes = {
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.SRC,
            PorterDuff.Mode.DST,
            PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.DST_OVER,

            //取两层交集，显示上层 SRC
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.XOR,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.LIGHTEN,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.OVERLAY
    };

    public XfermodesView(Context context) {
        this(context, null, 0);
    }

    public XfermodesView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(32f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mItemSize = Math.min(mWidth, mHeight) / columeSize;
        Log.i(TAG, "onMeasure:" + mItemSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Log.i("onDraw", "onDraw");

        //禁用硬件加速 禁用后反复回调onDraw
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
        //离屏绘制
        int layerCount = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint);

        int left = 0;
        int top = 0;
        Log.i(TAG, "mItemSize=" + mItemSize);
        for (int i = 0; i < sModes.length; i++) {
            if (i % columeSize == 0 && i / columeSize > 0) {
                top = i / columeSize * mItemSize;
            }
            left = i % columeSize * mItemSize;
            Log.i(TAG, "i=" + i + "\tleft=" + left + "\ttop=" + top);

            mPaint.setXfermode(null);

            //绘制边框
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(1f);
            mPaint.setColor(Color.LTGRAY);
            canvas.drawRect(left, top, left + mItemSize, top + mItemSize, mPaint);

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.BLACK);

            //绘制文字
            canvas.drawText(sModes[i].name(), left + 10, top , mPaint);
//            canvas.translate(left, top);
            //绘制DST
            canvas.drawBitmap(createRectBitmap(), left, top, mPaint);
//            canvas.drawBitmap(makeDst(mItemSize, mItemSize), 0, 0, mPaint);
            //设置混合模式
            mPaint.setXfermode(new PorterDuffXfermode(sModes[i]));
            //绘制SRC
            canvas.drawBitmap(createCircleBitmap(), left, top, mPaint);
//            canvas.drawBitmap(makeSrc(mItemSize, mItemSize), 0, 0, mPaint);
        }
        //清除混合模式
        mPaint.setXfermode(null);
        //绘制到屏幕上
        canvas.restoreToCount(layerCount);

    }

    //创建矩形的Bitmap对象 DST
    private Bitmap createRectBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mItemSize, mItemSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(mItemSize / 3 + paddingSize, mItemSize / 3 + paddingSize, mItemSize - paddingSize, mItemSize - paddingSize);
        canvas.drawRect(rect, paint);
        return bitmap;
    }

    //创建圆形的Bitmap对象 SRC
    private Bitmap createCircleBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mItemSize, mItemSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mItemSize / 3 + paddingSize, mItemSize / 3 + paddingSize, mItemSize / 3, paint);
        return bitmap;
    }
}
