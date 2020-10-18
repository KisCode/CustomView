package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/****
 * Description: 可拖动的红色角标View
 *
 * 1. 圆形背景+数值
 * 2. 拖动控件本身，固定圆形+拉伸线+拖动圆形
 * 3. 超出最大距离，跟随手指动
 * 4. 手指释放，炸裂
 * Author:  keno
 * CreateDate: 2020/10/17 20:50
 */

public class DragBadgeView extends View {
    private static final String TAG = "DragBadgeView";
    private Paint mPaint;

    private final int STATE_DEFAULT = 10;
    private final int STATE_MOVE = 30;
    private final int STATE_DISSMISS = 40;

    private int mCurrentState = STATE_DEFAULT;
    private float mDragCenterX, mDragCenterY;
    private PointF mFixedCenter;
    private float mTextSize = 48;
    private float mBadgeRadius, mFixedRadius;
    private float mMaxDragDistance;
    private String mBadgeText = "10";
    private boolean isOverDist;

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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mFixedCenter == null) {
            mFixedCenter = new PointF(w / 2, h / 2);
        } else {
            mFixedCenter.set(w / 2, h / 2);
        }
        mBadgeRadius = 60;  //
        mFixedRadius = mBadgeRadius;
        mMaxDragDistance = mBadgeRadius * 8;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCurrentState == STATE_DEFAULT) {

            mPaint.setColor(Color.RED);
            //绘制圆形背景 和 文字
            drawCircleAndText(canvas, mFixedCenter.x, mFixedCenter.y);
        } else if (mCurrentState == STATE_MOVE) {
            mPaint.setColor(Color.RED);
            if (!isOverDist) {
                //绘制 fixedCircle 固定的圆形
                canvas.drawCircle(mFixedCenter.x, mFixedCenter.y, mFixedRadius, mPaint);
                //绘制线 贝塞尔曲线
                canvas.drawLine(mFixedCenter.x, mFixedCenter.y, mDragCenterX, mDragCenterY, mPaint);
            }
            drawCircleAndText(canvas, mDragCenterX, mDragCenterY);

        } else if (mCurrentState == STATE_DISSMISS) {
            //手指释放，炸裂后消失
            drawCircleAndText(canvas, mDragCenterX, mDragCenterY);
            Log.i(TAG, "手指释放，炸裂后消失");
        }

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
                    mCurrentState = STATE_MOVE;
                }

                mDragCenterX = event.getX();
                mDragCenterY = event.getY();
                double dst = Math.hypot(mDragCenterX - mFixedCenter.x, mDragCenterY - mFixedCenter.y);
                if (dst < mMaxDragDistance) {
                    mFixedRadius = (float) ((mMaxDragDistance - dst) / mMaxDragDistance * mBadgeRadius);
                } else {
                    isOverDist = true;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                dst = Math.hypot(mDragCenterX - mFixedCenter.x, mDragCenterY - mFixedCenter.y);
                if (dst < mMaxDragDistance) {
                    mCurrentState = STATE_DEFAULT;
                } else {
                    mCurrentState = STATE_DISSMISS;
                }
                invalidate();
                break;
        }

        return true;
    }
}
