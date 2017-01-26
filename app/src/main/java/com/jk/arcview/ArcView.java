package com.jk.arcview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jk on 2017-01-26.
 */

public class ArcView extends View {

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RectF getOval(int width, int height){

        int padding = 10;

        return new RectF(0, 0, width - padding, height - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorAccent));

        int w = getWidth();
        int h = getHeight();

        //Choose the smaller
        int size = Math.min(w, h);

        canvas.drawArc(getOval(size, size), 0.0f, 45.0f,true, paint);
        canvas.drawArc(getOval(size, size), 180.0f, 45.0f,true, paint);

        paint.setColor(getResources().getColor(R.color.colorPrimary));

        canvas.drawArc(getOval(size, size), 90.0f, 45.0f,true, paint);
        canvas.drawArc(getOval(size, size), 270.0f, 45.0f,true, paint);

        canvas.

    }
}
