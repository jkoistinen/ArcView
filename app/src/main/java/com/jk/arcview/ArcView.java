package com.jk.arcview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jk on 2017-01-26.
 */

public class ArcView extends View {

    private Paint paint;

    private final Integer mDefaultTotalPieces = 100;
    private Integer mTotalPieces;

    private GestureDetector mDetector;

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

    private float getPieceAngle(){
        Float cycle = 360.0f;
        Float mTotalPiecesFloat = (float)mTotalPieces;

        return (cycle / mTotalPiecesFloat);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        mDetector = new GestureDetector(getContext(), new GestureListener());
        mDetector.setIsLongpressEnabled(false);

    }

    public void setTotalPieces(int pieces){
        if(pieces < 1) {
            pieces = 1;
        }
        mTotalPieces = pieces;
        invalidate();
    }

    private void drawFullArc(Canvas canvas, int size, Paint paint) {
        Float startPoint = 0.0f;
        int colorAccent = ContextCompat.getColor(getContext(), R.color.colorAccent);
        int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);

        int radius = size/2;
        float left = (float) (getWidth()/2)-radius;
        float top = (float) (getHeight()/2)-radius;
        float right = (float) (getWidth()/2)+radius;
        float bottom = (float) (getHeight()/2+radius);

        RectF ovalshape = new RectF(left, top, right, bottom);

        if(mTotalPieces == null){
            mTotalPieces = mDefaultTotalPieces;
        }

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
        return mDetector.onTouchEvent(event);

    }

private class GestureListener extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("TAG", "onFling");

        float distance = 8000f;
        float scale = getContext().getResources().getDisplayMetrics().density;
        ArcView.this.setCameraDistance(distance * scale);

        if(e1.getY() - e2.getY() > 50){ //Up
            swipeAnimate("up");
            return true;
        }

        if(e2.getY() - e1.getY() > 50){ //Down
            swipeAnimate("down");
            return true;
        }

        if(e1.getX() - e2.getX() > 50){ //Left
            swipeAnimate("left");
            return true;
        }

        if(e2.getX() - e1.getX() > 50) { //Right
            swipeAnimate("right");
            return true;
        }
        else {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void swipeAnimate(String direction) {
        String property = null;
        Float start = null;
        Float end = null;

        switch(direction) {
            case "left":
                property = "rotationY"; start = 360f; end = 0f;
                break;
            case "up":
                property = "rotationX"; start = 0f; end = 360f;
                break;
            case "right":
                property = "rotationY"; start = 0f; end = 360f;
                break;
            case "down":
                property = "rotationX"; start = 360f; end = 0f;
                break;

        }

        ObjectAnimator animation = ObjectAnimator.ofFloat(ArcView.this, property, start, end);
        animation.setDuration(3000);
        animation.start();
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("TAG", "onScroll");
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
}

}
