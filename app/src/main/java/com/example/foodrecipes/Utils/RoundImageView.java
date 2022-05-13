package com.example.foodrecipes.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class RoundImageView extends View {

    public RoundImageView(Context context) {

        super(context);

    }

    public RoundImageView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    public RoundImageView(Context context, AttributeSet attrs,

                          int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override

    protected void onDraw(Canvas canvas) {

        Path clipPath = new Path();

        int w = this.getWidth();

        int h = this.getHeight();

        clipPath.addCircle(w/2, h/2, w/2, Path.Direction.CW);

        canvas.clipPath(clipPath);

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));

        super.onDraw(canvas);

    }

}
