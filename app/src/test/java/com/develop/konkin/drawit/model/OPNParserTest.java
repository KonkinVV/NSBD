package com.develop.konkin.drawit.model;

import com.develop.konkin.drawit.model.opn.OPNParser;

import org.junit.Test;

import static org.junit.Assert.*;

public class OPNParserTest {

    @Test
    public void readFunc1() {
        final String strFunction = "7+47+4-8/x";
        // final String strFunctionPostfix = "7 47 + 4 + 8 x ^ -";
        final Function function = OPNParser.readFunc(strFunction);
        final TokenData[] tokens = function.getPostTokens();
        assertEquals(new TokenData(Token.NUMBER, "7"), tokens[0]);
        assertEquals(new TokenData(Token.NUMBER, "47"), tokens[1]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[2]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[3]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[4]);
        assertEquals(new TokenData(Token.NUMBER, "8"), tokens[5]);
        assertEquals(new TokenData(Token.VAR, "x"), tokens[6]);
        assertEquals(new TokenData(Token.MID_SIGN, "/"), tokens[7]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[8]);
    }

    @Test
    public void readFunc2() {
        final String strFunction = "743*47+x+22-4-8/x";
        // 743 47 * x + 22 + 4 - 8 x ^ -
        final Function function = OPNParser.readFunc(strFunction);
        final TokenData[] tokens = function.getPostTokens();
        assertEquals(new TokenData(Token.NUMBER, "743"), tokens[0]);
        assertEquals(new TokenData(Token.NUMBER, "47"), tokens[1]);
        assertEquals(new TokenData(Token.MID_SIGN, "*"), tokens[2]);
        assertEquals(new TokenData(Token.VAR, "x"), tokens[3]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[4]);
        assertEquals(new TokenData(Token.NUMBER, "22"), tokens[5]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[6]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[7]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[8]);
        assertEquals(new TokenData(Token.NUMBER, "8"), tokens[9]);
        assertEquals(new TokenData(Token.VAR, "x"), tokens[10]);
        assertEquals(new TokenData(Token.MID_SIGN, "/"), tokens[11]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[12]);

    }

    @Test
    public void readFunc3() {
        final String strFunction = "3 + 4 * 2 / 4";
        // 3 4 2 * 4 / +
        final Function function = OPNParser.readFunc(strFunction);
        final TokenData[] tokens = function.getPostTokens();
        assertEquals(new TokenData(Token.NUMBER, "3"), tokens[0]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[1]);
        assertEquals(new TokenData(Token.NUMBER, "2"), tokens[2]);
        assertEquals(new TokenData(Token.MID_SIGN, "*"), tokens[3]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[4]);
        assertEquals(new TokenData(Token.MID_SIGN, "/"), tokens[5]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[6]);
    }

    @Test
    public void readFunc4() {
        final String strFunction = "3 + 4 * 2 / 4 ^ 0";
        // 3 4 2 * 4 / +
        final Function function = OPNParser.readFunc(strFunction);
        final TokenData[] tokens = function.getPostTokens();
        assertEquals(new TokenData(Token.NUMBER, "3"), tokens[0]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[1]);
        assertEquals(new TokenData(Token.NUMBER, "2"), tokens[2]);
        assertEquals(new TokenData(Token.MID_SIGN, "*"), tokens[3]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[4]);
        assertEquals(new TokenData(Token.NUMBER, "0"), tokens[5]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "^"), tokens[6]);
        assertEquals(new TokenData(Token.MID_SIGN, "/"), tokens[7]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[8]);
    }

    @Test
    public void readHardFunc1() {
        final String strFunction = "sin(3 + 4) * 2 / 4 ^ 0";
        // 3 4 + sin 2 * 4 ^ 0 /
        final Function function = OPNParser.readFunc(strFunction);
        final TokenData[] tokens = function.getPostTokens();
        assertEquals(new TokenData(Token.NUMBER, "3"), tokens[0]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[1]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[2]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "sin"), tokens[3]);
        assertEquals(new TokenData(Token.NUMBER, "2"), tokens[4]);
        assertEquals(new TokenData(Token.MID_SIGN, "*"), tokens[5]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[6]);
        assertEquals(new TokenData(Token.NUMBER, "0"), tokens[7]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "^"), tokens[8]);
        assertEquals(new TokenData(Token.MID_SIGN, "/"), tokens[9]);
    }


    @Test
    public void readHardFunc2() {
        final String strFunction = "ln(3 * sin(5 - 4 ^ 3)) + (5 - 7)";
        // 3 5 4 3 ^ - sin * ln 5 7 - +
        final Function function = OPNParser.readFunc(strFunction);
        final TokenData[] tokens = function.getPostTokens();
        assertEquals(new TokenData(Token.NUMBER, "3"), tokens[0]);
        assertEquals(new TokenData(Token.NUMBER, "5"), tokens[1]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[2]);
        assertEquals(new TokenData(Token.NUMBER, "3"), tokens[3]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "^"), tokens[4]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[5]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "sin"), tokens[6]);
        assertEquals(new TokenData(Token.MID_SIGN, "*"), tokens[7]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "ln"), tokens[8]);
        assertEquals(new TokenData(Token.NUMBER, "5"), tokens[9]);
        assertEquals(new TokenData(Token.NUMBER, "7"), tokens[10]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[11]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[12]);
    }
}