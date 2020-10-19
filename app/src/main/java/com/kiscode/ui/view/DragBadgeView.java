package com.kiscode.ui.view;


import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

/****
 * Description: 可拖动的红色角标View
 *
 * 1. 静态 圆形背景和数值
 * 2. 拖动控件本身，固定圆形+贝塞尔曲线+圆形背景和数值
 * 3. 超出最大距离，跟随手指动 圆形背景和数值
 * 4. 手指释放，炸裂
 * Author:  keno
 * CreateDate: 2020/10/17 20:50
 */

public class DragBadgeView extends View {
    private static final String TAG = "DragBadgeView";
    private Paint mPaint;

    private final int STATE_DEFAULT = 10;
    private final int STATE_MOVE_CONECT = 20;
    private final int STATE_APART = 30;
    private final int STATE_DISSMISS = 40;

    private int mCurrentState = STATE_DEFAULT;
    private float mDragCenterX, mDragCenterY;
    private PointF mFixedCenter;
    private Path mPath;
    private float mTextSize = 48;
    private float mBadgeRadius, mFixedRadius;
    private float mMaxDragDistance;
    private float mDst;
    private String mBadgeText = "10";

    public DragBadgeView(Context context) {
        this(context, null, 0);
    }

    public DragBadgeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mFixedCenter == null) {
            mFixedCenter = new PointF(w / 2, h / 2);
        } else {
            mFixedCenter.set(w / 2, h / 2);
        }

        mDragCenterX = mFixedCenter.x;
        mDragCenterY = mFixedCenter.y;

        mBadgeRadius = 60;  //
        mFixedRadius = mBadgeRadius;
        mMaxDragDistance = mBadgeRadius * 8;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mCurrentState != STATE_DISSMISS) {
            mPaint.setColor(Color.RED);
            //绘制圆形背景 和 文字
            drawCircleAndText(canvas, mDragCenterX, mDragCenterY);
        }

        if (mCurrentState == STATE_MOVE_CONECT) {
            mPaint.setColor(Color.RED);
            //绘制 fixedCircle 固定的圆形
            canvas.drawCircle(mFixedCenter.x, mFixedCenter.y, mFixedRadius, mPaint);
//
//            //绘制线 贝塞尔曲线
            //控制点的坐标
            int iAnchorX = (int) ((mFixedCenter.x + mDragCenterX) / 2);
            int iAnchorY = (int) ((mFixedCenter.y + mDragCenterY) / 2);

            float sinTheta = (mDragCenterY - mFixedCenter.y) / mDst;
            float cosTheta = (mDragCenterX - mFixedCenter.x) / mDst;

            //B
            float iDragStartX = mDragCenterX + sinTheta * mBadgeRadius;
            float iDragStartY = mDragCenterY - cosTheta * mBadgeRadius;

            //A
            float iFixEndX = mFixedCenter.x + mFixedRadius * sinTheta;
            float iFixEndY = mFixedCenter.y - mFixedRadius * cosTheta;

            //C
            float iDragEndX = mDragCenterX - sinTheta * mBadgeRadius;
            float iDragEndY = mDragCenterY + cosTheta * mBadgeRadius;

            //D
            float iFixStartX = mFixedCenter.x - mFixedRadius * sinTheta;
            float iFixStartY = mFixedCenter.y + mFixedRadius * cosTheta;

            mPath.reset();
            mPath.moveTo(iFixStartX, iFixStartY);
            mPath.quadTo(iAnchorX, iAnchorY, iDragEndX, iDragEndY);

            mPath.lineTo(iDragStartX, iDragStartY);
            mPath.quadTo(iAnchorX, iAnchorY, iFixEndX, iFixEndY);
            mPath.close();
            canvas.drawPath(mPath, mPaint);

        }

        if (mCurrentState == STATE_APART) {

        }

        if (mCurrentState == STATE_DISSMISS) {
//            drawCircleAndText(canvas, mDragCenterX, mDragCenterY);
            Log.i(TAG, "手指释放，炸裂后消失");

        }


//
//        if (mCurrentState == STATE_DEFAULT) {
//
//            mPaint.setColor(Color.RED);
//            //绘制圆形背景 和 文字
//            drawCircleAndText(canvas, mFixedCenter.x, mFixedCenter.y);
//        } else if (mCurrentState == STATE_MOVE_CONECT) {
//            mPaint.setColor(Color.RED);
//            if (!isOverDist) {
//                //绘制 fixedCircle 固定的圆形
//                canvas.drawCircle(mFixedCenter.x, mFixedCenter.y, mFixedRadius, mPaint);
//                //绘制线 贝塞尔曲线
//                canvas.drawLine(mFixedCenter.x, mFixedCenter.y, mDragCenterX, mDragCenterY, mPaint);
//            }
//            drawCircleAndText(canvas, mDragCenterX, mDragCenterY);
//
//        } else if (mCurrentState == STATE_DISSMISS) {
//            //手指释放，炸裂后消失
//            drawCircleAndText(canvas, mDragCenterX, mDragCenterY);
//            Log.i(TAG, "手指释放，炸裂后消失");
//        }

// 1. 圆形背景+数值
// 2. 拖动控件本身，固定圆形+拉伸线+拖动圆形
// 3. 超出最大距离，跟随手指动
// 4. 手指释放，炸裂
    }

    private void drawCircleAndText(Canvas canvas, float mDragCenterX, float mDragCenterY) {
        //绘制圆形背景 和 文字
        canvas.drawCircle(mDragCenterX, mDragCenterY, mBadgeRadius, mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(mTextSize);
        Rect rect = new Rect();
        mPaint.getTextBounds(mBadgeText, 0, mBadgeText.length(), rect);
        canvas.drawText(mBadgeText, mDragCenterX - rect.width() / 2, mDragCenterY + rect.height() / 2, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentState == STATE_DEFAULT) {
                    mCurrentState = STATE_MOVE_CONECT;
                }

                mDragCenterX = event.getX();
                mDragCenterY = event.getY();
                mDst = (float) Math.hypot(mDragCenterX - mFixedCenter.x, mDragCenterY - mFixedCenter.y);
                if (mDst < mMaxDragDistance) {
                    mFixedRadius = (float) ((mMaxDragDistance - mDst) / mMaxDragDistance * mBadgeRadius);
                } else {
                    mCurrentState = STATE_APART;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentState == STATE_MOVE_CONECT) {
                    startBubbleRestAnim();
                    mCurrentState = STATE_DEFAULT;
                } else if (mCurrentState == STATE_APART) {
                    if (mDst < mMaxDragDistance) {
//                        mCurrentState = STATE_DEFAULT;
//                        mDragCenterX = mFixedCenter.x;
//                        mDragCenterY = mFixedCenter.y;
                        startBubbleRestAnim();
                        mCurrentState = STATE_DEFAULT;
                    } else {
                        mCurrentState = STATE_DISSMISS;
                        startBubbleBurstAnim();
                    }
                }

                invalidate();
                break;
        }

        return true;
    }

    //开启炸裂效果
    private void startBubbleBurstAnim() {
//        ValueAnimator animator = ValueAnimator.ofFloat(mBadgeRadius,0);
//        animator.setDuration(3000);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mBadgeRadius= (float) valueAnimator.getAnimatedValue();
//                invalidate();
//            }
//        });
//        animator.start();
    }

    //开启橡皮筋回弹效果
    private void startBubbleRestAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(mDragCenterX, mDragCenterY),
                new PointF(mFixedCenter.x, mFixedCenter.y)
        );
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator(5f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                mDragCenterX = pointF.x;
                mDragCenterY = pointF.y;
                invalidate();
            }
        });
        animator.start();
    }
}
