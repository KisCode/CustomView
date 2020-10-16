package com.kiscode.ui.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;
import com.kiscode.ui.pojo.ParticleBall;

import java.util.ArrayList;
import java.util.List;

/****
 * Description: 粒子特效控件
 * Author:  keno
 * CreateDate: 2020/10/15 22:12
 */
public class ParticleView extends View {
    //设定粒子像素为16
    private static final float SIZE_BALL = 16;
    private Paint mPaint;
    private Bitmap mBitmap;
    private ValueAnimator mAnimator;
    private List<ParticleBall> mBalls = new ArrayList<>();

    public ParticleView(Context context) {
        this(context, null, 0);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_baidu);

        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        for (int i = 0; i < bitmapWidth; i += SIZE_BALL) {
            for (int j = 0; j < bitmapHeight; j += SIZE_BALL) {
                ParticleBall ball = new ParticleBall();
                //返回指定像素点的颜色
                ball.color = mBitmap.getPixel(i, j);
                ball.r = SIZE_BALL / 2;
//                ball.x = i * SIZE_BALL + ball.r;
//                ball.y = j * SIZE_BALL + ball.r;
                ball.x = i;
                ball.y = j;

                // 速度(-20,20)
                //Math.pow(x,y)  x的y次幂
                //Math.ceil 向上取整
                ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vY = rangeInt(-5, 25);
                ball.aX = 0;
                ball.aY = 0.98f;
                mBalls.add(ball);
            }
        }

        mAnimator = ValueAnimator.ofFloat(0, 1); //从0到1秒开始变化
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });

    }

    private void updateBall() {
        //更新粒子位置
        for (ParticleBall ball : mBalls) {
            ball.x += ball.vX;
            ball.y += ball.vY;

            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }

    }

    private int rangeInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在 0到（max-min）范围内变化
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //平移
        canvas.translate(100, 100);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 100, 100, mPaint);
            mBitmap.recycle();
            mBitmap = null;
        } else {
            for (ParticleBall ball : mBalls) {
                mPaint.setColor(ball.color);
                canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //执行动画
            mAnimator.start();
        }
        return super.onTouchEvent(event);
    }
}
