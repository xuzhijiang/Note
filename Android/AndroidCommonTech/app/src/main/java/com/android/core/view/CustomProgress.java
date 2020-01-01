package com.android.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgress extends ProgressBar {

    /**
     * 文本内容
     */
    private String text;

    private Paint mPaint;

    public CustomProgress(Context context) {
        super(context);
        initPaint();
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化，画笔
     */
    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(18.0f);
    }

    @Override
    public synchronized void setProgress(int progress) {
        int percent = (progress * 100) / getMax();
        text = String.valueOf(percent) + "%";
        super.setProgress(progress);
    }

    @Override
    public void setProgress(int progress, boolean animate) {
        super.setProgress(progress, animate);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
    }
}
