package com.develop.konkin.drawit.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.develop.konkin.drawit.model.helper.ScreenHelper;
import com.develop.konkin.drawit.model.tree.Node;

public class FunctionView {
    private static final double EPSILON = Math.pow(10, -2);
    private static final float MAX_FLOAT_PATH = 2000;
    private static final int COUNT_POINTS = 5;

    private static final int[] colors = new int[]{Color.MAGENTA, Color.GRAY, Color.BLUE, Color.RED};

    private static int nextColor = 0;
    private final Path path;
    private final Node node;

    private double xRight;
    private double xLeft;

    private final Paint paint;

    public FunctionView(final Node node) {
        this.node = node;
        this.path = new Path();
        xLeft = xRight = 0;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        nextColor %= colors.length;
        paint.setColor(colors[nextColor++]);
//      paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }

    public Node getNode() {
        return node;
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void offset(final float dx, final float dy) {
        path.offset(dx, dy);
    }

    public boolean goRight(final float x0, final float y0, final float ms, final double boarder) {
        final double step = ms / 2000;

        boolean prevNaN = true;
        int count = 0;
        float preY = 0;

        for (double realX = xRight; realX <= boarder + EPSILON; realX += step, xRight += step) {
            float x = ScreenHelper.getXonScreen(realX, x0, ms);
            float y = ScreenHelper.getYonScreen(node, realX, y0, ms);

            if (!isBadValue(y) && Math.abs(y) > MAX_FLOAT_PATH) {
                y = y > 0 ? MAX_FLOAT_PATH : -MAX_FLOAT_PATH;
            }

            if (prevNaN) {
                if (!isBadValue(y)) {
                    path.moveTo(x, y);
//                    path.addCircle(x, y, 1, Path.Direction.CCW);
                }
            } else if (!isBadValue(y)) {
                if (Math.abs(preY - y) > 5 * ms) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            prevNaN = isBadValue(y);
            preY = y;

            if (++count > COUNT_POINTS) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        path.reset();
        xLeft = xRight = 0;
    }


    public boolean goLeft(final float x0, final float y0, final float ms, final double boarder) {
        final double step = ms / 2000;

        boolean prevNaN = true;
        int count = 0;
        float preY = 0;

        for (double realX = xLeft; realX >= boarder - EPSILON; realX -= step, xLeft -= step) {
            float x = ScreenHelper.getXonScreen(realX, x0, ms);
            float y = ScreenHelper.getYonScreen(node, realX, y0, ms);
            if (!isBadValue(y) && Math.abs(y) > MAX_FLOAT_PATH) {
                y = y > 0 ? MAX_FLOAT_PATH : -MAX_FLOAT_PATH;
            }
            if (prevNaN) {
                if (!isBadValue(y)) {
                    path.moveTo(x, y);
//                    path.addCircle(x, y, 1, Path.Direction.CCW);
                }
            } else if (!isBadValue(y)) {
                if (Math.abs(preY - y) > 5 * ms) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }

            prevNaN = isBadValue(y);
            preY = y;

            if (++count > COUNT_POINTS) {
                return false;
            }
        }
        return true;
    }

    private boolean isBadValue(final float value) {
        return Float.isNaN(value) || Float.isInfinite(value);
    }
}
