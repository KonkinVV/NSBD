package com.develop.konkin.drawit.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FunctionTest {


    @Test
    public void createTokens1() {
        final String strFunction = "7+47+4-8^x";
        final Function function = new Function(strFunction);
        TokenData[] tokens = function.getInfixTokens();
        assertEquals(new TokenData(Token.NUMBER, "7"), tokens[0]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[1]);
        assertEquals(new TokenData(Token.NUMBER, "47"), tokens[2]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[3]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[4]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[5]);
        assertEquals(new TokenData(Token.NUMBER, "8"), tokens[6]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "^"), tokens[7]);
        assertEquals(new TokenData(Token.VAR, "x"), tokens[8]);

    }

    @Test
    public void createTokens2() {
        final String strFunction = "743*47+x+22-4-8^x";
        final Function function = new Function(strFunction);
        TokenData[] tokens = function.getInfixTokens();
        assertEquals(new TokenData(Token.NUMBER, "743"), tokens[0]);
        assertEquals(new TokenData(Token.MID_SIGN, "*"), tokens[1]);
        assertEquals(new TokenData(Token.NUMBER, "47"), tokens[2]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[3]);
        assertEquals(new TokenData(Token.VAR, "x"), tokens[4]);
        assertEquals(new TokenData(Token.LOW_SIGN, "+"), tokens[5]);
        assertEquals(new TokenData(Token.NUMBER, "22"), tokens[6]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[7]);
        assertEquals(new TokenData(Token.NUMBER, "4"), tokens[8]);
        assertEquals(new TokenData(Token.LOW_SIGN, "-"), tokens[9]);
        assertEquals(new TokenData(Token.NUMBER, "8"), tokens[10]);
        assertEquals(new TokenData(Token.HIGH_SIGN, "^"), tokens[11]);
        assertEquals(new TokenData(Token.VAR, "x"), tokens[12]);
    }

    @Test
    public void separation() {
        final String func = "fmkdfk+d,ls+fsfsfs+ffdfd";
        final List<String> list = new ArrayList<>();
        list.add(func);
        final List<String> answer = Function.separation(list, "+");
        final StringBuilder sb2 = new StringBuilder();
        for (final String s : answer) {
            if (s.contains("+")) {
                assertEquals("+", s);
            }
            sb2.append(s);
        }
        assertEquals(func, sb2.toString());
    }

}