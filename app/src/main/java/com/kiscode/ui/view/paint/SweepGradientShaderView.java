package com.kiscode.ui.view.paint;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.SweepGradient;

/****
 * Description:  扫描渐变
 * Author:  keno
 * CreateDate: 2020/10/13 19:52
 */

public class SweepGradientShaderView extends BaseView {

    public SweepGradientShaderView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Shader.TileMode有三个：
        //CLAMP 重复最后一个颜色至最后
        //MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
        //REPEAT 重复着色的图像水平或垂直方向
        //扫描渐变效果 LinearGradient 从(x0,y0) 到 (x1,y1)渐变
        Shader sweepGradient = new SweepGradient(100, 100, Color.RED, Color.BLUE);
//        linearGradient = new LinearGradient(0, 0, 200, 200, new int[]{Color.RED, Color.YELLOW, Color.BLUE}, new float[]{10, 50, 100}, Shader.TileMode.MIRROR);
        mPaint.setShader(sweepGradient);
        canvas.drawCircle(100, 100, 100, mPaint);
//        canvas.drawText(Shader.TileMode.MIRROR.name(), 0, 250, mPaint);


    }
}
