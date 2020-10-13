package com.kiscode.ui.view.paint;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;

import com.kiscode.ui.R;

/****
 * Description:  扫描渐变
 * Author:  keno
 * CreateDate: 2020/10/13 19:52
 */

public class BitmapShaderView extends BaseView {
    private Bitmap mBitmap;

    public BitmapShaderView(Context context) {
        super(context);

    }

    @Override
    protected void init() {
        super.init();
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);

        //缩放图片
        Matrix matrix = new Matrix();
        matrix.setScale(0.2f, 0.2f);
        mBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Shader.TileMode有三个：
        //CLAMP 重复最后一个颜色至最后
        //MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
        //REPEAT 重复着色的图像水平或垂直方向

        //图片渐变效果 BitmapShader  BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
        Shader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        mPaint.setShader(bitmapShader);
        canvas.drawRect(0, 0, 900, 400, mPaint);


        //Paint设置了bitmapShader后绘制drawCircle 实现圆形图片
        canvas.drawCircle(300,720,200,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(100);

        //Paint设置了bitmapShader后绘制drawCircle 实现环形图片
        canvas.drawCircle(800,720,200,mPaint);
    }
}
