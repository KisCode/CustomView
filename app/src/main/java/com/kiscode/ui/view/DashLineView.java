package com.kiscode.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/**
 * Description:
 * Author: keno
 * Date : 2021/5/6 11:38
 **/
public class DashLineView extends View {
    private static final String TAG = "DashLineView";
    private static final int DEFALUT_STOKE_SIZE = 1;
    private static final int DEFALUT_CIRCULE_RADIUS = 10;
    private Paint mDashPaint, mCirclePaint;

    private float mStrokWidth, mCircleRadius, mLinePadding;
    private int mInternal, mDistance;
    private float mDashStartX, mDashEndX, mDashY;
    private @ColorInt
    int mStorkColor, mCirculeColor;

    public DashLineView(Context context) {
        this(context, null);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashLineView, defStyleAttr, 0);
        if (typedArray != null) {
            mStrokWidth = typedArray.getDimensionPixelOffset(R.styleable.DashLineView_dash_stroke_width, DEFALUT_STOKE_SIZE);
            mCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.DashLineView_dash_circle_radius, DEFALUT_CIRCULE_RADIUS);
            mLinePadding = typedArray.getDimensionPixelOffset(R.styleable.DashLineView_dash_stroke_padding, 0);
            mInternal = typedArray.getInt(R.styleable.DashLineView_dash_stroke_internal, 20);
            mDistance = typedArray.getInt(R.styleable.DashLineView_dash_stroke_distance, 10);
            mStorkColor = typedArray.getColor(R.styleable.DashLineView_dash_stroke_color, Color.BLACK);
            mCirculeColor = typedArray.getColor(R.styleable.DashLineView_dash_circle_color, Color.BLACK);
        }
        init();
    }


    private void init() {
        mDashPaint = new Paint();
        mDashPaint.reset();
        mDashPaint.setAntiAlias(true);
        mDashPaint.setStyle(Paint.Style.STROKE);
        mDashPaint.setStrokeWidth(mStrokWidth);
        mDashPaint.setColor(mStorkColor);
        Log.i(TAG, mDistance + "\t" + mInternal);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePaint.setColor(mCirculeColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, mCirclePaint);

        //绘制左半圆
        canvas.drawCircle(0, mCircleRadius, mCircleRadius, mCirclePaint);

        //绘制虚线

        //绘制右半圆
        canvas.drawCircle(getWidth(), mCircleRadius, mCircleRadius, mCirclePaint);

        PathEffect pathEffect = new DashPathEffect(new float[]{mInternal, mDistance}, 1);
        mDashPaint.setPathEffect(pathEffect);
        Path path = new Path();
        path.moveTo(mDashStartX, mDashY);
        path.lineTo(mDashEndX, mDashY);
        canvas.drawPath(path, mDashPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, widthSize + "\t" + heightSize);
        mDashStartX = mCircleRadius + mLinePadding;
        mDashEndX = widthSize - mCircleRadius - mLinePadding;
        mDashY = (heightSize - mStrokWidth) / 2;
    }
}