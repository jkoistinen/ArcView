package com.jk.arcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by jk on 2017-01-26.
 */

public class ArcView extends View {

    private Integer mDefaultTotalPieces = 100;
    private Integer mTotalPieces;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);

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

    }
}
