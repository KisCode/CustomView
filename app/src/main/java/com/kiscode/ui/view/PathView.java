package com.kiscode.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description: Path 绘制演示
 * Author: KrisKeno
 * Date : 2020/10/18 9:50 PM
 **/

public class PathView extends View {
    private Paint mPaint;
    private Path mPath;

    public PathView(Context context) {
        this(context, null, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mPath =new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPath.moveTo(10,20);
//        mPath.lineTo(300,400);
//        mPath.rLineTo(20,-100);
//        mPath.close();
        //相对上一个点的位置
//        mPath.rLineTo(10,100);


//        //二阶贝塞尔曲线
//        mPath.moveTo(0,0);
//        mPath.quadTo(30,200,300,300);

        //三阶贝塞尔曲线
        mPath.moveTo(10,10);
        //(x1,y1) (x2,y2)为控制点坐标，(x3,x4)为曲线尾端点坐标
        mPath.cubicTo(120,30,300,160,600,200);
        canvas.drawPath(mPath,mPaint);

    }
}
