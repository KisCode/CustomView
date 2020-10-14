package com.kiscode.ui.view.paint;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

import com.kiscode.ui.R;

/****
 * Description:  设置模糊效果
 * paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
 * Author:  keno
 * CreateDate: 2020/10/13 19:52
 */
public class BlurMaskFilterView extends BaseView {
    private Bitmap mBitmap;

    public BlurMaskFilterView(Context context) {
        super(context);

    }

    @Override
    protected void init() {
        super.init();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //禁用硬件加速
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);

        /*BlurMaskFilter.Blur 四种模式
        NORMAL: 内外都模糊绘制
        SOLID: 内部正常绘制，外部模糊
        INNER: 内部模糊，外部不绘制
        OUTER: 内部不绘制，外部模糊*/
        mPaint.setColor(Color.RED);

        //内外都模糊
        BlurMaskFilter maskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawRect(25, 25, 175, 175, mPaint);

        //内部正常绘制，外部模糊
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawRect(200, 25, 350, 175, mPaint);

        //内部模糊，外部不绘制
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
        canvas.drawRect(25, 300, 175, 450, mPaint);

        //外部模糊
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        canvas.drawRect(200, 300, 350, 450, mPaint);
//
//        mPaint.setMaskFilter(new BlurMaskFilter(100, BlurMaskFilter.Blur.OUTER));
//        canvas.drawBitmap(mBitmap, 25, 500, mPaint);
    }
}
