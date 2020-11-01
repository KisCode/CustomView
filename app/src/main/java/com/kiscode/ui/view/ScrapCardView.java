package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/****
 * Description: 刮刮卡
 * 实现思路：
 * 1. 绘制刮开图片作为底图
 * 2. 离屏绘制手指滑动区域 + 前景图 使用PorterDuff.Mode.SRC_OUT 显示上层非交集区域
 mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
 *
 * Author:  keno
 * CreateDate: 2020/10/11 17:15
 */
public class ScrapCardView extends View {
    private Paint mPaint;
    private Path mPath;
    private Bitmap mResultBitmap, mForgroundBitmap, mPathBitmap;
    private float mEventX, mEventY;
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
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(100);
        //设置圆形线帽
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //初始化图片
        mResultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.card_result);
        mForgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scraping_card);
        mBitmapWidth = mResultBitmap.getWidth();
        mBitmapHeight = mResultBitmap.getHeight();

        mPath = new Path();
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
        float scaleX = (float) mBitmapWidth / w;
        float scaleY = (float) mBitmapHeight / h;
        float scacle = 1;
        if (scaleX >= 1 && scaleX >= scaleY) {
            scacle = 1 / scaleX;
        } else if (scaleY >= 1 && scaleY >= scaleX) {
            scacle = 1 / scaleY;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scacle, scacle);
        //图片进行缩放
        mResultBitmap = Bitmap.createBitmap(mResultBitmap, 0, 0, mBitmapWidth, mBitmapHeight, matrix, true);
        mForgroundBitmap = Bitmap.createBitmap(mForgroundBitmap, 0, 0, mBitmapWidth, mBitmapHeight, matrix, true);
        //路径图片
        mPathBitmap = Bitmap.createBitmap(mResultBitmap.getWidth(), mResultBitmap.getHeight(), Bitmap.Config.ARGB_8888);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("onDraw", mBitmapWidth + "-" + mBitmapHeight);

        //绘制彩票刮开后显示结果图片
        canvas.drawBitmap(mResultBitmap, 0, 0, mPaint);
        //开启离屏绘制
        int layerId = canvas.saveLayer(0, 0, mResultBitmap.getWidth(), mResultBitmap.getHeight(), mPaint);

        //先绘制路径
        Canvas dstCanvas = new Canvas(mPathBitmap);
        dstCanvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(mPathBitmap, 0, 0, mPaint);
        // 显示上层非交集区域
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mForgroundBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEventX = event.getX();
                mEventY = event.getY();
                mPath.moveTo(mEventX, mEventY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (event.getX() - mEventX) / 2 + mEventX;
                float endY = (event.getY() - mEventY) / 2 + mEventY;
                //画二阶贝塞尔曲线
                mPath.quadTo(mEventX, mEventY, endX, endY);
                mEventX = event.getX();
                mEventY = event.getY();
                break;
        }
        invalidate();
        return true;

    }
}
