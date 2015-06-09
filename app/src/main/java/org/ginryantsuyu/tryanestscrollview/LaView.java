package org.ginryantsuyu.tryanestscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.v4.view.MotionEventCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;

public class LaView extends View {
    public static final boolean DEBUG = false;

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

    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF rf = null;
    /**
     * pie图颜色
     */
    int pieColor = 0;
    /**
     * 显示文字颜色
     */
    int showingTextColor = 0;

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LaView, defStyle, 0);
        a.recycle();
        invalidateArc();
    }

    public static final int STROKE = 1;
    public static final int FILL = 2;

    /**
     * 画笔样式
     * <pre>
     * 可选值： STROKE——空心描边样式
     *          FILL——实心描边样式
     * </pre>
     *
     * @param paintStyle
     */
    public void setPaintStyle(int paintStyle) {
        this.paintStyle = paintStyle;
    }

    /**
     * 画笔样式
     */
    private int paintStyle = 2;

    /**
     * 显示文本颜色
     *
     * @param showingTextColor
     */
    public void setShowingTextColor(int showingTextColor) {
        this.showingTextColor = showingTextColor;
    }

    public void setShowingTextColor(String rrggbb) {
        this.showingTextColor = Color.parseColor(rrggbb);
    }

    /**
     * pie图颜色
     *
     * @param pieColor
     */
    public void setPieColor(int pieColor) {
        this.pieColor = pieColor;
    }

    @SuppressWarnings("ResourceType")
    public void setPieColor(String rrggbb) {
        this.pieColor = Color.parseColor(rrggbb);
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
    //最大进度
    float maxProgress = 100f;
    //当前进度
    float currentProgress = 0f;
    //下一阶进度
    float nextProgress = 0f;
    //进度比例，用于绘制进度时计算角度值
    float progressRatio = 0.0f;

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setNextProgress(float nextProgress) {
        this.nextProgress = nextProgress;
        progressRatio = currentProgress / maxProgress;
        nextProgressRatio = nextProgress / maxProgress;

        sweepAngle = 360 * progressRatio;
        nextSweepAngle = 360 * nextProgressRatio;
        refreshProgressEffectAnimate();
    }

    float showingPercent = 0f;

    /**
     * 开始不停刷新数值并提醒界面更新
     */
    public void refreshProgressEffectAnimate() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((int) sweepAngle < (int) nextSweepAngle) {
                    if (sweepAngle < 360) {
                        sweepAngle++;
                        showingPercent = sweepAngle / 360;
                        if (DEBUG)
                            Log.d("angle", "sweepAngle:  " + sweepAngle + " ---nextSweepAngle:  " + nextSweepAngle);
                        invalidate();
                        refreshProgressEffectAnimate();
                    }
                } else if ((int) sweepAngle > (int) nextSweepAngle) {
                    if (sweepAngle > 0) {
                        sweepAngle--;
                        showingPercent = sweepAngle / 360;
                        if (DEBUG)
                            Log.d("angle", "sweepAngle:  " + sweepAngle + " ---nextSweepAngle:  " + nextSweepAngle);
                        invalidate();
                        refreshProgressEffectAnimate();
                    }
                } else {
                    if (DEBUG)
                        Log.d("angle", "stop");
                    currentProgress = nextProgress;
                    invalidate();
                }
            }
        }, 5);
    }



    float mShowingTextSize = 40;

    /**
     * 初始化
     */
    private void invalidateArc() {

        raduis = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom()) / 2 - strokeWidth;
        float left = startPointX + getPaddingLeft() + strokeWidth;
        float top = startPointY + getPaddingTop() + strokeWidth;
        float right = left + raduis * 2 - strokeWidth;
        float bottom = top + raduis * 2 - strokeWidth;
        rf = new RectF(left, top, right, bottom);
        circleCenterX = (right + left) / 2;
        circleCenterY = (top + bottom) / 2;

        textPaint.setColor(showingTextColor);
        textPaint.setDither(true);
        textPaint.setTextSize(mShowingTextSize);


        paint2.setColor(pieColor);
        paint2.setDither(true);
        switch (paintStyle) {
            case STROKE:
                paint2.setStyle(Paint.Style.STROKE);
                break;
            case FILL:
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            default:
                paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        paint2.setStrokeWidth(strokeWidth);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        invalidate();
    }

    public void setRaduis(float raduis) {
        this.raduis = raduis;
    }

    /**
     * 下一个扫描角度
     */
    float nextSweepAngle;
    /**
     * 下一个进度比例
     */
    float nextProgressRatio;
    /**
     * 线宽
     */
    float strokeWidth = 6;
    /**
     * 圆弧的半径，正方形范围的一半
     */
    float raduis = 0f;
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

    TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    String textShowing = "75%";
    DecimalFormat decimalFormat = new DecimalFormat("##");

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textShowing = decimalFormat.format(showingPercent * 100) + "%";
        if (isInEditMode()) {
            sweepAngle = 75;
        }
        canvas.drawArc(rf, originalStartAngleOffset, sweepAngle, true, paint2);
        float textWidth = textPaint.measureText(textShowing);
        canvas.drawText(textShowing, circleCenterX - textWidth / 2, circleCenterY + mShowingTextSize / 2, textPaint);
    }

}
