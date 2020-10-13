package com.kiscode.ui.view.paint;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/13 19:52
 */

public class LinearGradientShaderView extends BaseView {

    public LinearGradientShaderView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Shader.TileMode有三个：
        //CLAMP 重复最后一个颜色至最后
        //MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
        //REPEAT 重复着色的图像水平或垂直方向
        //线性渐变效果 LinearGradient 从(x0,y0) 到 (x1,y1)渐变
        Shader linearGradient = new LinearGradient(0, 0, 100, 100, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
//        linearGradient = new LinearGradient(0, 0, 200, 200, new int[]{Color.RED, Color.YELLOW, Color.BLUE}, new float[]{10, 50, 100}, Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0, 200, 200, mPaint);
        canvas.drawText(Shader.TileMode.MIRROR.name(), 0, 250, mPaint);

        linearGradient = new LinearGradient(250, 0, 350, 100, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawRect(250, 0, 450, 200, mPaint);
        canvas.drawText(Shader.TileMode.CLAMP.name(), 250, 250, mPaint);

        linearGradient = new LinearGradient(500, 0, 600, 100, Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        mPaint.setShader(linearGradient);
        canvas.drawRect(500, 0, 700, 200, mPaint);
        canvas.drawText(Shader.TileMode.REPEAT.name(), 500, 250, mPaint);


    }
}
