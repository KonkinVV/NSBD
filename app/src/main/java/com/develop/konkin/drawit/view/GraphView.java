package com.develop.konkin.drawit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.develop.konkin.drawit.model.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {
    private float lengthHatch;
    private long updateTime = 0;
    protected float width;
    protected float height;
    protected float x0;
    protected float y0;
    protected float ms;
    private final List<FunctionView> functions;
    private final Path cells;

    private Paint paintText;
    private Paint paintCells;
    private Paint paintMainHatch;

    private float preTapX;
    private float preTapY;

    private boolean graphsDone;


    public GraphView(Context context) {
        this(context, null);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        functions = new ArrayList<>();
        cells = new Path();
        setPaints();
        if (attrs != null) {
            //TODO get attrs
        }
        update();
    }

    public void update() {
        update(getWidth(), getHeight());
    }

    public void update(final float width, final float height) {
        this.width = width;
        this.height = height;
        x0 = width / 2;
        y0 = height / 2;
        ms = width / 15;
        lengthHatch = ms / 10;
        if (ms > 0.1) {
            paintText.setTextSize(ms / 3);
        }
        updateCells();

        for (final FunctionView functionView : functions) {
            functionView.reset();
        }
    }

    protected void setPaints() {
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.GRAY);
        paintText.setTextSize(20);

        paintMainHatch = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMainHatch.setColor(Color.BLACK);
        paintMainHatch.setStrokeWidth(3);

        paintCells = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCells.setColor(Color.BLACK);
        paintCells.setStyle(Paint.Style.STROKE);
        paintCells.setStrokeWidth(1);
    }

    protected Node getFirst() {
        if (functions.isEmpty()) {
            return null;
        }
        return functions.get(0).getNode();
    }

    private void updateCells() {
        if (width < 1.0 || height < 1.0) {
            return;
        }
        cells.reset();
        final float lengthHatch = ms / 10;
        for (float xRight = x0, xLeft = x0, yTop = y0, yBottom = y0; xLeft > 0 || yTop > 0;
             xRight += ms, xLeft -= ms, yTop -= ms, yBottom += ms) {

            cells.moveTo(xLeft, y0 - lengthHatch);
            cells.lineTo(xLeft, y0 + lengthHatch);

            cells.moveTo(xRight, y0 - lengthHatch);
            cells.lineTo(xRight, y0 + lengthHatch);

            cells.moveTo(x0 - lengthHatch, yTop);
            cells.lineTo(x0 + lengthHatch, yTop);

            cells.moveTo(x0 - lengthHatch, yBottom);
            cells.lineTo(x0 + lengthHatch, yBottom);
        }
    }

    public void addAll(final List<Node> functions) {
        for (final Node node : functions) {
            if (node != null) {
                this.functions.add(new FunctionView(node));
            }
        }
        invalidate();
    }

    public void add(final Node node) {
        if (node != null) {
            this.functions.add(new FunctionView(node));
        }
        invalidate();
    }

    protected void offset(final float dx, final float dy) {
        cells.offset(dx, dy);
        for (final FunctionView function : functions) {
            function.offset(dx, dy);
        }
    }

    protected void clearFunctions() {
        functions.clear();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preTapX = event.getRawX();
                preTapY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float tapX = event.getRawX();
                final float tapY = event.getRawY();
                final float dx = tapX - preTapX;
                final float dy = tapY - preTapY;
                offset(dx, dy);
                x0 += dx;
                y0 += dy;
                preTapX = tapX;
                preTapY = tapY;
                final long time = System.currentTimeMillis();
                if (time - updateTime > 15) {
                    updateTime = time;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                invalidate();
        }
        return true;
    }


    private void drawNumberHorizontal(final float x,
                                      final float y,
                                      final String text,
                                      final Canvas canvas) {
        final float widthText = paintText.measureText(text);
        canvas.drawText(text, x - widthText / 2, y, paintText);
        canvas.drawLine(x, y0 - lengthHatch, x, y0 + lengthHatch, paintCells);

    }

    private void drawNumberVertical(final float x,
                                    final float y,
                                    final String text,
                                    final Canvas canvas) {
        final float widthText = paintText.measureText(text);
        canvas.drawText(text, x - widthText - 2 * lengthHatch, y + paintText.getTextSize() / 3, paintText);
        canvas.drawLine(x0 - lengthHatch, y, x0 + lengthHatch, y, paintCells);
    }

    private void numbering(final Canvas canvas) {
        if (width < 1.0 || height < 1.0) {
            return;
        }

        final float textSize = paintText.getTextSize();
        for (int i = 1; ; i++) {
            float xRight = x0 + i * ms;
            float xLeft = x0 - i * ms;
            float yTop = y0 - i * ms;
            float yBottom = y0 + i * ms;

            drawNumberHorizontal(xLeft, y0 + 2 * textSize, Integer.toString(-i), canvas);
            drawNumberHorizontal(xRight, y0 + 2 * textSize, Integer.toString(i), canvas);
            drawNumberVertical(x0, yTop, Integer.toString(i), canvas);
            drawNumberVertical(x0, yBottom, Integer.toString(-i), canvas);
            if (xLeft < 0 && yTop < 0 && xRight > width && yBottom > height) {
                return;
            }
        }
    }

    private void drawFunction(final FunctionView function, final Canvas canvas) {
        if (width < 0.1 || height < 0.1) {
            return;
        }
        graphsDone &= function.goRight(x0, y0, ms, (width - x0) / ms);
        graphsDone &= function.goLeft(x0, y0, ms, -x0 / ms);
        canvas.drawPath(function.getPath(), function.getPaint());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        update(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(x0, 0, x0, height, paintMainHatch);
        canvas.drawLine(0, y0, width, y0, paintMainHatch);

        numbering(canvas);

        graphsDone = true;
        for (final FunctionView function : functions) {
            drawFunction(function, canvas);
        }

        if (graphsDone) {
            updateTime = System.currentTimeMillis();
        } else {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();
        }
    }

}
