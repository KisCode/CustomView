package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/****
 * Description: 展示图层叠加的18种PorterDuff.Mode
 * DST 图层为 下层方形
 * SRC 图层为 上层圆形
 * Author:  keno
 * CreateDate: 2020/10/10 21:07
 */
public class XfermodesView extends View {
    private static final String TAG = "XfermodesView";
    private Paint mPaint;
    private final int columeSize = 4;
    private final int textSizeHeight = 32;
    private final int paddingSize = 8;
    private int mItemSize, mWidth, mHeight;
    private static final PorterDuff.Mode[] sModes = {
            //绘制不会提交到画布
            PorterDuff.Mode.CLEAR,

            //显示上层绘制图层
            PorterDuff.Mode.SRC,

            //显示下层绘制图层
            PorterDuff.Mode.DST,

            //显示上下层叠加，上层居上
            PorterDuff.Mode.SRC_OVER,

            //显示上下层叠加，下层居上
            PorterDuff.Mode.DST_OVER,

            //取两层交集，显示上层 SRC
            PorterDuff.Mode.SRC_IN,

            //去两层交集，显示下层DST
            PorterDuff.Mode.DST_IN,

            //显示上层非交集部分
            PorterDuff.Mode.SRC_OUT,

            //显示下层非交集部分
            PorterDuff.Mode.DST_OUT,

            //显示上层交集 与 下层非交集部分
            PorterDuff.Mode.SRC_ATOP,

            //显示下层交集 与 上层非交集部分
            PorterDuff.Mode.DST_ATOP,

            //去除交集部分（交集处透明）
            PorterDuff.Mode.XOR,

            //交集处深色显示
            PorterDuff.Mode.DARKEN,

            //交集处浅色显示
            PorterDuff.Mode.LIGHTEN,

            //颜色叠加
            PorterDuff.Mode.MULTIPLY,

            //交集部分过滤色
            PorterDuff.Mode.SCREEN,

            //交集部分饱和度相加
            PorterDuff.Mode.ADD,

            //交集部分叠加
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

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        int infoHeight = mItemSize;
        canvas.drawBitmap(createRectBitmap(), 0, 0, mPaint);
        canvas.drawText("DST", infoHeight / 2, infoHeight / 2, mPaint);

        canvas.drawBitmap(createCircleBitmap(), mItemSize, 0, mPaint);
        canvas.drawText("SRC", mItemSize + infoHeight / 2, infoHeight / 2, mPaint);


        int left = 0;
        int top = paddingSize;
        Log.i(TAG, "mItemSize=" + mItemSize);
        for (int i = 0; i < sModes.length; i++) {
            if (i % columeSize == 0) {
                //Item高度 + 文本高度
                top = i / columeSize * mItemSize + i / columeSize * textSizeHeight + infoHeight;
            }


            left = i % columeSize * mItemSize;
            Log.i(TAG, "i=" + i + "\tleft=" + left + "\ttop=" + top);

            mPaint.setXfermode(null);

            //绘制边框
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(1f);
            mPaint.setColor(Color.LTGRAY);
            canvas.drawRect(left, top, left + mItemSize, top + mItemSize + textSizeHeight, mPaint);

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.BLACK);

            //绘制文字
            canvas.drawText(sModes[i].name(), left + paddingSize, top + mItemSize + textSizeHeight / 2, mPaint);

//            canvas.translate(left, top);
            //绘制DST
            canvas.drawBitmap(createRectBitmap(), left, top, mPaint);
            //设置混合模式
            mPaint.setXfermode(new PorterDuffXfermode(sModes[i]));
            //绘制SRC
            canvas.drawBitmap(createCircleBitmap(), left, top, mPaint);

        }
        //清除混合模式
        mPaint.setXfermode(null);

        //绘制到屏幕上
        canvas.restoreToCount(layerCount);
    }

    //创建矩形的Bitmap对象 DST
    private Bitmap createRectBitmap() {
        int bitmapSize = mItemSize - paddingSize * 2;
        Bitmap bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.YELLOW);
        paint.setColor(0xFF66AAFF);
        Rect rect = new Rect(bitmapSize / 3, bitmapSize / 3, bitmapSize, bitmapSize);
        canvas.drawRect(rect, paint);
        return bitmap;
    }

    //创建圆形的Bitmap对象 SRC
    private Bitmap createCircleBitmap() {
        int bitmapSize = mItemSize - paddingSize * 2;
        Bitmap bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.BLUE);
        paint.setColor(0xFFFFCC44);
        canvas.drawCircle(bitmapSize / 3 + paddingSize, bitmapSize / 3 + paddingSize, bitmapSize / 3, paint);
        return bitmap;
    }
}
