package com.jk.arcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

/**
 * Created by jk on 2017-01-26.
 */

public class ArcView extends View {

    private Paint paint;
    private RectF ovalshape;

    private Integer mDefaultTotalPieces = 100;
    private Integer mTotalPieces;

    public ArcView(Context context) {
        super(context);

        init();
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

        if(mTotalPieces == null){
            mTotalPieces = mDefaultTotalPieces;
        }

        int radius = size/2;
        float left = (float) (getWidth()/2)-radius;
        float top = (float) (getHeight()/2)-radius;
        float right = (float) (getWidth()/2)+radius;
        float bottom = (float) (getHeight()/2+radius);

        ovalshape = new RectF(left, top, right , bottom);

        for (int i = 0; i < mTotalPieces; i++) {

            if(i % 2 == 0) {
                paint.setColor(colorAccent);
            } else {
                paint.setColor(colorPrimary);
            }

            canvas.drawArc(ovalshape, startPoint, getPieceAngle(),true, paint);
            startPoint = startPoint + getPieceAngle();

        }
        paint.setColor(colorPrimary);
        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,200, paint);
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();

        }

        return super.onTouchEvent(event);
    }
}
