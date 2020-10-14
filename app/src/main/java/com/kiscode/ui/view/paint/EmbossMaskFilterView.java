package com.kiscode.ui.view.paint;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;

import com.kiscode.ui.R;

/****
 * Description:  设置浮雕效果
 * Author:  keno
 * CreateDate: 2020/10/13 19:52
 */
public class EmbossMaskFilterView extends BaseView {
    private Bitmap mBitmap;

    public EmbossMaskFilterView(Context context) {
        super(context);

    }

    @Override
    protected void init() {
        super.init();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dog);
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

        //EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius) 的参数里，
        // direction 是一个 3 个元素的数组，指定了光源的方向；
        // ambient 是环境光的强度，数值范围是 0 到 1；
        // specular 是炫光的系数；
        // blurRadius 是应用光线的范围
        EmbossMaskFilter maskFilter = new EmbossMaskFilter(new float[]{0, 1, 1}, 0.7f, 18, 200);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
    }
}
