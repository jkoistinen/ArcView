package com.jk.arcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by jk on 2017-01-26.
 */

public class ArcView extends View {

    private Paint paint;

    private Integer mDefaultTotalPieces = 100;
    private Integer mTotalPieces;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();

        TypedArray styledAttributesFromXML = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcView, 0, 0);

        mTotalPieces = styledAttributesFromXML.getInteger(R.styleable.ArcView_totalPieces, mDefaultTotalPieces);

    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public float getPieceAngle(){
        Float cycle = 360.0f;
        Float mTotalPiecesFloat = (float)mTotalPieces;

        Float pieceAngle = (cycle / mTotalPiecesFloat);

        return pieceAngle;
    }

    public RectF getOval(int width, int height){

        int padding = 10;

        return new RectF(0, 0, width - padding, height - padding);
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    public void setTotalPieces(int pieces){
        if(pieces < 1) {
            pieces = 1;
        }
        mTotalPieces = pieces;
        invalidate();
    }

    public void drawFullArc(Canvas canvas, int size, Paint paint) {
        Float startPoint = 0.0f;
        int colorAccent = getResources().getColor(R.color.colorAccent);
        int colorPrimary = getResources().getColor(R.color.colorPrimary);

        for (int i = 0; i < mTotalPieces; i++) {

            if(i % 2 == 0) {
                paint.setColor(colorAccent);
            } else {
                paint.setColor(colorPrimary);
            }

            canvas.drawArc(getOval(size, size), startPoint, getPieceAngle(),true, paint);
            startPoint = startPoint + getPieceAngle();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        //Choose the smaller
        int size = Math.min(w, h);

        drawFullArc(canvas, size, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }
}
