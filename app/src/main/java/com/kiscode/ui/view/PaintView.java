package com.kiscode.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/5 10:10
 */

public class PaintView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    public PaintView(Context context) {
        this(context, null, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置画笔颜色值
//        mPaint.setColor(Color.RED);
        //设置画笔颜色
        mPaint.setARGB(0, 0, 0, 255);
        //设置透明度 0~255
        mPaint.setAlpha(255);
        //设置画笔宽度
        mPaint.setStrokeWidth(15f);

        //设置是否抗锯齿
        mPaint.setAntiAlias(true);

        //线框
//        mPaint.setStyle(Paint.Style.STROKE);

        //设置线帽
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        Paint.Cap.BUTT 没有
//        Paint.Cap.ROUND 圆形
//        Paint.Cap.SQUARE 圆角

//        mPaint.setStrokeJoin(Paint.Join.MITER);
//        Join.MITER（结合处为锐角）
//        Join.Round(结合处为圆弧)
//        Join.BEVEL(结合处为直线)

/*
        //设置线帽
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(10, 20, 200, 20, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(10, 50, 200, 50, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(10, 80, 200, 80, mPaint);
//        canvas.drawRect(100, 100, 250, 250, mPaint);

        mPaint.setStrokeWidth(25);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawRect(100, 200, 400, 400, mPaint);*/


//绘制文本
//        mPaint.setTextSize(230);
//        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        canvas.drawText("Text xyz", 0, 230, mPaint);


        // setColorFilter 设置颜色过滤器
        //设置颜色过滤，ColorFilter有三个子类去实现ColorMatrixColorFilter、LightingColorFilter和PorterDuffColorFilter

        //ColorMatrixColorFilter 色彩矩阵颜色过滤器
        //其中，第一行表示的R（红色）的向量，
        //第二行表示的G（绿色）的向量，
        //第三行表示的B（蓝色）的向量，
        //最后一行表示A（透明度）的向量，
        //这个矩阵不同的位置表示的RGBA值，
        //其范围在0.0F至2.0F之间，1为保持原图的RGB值。
        //每一行的第五列数字表示偏移值，何为偏移值？
        //顾名思义当我们想让颜色更倾向于红色的时候就增大R向量中的偏移值，
        //想让颜色更倾向于蓝色的时候就增大B向量中的偏移值
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0.5f, 0, 0, 0, 0,
//                0, 1, 0, 0, 0,
//                0, 0, 1, 0, 0,
//                0, 0, 0, 1, 0,
//        });
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

//       LightingColorFilter https://developer.android.com/reference/android/graphics/LightingColorFilter
        // 设置颜色过滤,去掉蓝色 ARGB
//        参数1：mul全称是colorMultiply意为色彩倍增；参数2：add全称是colorAdd意为色彩增量
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFFFF00, 0x00AA0000));

/*
 // 设置颜色过滤,Color的值设为红色，模式PorterDuff.Mode.LIGHTEN变亮
        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN));

        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setDither(true);
        //设置图片滤波处理，
        mPaint.setFilterBitmap(true);
//        //绘制图片
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        */

/*
        //tileX、tileY参数Shader.TileMode有三个：
        //CLAMP 重复最后一个颜色至最后
        //MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
        //REPEAT 重复着色的图像水平或垂直方向
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        mPaint.setShader(bitmapShader);
        canvas.drawCircle(500,500,500,mPaint);*/

/*
        //线性渐变效果 LinearGradient 从(x0,y0) 到 (x1,y1)渐变
        Shader shader =new LinearGradient(0,0,200,200, Color.RED,Color.GREEN, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
//        canvas.drawRect(0,0,400,400,mPaint);
        canvas.drawCircle(100,100,100,mPaint);
        */

/*
        //RadialGrdient 设置光束从中心向四周发散的辐射渐变效果
//        Shader shader = new RadialGradient(100, 100, 100, Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        Shader shader = new RadialGradient(100, 100, 100, Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
        canvas.drawCircle(100,100,100,mPaint);
        */

        //SweepGradient 设置绕着某中心点进行360度旋转渐变效果
//        Shader shader = new SweepGradient(100, 100, Color.RED, Color.GREEN);
        //positions 取值范围为 0~1： 0和1都表示3点钟位置，0.25表示6点钟位置，0.5表示9点钟位置
        Shader shader = new SweepGradient(100, 100, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, new float[]{0, 0.25f, 0.5f, 1f});
        mPaint.setShader(shader);
        canvas.drawCircle(100, 100, 100, mPaint);


    }
}
