package com.kiscode.ui.view.paint;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/13 19:52
 */

public class RadialGradientShaderView extends BaseView {

    public RadialGradientShaderView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Shader.TileMode有三个：
        //CLAMP 重复最后一个颜色至最后
        //MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
        //REPEAT 重复着色的图像水平或垂直方向
        //线性渐变效果 RadialGradient
        Shader radialGraient = new RadialGradient(100, 100, 50, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(radialGraient);
        canvas.drawCircle(100, 100, 100, mPaint);
        canvas.drawText(Shader.TileMode.CLAMP.name(), 250, 100, mPaint);

        radialGraient = new RadialGradient(100, 400, 50, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        mPaint.setShader(radialGraient);
        canvas.drawCircle(100, 400, 100, mPaint);
        canvas.drawText(Shader.TileMode.MIRROR.name(), 250, 400, mPaint);

        radialGraient = new RadialGradient(100, 700, 50, Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        mPaint.setShader(radialGraient);
        canvas.drawCircle(100, 700, 100, mPaint);
        canvas.drawText(Shader.TileMode.REPEAT.name(), 250, 700, mPaint);
    }
}
