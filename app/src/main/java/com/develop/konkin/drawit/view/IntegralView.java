package com.develop.konkin.drawit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.develop.konkin.drawit.model.helper.ScreenHelper;

public class IntegralView extends GraphView {
    private static final int COUNT_HATCH = 2;
    private float leftBoarderHatch;
    private float rightBoarderHatch;
    private float currentLeft;
    private float currentRight;
    private Path hatch;
    private Paint paintHatch;
    private Paint paintBoldHatch;

    public IntegralView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        hatch = new Path();
        leftBoarderHatch = rightBoarderHatch = Float.MAX_VALUE;

    }

    @Override
    protected void setPaints() {
        super.setPaints();
        paintHatch = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHatch.setStyle(Paint.Style.STROKE);
        paintHatch.setStrokeWidth(1);
        paintHatch.setColor(Color.DKGRAY);

        paintBoldHatch = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBoldHatch.setStyle(Paint.Style.STROKE);
        paintBoldHatch.setStrokeWidth(2);
        paintBoldHatch.setColor(Color.BLUE);
    }

    @Override
    public void update(final float width, final float height) {
        super.update(width, height);
        if (hatch != null && leftBoarderHatch != Float.MAX_VALUE && rightBoarderHatch != Float.MAX_VALUE) {
            setHatchBoarders(leftBoarderHatch, rightBoarderHatch);
        }
    }

    public void setHatchBoarders(final float leftBoarderHatch, final float rightBoarderHatch) {
        this.leftBoarderHatch = leftBoarderHatch;
        this.rightBoarderHatch = rightBoarderHatch;
        final float currentBoarderLeft = Math.max(leftBoarderHatch, -x0 / ms);
        final float currentBoarderRight = Math.min(rightBoarderHatch, (width - x0) / ms);
        currentLeft = currentRight = currentBoarderLeft + (currentBoarderRight - currentBoarderLeft) / 2;
        hatch.reset();
        invalidate();
    }

    @Override
    public void clearFunctions() {
        super.clearFunctions();
    }

    @Override
    protected void offset(float dx, float dy) {
        super.offset(dx, dy);
        hatch.offset(dx, dy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(hatch, paintHatch);
        if (!hatching()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();
        }

        if (getFirst() == null) {
            return;
        }

        final float xLeft = ScreenHelper.getXonScreen(leftBoarderHatch, x0, ms);
        final float xRight = ScreenHelper.getXonScreen(rightBoarderHatch, x0, ms);
        final float yLeft = ScreenHelper.getYonScreen(getFirst(), leftBoarderHatch, y0, ms);
        final float yRight = ScreenHelper.getYonScreen(getFirst(), rightBoarderHatch, y0, ms);

        canvas.drawLine(xLeft, y0, xLeft, yLeft, paintBoldHatch);
        canvas.drawLine(xRight, y0, xRight, yRight, paintBoldHatch);
    }

    private boolean hatching() {
        if (leftBoarderHatch == Float.MAX_VALUE || rightBoarderHatch == Float.MAX_VALUE || getFirst() == null) {
            return true;
        }
        final double step = ms / 300;
        final float currentRightBoarder = Math.min((width - x0) / ms, rightBoarderHatch);
        int n = 0;
        for (; n < COUNT_HATCH && currentRight < currentRightBoarder; n++, currentRight += step) {
            final float x = ScreenHelper.getXonScreen(currentRight, x0, ms);
            final float y = ScreenHelper.getYonScreen(getFirst(), currentRight, y0, ms);
            hatch.moveTo(x, y0);
            hatch.lineTo(x, y);
        }

        final boolean done = n == 20;

        final float currentLeftBoarder = Math.max(-x0 / ms, leftBoarderHatch);

        n = 0;
        for (; n < COUNT_HATCH && currentLeft > currentLeftBoarder; n++, currentLeft -= step) {
            final float x = ScreenHelper.getXonScreen(currentLeft, x0, ms);
            final float y = ScreenHelper.getYonScreen(getFirst(), currentLeft, y0, ms);
            hatch.moveTo(x, y0);
            hatch.lineTo(x, y);
        }

        if (n < 20) {
            return false;
        }

        return done;
    }
}
