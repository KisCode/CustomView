package com.kiscode.ui.view.canvas;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.kiscode.ui.R;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/14 22:49
 */

public class CanvasDemoView extends View {
    private Paint mPaint;

    public CanvasDemoView(Context context) {
        this(context, null, 0);
    }

    public CanvasDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_baidu);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Log.i("onDraw", bitmapHeight + "-" + bitmapWidth);

/*
        //1. 画布平移
        canvas.save();
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.translate(100, 200);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        */

/*
        //2. 画布旋转
        canvas.save();
        //从原点坐标旋转
//        canvas.rotate(45);
        //从指定中心点旋转
        canvas.rotate(45, 100, 100);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        */
/*
        //3. 画布拉伸
        canvas.save();
        //对画布进行拉伸
//        canvas.scale(2,2);
        canvas.scale(2,2,bitmapWidth / 2, bitmapHeight / 2);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        canvas.drawRect(0, 0, bitmapWidth,bitmapHeight, mPaint);
*/

  /*      //裁剪区域
        canvas.save();
        canvas.clipRect(0, 0, bitmapWidth / 2, bitmapHeight / 2);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
        canvas.drawRect(bitmapWidth / 2, bitmapHeight / 2, bitmapWidth,bitmapHeight, mPaint);
        */
//
/*          //反向裁剪
        canvas.save();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutRect(0, 0, bitmapWidth / 2, bitmapHeight / 2);
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();*/



/*
        canvas.save();
        //x轴，y轴 倾斜操作
        canvas.skew(0.5f,0);
//        canvas.rotate(45, 100, 100);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
*/

    /*    //矩阵
        Matrix matrix = new Matrix();
        //旋转
//        matrix.setTranslate(100,300);

        //旋转
//        matrix.setRotate(45);

        //拉伸
//        matrix.setScale(2f, 1.5f);

        //倾斜
//        matrix.setSkew(0.5f, 0);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);*/

/*
        //save和restor成对出现 ，save入栈,restore出栈
        int state = canvas.save();
        canvas.restore();
        canvas.restoreToCount(state); //返回到指定状态
        */

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(200, 200, 700, 700, mPaint);
        //离屏缓冲
        int layerId = canvas.saveLayer(0, 0, 700, 700, mPaint);
        canvas.translate(100, 100); // 平移
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, 700, 700, mPaint);
        canvas.restoreToCount(layerId); //画布恢复到指定状态
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 100, 100, mPaint);
    }
}
