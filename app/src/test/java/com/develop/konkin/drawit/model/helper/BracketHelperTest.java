package com.develop.konkin.drawit.model.helper;

import com.develop.konkin.drawit.model.Function;

import org.junit.Test;

import static org.junit.Assert.*;

public class BracketHelperTest {

    @Test
    public void checkParentheses() {
        assertTrue(BracketHelper.checkParentheses("(([{}]))"));
        assertTrue(BracketHelper.checkParentheses("(7<([{}7])>)"));
        assertTrue(BracketHelper.checkParentheses("{([{}])9}"));
        assertTrue(BracketHelper.checkParentheses("{9}"));

        assertFalse(BracketHelper.checkParentheses("}{"));
        assertFalse(BracketHelper.checkParentheses("}<><<{"));
        assertFalse(BracketHelper.checkParentheses("<<<"));
        assertFalse(BracketHelper.checkParentheses("(((>>>"));
        assertFalse(BracketHelper.checkParentheses("[}"));
    }

    @Test
    public void isBracket() {
        assertTrue(BracketHelper.isBracket('('));
        assertTrue(BracketHelper.isBracket('<'));
        assertTrue(BracketHelper.isBracket('['));
        assertTrue(BracketHelper.isBracket('{'));
        assertTrue(BracketHelper.isBracket(')'));
        assertTrue(BracketHelper.isBracket(']'));
        assertTrue(BracketHelper.isBracket('}'));
        assertTrue(BracketHelper.isBracket('>'));

        assertFalse(BracketHelper.isClose('d'));
        assertFalse(BracketHelper.isClose('a'));
        assertFalse(BracketHelper.isClose('G'));
        assertFalse(BracketHelper.isClose('('));
        assertFalse(BracketHelper.isClose('['));
        assertFalse(BracketHelper.isClose('7'));
    }

    @Test
    public void isOpen() {
        assertTrue(BracketHelper.isOpen('('));
        assertTrue(BracketHelper.isOpen('<'));
        assertTrue(BracketHelper.isOpen('['));
        assertTrue(BracketHelper.isOpen('{'));

        assertFalse(BracketHelper.isOpen('d'));
        assertFalse(BracketHelper.isOpen('a'));
        assertFalse(BracketHelper.isOpen('r'));
        assertFalse(BracketHelper.isOpen(']'));
        assertFalse(BracketHelper.isOpen('&'));
        assertFalse(BracketHelper.isOpen('?'));
    }

    @Test
    public void isClose() {
        assertTrue(BracketHelper.isClose(')'));
        assertTrue(BracketHelper.isClose('>'));
        assertTrue(BracketHelper.isClose(']'));
        assertTrue(BracketHelper.isClose('}'));

        assertFalse(BracketHelper.isClose('d'));
        assertFalse(BracketHelper.isClose('a'));
        assertFalse(BracketHelper.isClose('G'));
        assertFalse(BracketHelper.isClose('('));
        assertFalse(BracketHelper.isClose('['));
        assertFalse(BracketHelper.isClose('7'));
    }

    @Test
    public void getClose() {
        assertEquals(')', BracketHelper.getClose('('));
        assertEquals(']', BracketHelper.getClose('['));
        assertEquals('}', BracketHelper.getClose('{'));
        assertEquals('>', BracketHelper.getClose('<'));


        assertEquals('-', BracketHelper.getClose('&'));
        assertEquals('-', BracketHelper.getClose('R'));
        assertEquals('-', BracketHelper.getClose('f'));
    }

    @Test
    public void indexOfFirstBracket() {
        assertEquals(3, BracketHelper.indexOfFirstBracket("mkk(mdkkfmkef"));
        assertEquals(-1, BracketHelper.indexOfFirstBracket("sin"));
        assertEquals(3, BracketHelper.indexOfFirstBracket("mkk[mdkkfmkef"));
        assertEquals(-1, BracketHelper.indexOfFirstBracket("m}kkmdkkfmkef"));
        assertEquals(5, BracketHelper.indexOfFirstBracket("mkkmd{kkfmkef"));
        assertEquals(5, BracketHelper.indexOfFirstBracket("mkkmd<kkfmkef"));
        assertEquals(5, BracketHelper.indexOfFirstBracket("mkkmd[kkfmkef"));
        assertEquals(5, BracketHelper.indexOfFirstBracket("mkkmd{kkfmkef"));
        assertEquals(-1, BracketHelper.indexOfFirstBracket(""));
        assertEquals(3, BracketHelper.indexOfFirstBracket("mkk<mdkkfmkef"));
    }
}