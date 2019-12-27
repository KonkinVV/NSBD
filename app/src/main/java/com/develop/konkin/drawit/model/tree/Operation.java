package com.develop.konkin.drawit.model.tree;

import com.develop.konkin.drawit.model.TokenData;
import com.develop.konkin.drawit.model.helper.TokenHelper;

public class Operation {
    private String s;
    private final boolean binary;

    Operation(TokenData sign) {
        binary = TokenHelper.isBinary(sign.getValue());
        s = sign.getValue();
    }

    private String signDer() {
        switch (s) {
            case "sin":
                return "cos(x)";
            case "cos":
                return "-sin(x)";
            case "tg":
                return "1/(cos(x)^2)";
            case "tan":
                return "1/(cos(x)^2)";
            case "ctg":
                return "-1/(sin(x)^2)";
            case "sqrt":
                return "1/ (2*sqrt(x))";
            case "asin":
                return "1/sqrt(1-x^2)";
            case "acos":
                return "-1/sqrt(1-x^2)";
            case "atan":
                return "1/(1+x^2)";
            case "actg":
                return "-1/(1+x^2)";
            case "ln":
                return "1/x";
            case "lg":
                return "1/(ln(10)*x)";
            default:
                return "1/(1+x^2)";
        }
    }

    public boolean isBinary() {
        return binary;
    }

    public static double deciding(final String sign, double x1, double x2) {
        switch (sign) {
            // бинарныые
            case "*":
                return x1 * x2;
            case "+":
                return x1 + x2;
            case "/":
                return x1 / x2;
            case "-":
                return x1 - x2;
            case "^":
                return Math.pow(x1, x2);
            case "log":
                return Math.log(x2) / Math.log(x1);
            // унарные
            case "sin":
                return Math.sin(x1);
            case "cos":
                return Math.cos(x1);
            case "tg":
                return Math.tan(x1);
            case "tan":
                return Math.tan(x1);
            case "ctg":
                return 1 / Math.tan(x1);
            case "sqrt":
                return Math.sqrt(x1);
            case "abs":
                return Math.abs(x1);
            case "asin":
                return Math.asin(x1);
            case "acos":
                return Math.acos(x1);
            case "atan":
                return Math.atan(x1);
            case "actg":
                return -Math.atan(x1) + Math.PI / 2;
            case "ln":
                return Math.log(x1);
            case "lg":
                return Math.log10(x1);
            default:
                return Math.atan(x1);
        }
    }

}
