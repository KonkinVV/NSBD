package com.develop.konkin.drawit.model.tree;

import com.develop.konkin.drawit.model.opn.OPNParser;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    public void checkFunctionAnswer(final String function, final double x, final double excepted) {
        final Node tree = OPNParser.createTree(function);
        final double actual = tree.calculate(x);
        assertTrue(Math.abs(excepted - actual) < 0.1);
    }

    @Test
    public void calculate() {
        final String function1 = "7+47+4-8/x";
        checkFunctionAnswer(function1, 1, 50);
        checkFunctionAnswer(function1, 4, 56);
        checkFunctionAnswer(function1, 0.5, 42);
        checkFunctionAnswer(function1, 2, 54);

        final String function2 = "sin(3 + 4) * 2 / 4 ^ 0";
        checkFunctionAnswer(function2, 1, 1.3139);

        final String function3 = "ln(3 * sin(pi / 2)) + x";
        checkFunctionAnswer(function3, 1, 2.098);
        checkFunctionAnswer(function3, 4, 5.098);
        checkFunctionAnswer(function3, 0.5, 1.598);
        checkFunctionAnswer(function3, 2, 3.098);

        final String function4 = "x^2";
        checkFunctionAnswer(function4, 1, 1);
        checkFunctionAnswer(function4, 4, 16);
        checkFunctionAnswer(function4, 0.5, 0.25);
        checkFunctionAnswer(function4, 2, 4);

        final String function5 = "2^(x^2 + 1^2)";
        checkFunctionAnswer(function5, 1, 4);
        checkFunctionAnswer(function5, 4, 131072);
        checkFunctionAnswer(function5, 0.5, 2.378);
        checkFunctionAnswer(function5, 2, 32);
    }

    @Test
    public void reduction() {
    }
}