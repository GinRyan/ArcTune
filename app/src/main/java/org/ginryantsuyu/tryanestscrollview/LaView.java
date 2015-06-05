package org.ginryantsuyu.tryanestscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LaView extends View {


    public LaView(Context context) {
        super(context);
        init(null, 0);
    }

    public LaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    RectF rf = null;


    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LaView, defStyle, 0);
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        invalidateArc();
    }

    //圆心x坐标
    float circleCenterX = 0;
    //圆心y坐标
    float circleCenterY = 0;

    private void invalidateArc() {
        raduis = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        float left = startPointX + getPaddingLeft();
        float top = startPointY + getPaddingTop();
        float right = left + raduis * 2;
        float bottom = top + raduis * 2;
        rf = new RectF(left, top, right, bottom);
        circleCenterX = (right + left) / 2;
        circleCenterY = (top + bottom) / 2;

        paint1.setColor(Color.parseColor("#ed57ba"));
        paint1.setDither(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(6);

        paint2.setColor(Color.parseColor("#ed57ba"));
        paint2.setDither(true);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setStrokeWidth(6);
        invalidate();
    }

    public void setRaduis(float raduis) {
        this.raduis = raduis;
    }

    float raduis = 0f;//圆弧的半径，正方形范围的一半
    /**
     * 圆弧所在矩形起始x坐标
     */
    float startPointX = 0;
    /**
     * 圆弧所在矩形起始y坐标
     */
    float startPointY = 0;
    /**
     * 旋转的开始角度值
     */
    float originalStartAngleOffset = -90;
    /**
     * 圆弧划过的角度值
     */
    float sweepAngle = 240;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rf, paint1);
        canvas.drawArc(rf, originalStartAngleOffset, sweepAngle, true, paint2);
    }
    float startScrollX;
    float startScrollY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startScrollX = event.getX();
                startScrollY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //四个方向的旋转生肖
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
