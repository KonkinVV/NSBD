package com.develop.konkin.drawit.model.helper;

import com.develop.konkin.drawit.model.tree.Node;

public final class ScreenHelper {
    private ScreenHelper() {
    }

    public static float getYonScreen(final Node node,
                                     final double realX,
                                     final float y0,
                                     final float ms) {
        final double y = node.calculate(realX);
        return (float) (y0 - y * ms);
    }

    public static float getXonScreen(final double realX, final float x0, final float ms) {
        return (float) (x0 + realX * ms);
    }
}
