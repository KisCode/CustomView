package com.kiscode.ui.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/****
 * Description: 引导页动画效果
 *  1. 旋转 6个不同颜色的小球
 *  2. 聚合缩放
 *  3. 水波纹效果
 * Author:  keno
 * CreateDate: 2020/10/17 9:28
 */

public class SplashView extends View {
    //旋转小球个体半径
    private static final int mCircelRadius = 12;
    private static final String TAG = "SplashView";
    //旋转半径
    private float mRotateRadius = 48;

    //动画时长
    private final int mDuration = 1500;

    private SplashState mState;
    //屏幕斜边1/2
    private float mDistance;
    private Paint mPaint;
    private ValueAnimator mAnimator;
    //当前进度小球旋转角度
    private float mCurrentRotateAngel;
    //当前水波纹扩散圆形半径
    private float mCurrentHoleReRadius;
    //旋转小球颜色组
    private int[] mColorArr;
    //屏幕中心坐标
    private int mCenterX, mCenterY;

    public SplashView(Context context) {
        this(context, null, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mColorArr = getResources().getIntArray(R.array.progress_colors);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2 + 1;
        mCenterY = h / 2 + 1;

        //根据屏幕宽高计算屏幕对角线长度的1/2
        mDistance = (float) (Math.hypot(w, h) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == null) {
            mState = new RotateSplashState();
        }
        mState.drawState(canvas);
    }

    private void drawCirle(Canvas canvas) {
        Log.i(TAG,"drawCirle");
        //两个小球的间距角度
        float rotateAngel = (float) (Math.PI * 2 / mColorArr.length);
        for (int i = 0; i < mColorArr.length; i++) {
            // x = r * cos(a) + centerX
            // y = r* sin(a) + centerY
            float angel = rotateAngel * i + mCurrentRotateAngel;
            float cx = (float) (Math.cos(angel) * mRotateRadius + mCenterX);
            float cy = (float) (Math.sin(angel) * mRotateRadius + mCenterY);

            mPaint.setColor(mColorArr[i]);
            canvas.drawCircle(cx, cy, mCircelRadius, mPaint);
        }
    }

    private void drawBackgroud(Canvas canvas) {
        Log.i(TAG,"drawBackgroud");
        if (mCurrentHoleReRadius > 0) {
            //绘制空心圆
            float strokWidth = mDistance - mCurrentHoleReRadius;
            float realRadius = strokWidth / 2 + mCurrentHoleReRadius;
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokWidth);
            canvas.drawCircle(mCenterX, mCenterY, realRadius, mPaint);
        } else {
            canvas.drawColor(Color.WHITE);
        }
    }

    private static abstract class SplashState {
        abstract void drawState(Canvas canvas);
    }

    //1.旋转 小球
    private class RotateSplashState extends SplashState {

        public RotateSplashState() {
            mAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            mAnimator.setRepeatCount(2);
            mAnimator.setDuration(mDuration);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateAngel = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new MerginState();
                }
            });

            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            //绘制背景
            drawBackgroud(canvas);
            //绘制进度小球
            drawCirle(canvas);
        }
    }

    //2.扩散 小球
    private class MerginState extends SplashState {

        public MerginState() {
            mAnimator = ValueAnimator.ofFloat(mCircelRadius, mRotateRadius);
            mAnimator.setDuration(mDuration);
            mAnimator.setInterpolator(new OvershootInterpolator(10f));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRotateRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            mAnimator.reverse();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackgroud(canvas);
            drawCirle(canvas);
        }
    }

    //3. 水波纹
    private class ExpandState extends SplashState {

        public ExpandState() {
            mAnimator = ValueAnimator.ofFloat(mCircelRadius, mDistance);
//            mAnimator.setRepeatCount(2);
            mAnimator.setDuration(mDuration);
            //OvershootInterpolator
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentHoleReRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackgroud(canvas);
        }
    }
}
