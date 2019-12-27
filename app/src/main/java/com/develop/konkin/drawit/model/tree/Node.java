package com.develop.konkin.drawit.model.tree;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.develop.konkin.drawit.model.Token;
import com.develop.konkin.drawit.model.TokenData;

public class Node {
    private final Node left;
    private final Node right;
    private final TokenData tokenData;

    private Node(@Nullable final Node left,
                 @Nullable final Node right,
                 @NonNull final TokenData tokenData) {
        this.left = left;
        this.right = right;
        this.tokenData = tokenData;
    }

    public static Node ofNumber(@NonNull final TokenData tokenData) {
        return new Node(null, null, tokenData);
    }

    public static Node ofUnaryOperation(@NonNull final Node left,
                                        @NonNull final TokenData tokenData) {
        return new Node(left, null, tokenData);
    }

    public static Node ofBinaryOperation(@NonNull final Node left,
                                         @NonNull final Node right,
                                         @NonNull final TokenData tokenData) {
        return new Node(left, right, tokenData);
    }

    public double calculate(double x) {
        if (left == null && right == null) {
            if (tokenData.getToken() == Token.VAR) {
                switch (tokenData.getValue()) {
                    case "pi":
                        return Math.PI;
                    case "e":
                        return Math.E;
                    default:
                        return x;
                }
            }
            return Double.parseDouble(tokenData.getValue());
        }

        if (right == null) {
            double value = left.calculate(x);
            return Operation.deciding(tokenData.getValue(), value, -1);
        }

        assert left != null;
        double leftValue = left.calculate(x);
        double rightValue = right.calculate(x);
        return Operation.deciding(tokenData.getValue(), leftValue, rightValue);
    }
}
