package com.develop.konkin.drawit.model.helper;

import java.util.Stack;

public final class BracketHelper {
    private static final char[] OPEN_PARENTHESES = new char[]{'(', '[', '{', '<'};
    private static final char[] CLOSE_PARENTHESES = new char[]{')', ']', '}', '>'};

    private BracketHelper() {
    }

    public static boolean checkParentheses(final String s) {
        final char[] data = s.toCharArray();
        Stack<Character> opens = new Stack<>();
        for (char sign : data) {
            // if sign is not bracket - next
            if (!isBracket(sign)) {
                continue;
            }

            // if is openBracket then push to Stack
            if (isOpen(sign)) {
                opens.add(sign);
                continue;
            }

            // if stack isEmpty then InvalidExpression
            if (opens.isEmpty()) {
                return false;
            }

            char open = opens.pop();
            // if open-close brackets have different types then InvalidExpression
            if (getClose(open) != sign) {
                return false;
            }
        }

        // if stack have at least one bracket then InvalidExpression
        return opens.isEmpty();
    }

    public static boolean isBracket(char sign) {
        return isOpen(sign) || isClose(sign);
    }

    public static boolean isOpen(char sign) {
        for (char ch : OPEN_PARENTHESES) {
            if (ch == sign) {
                return true;
            }
        }
        return false;
    }


    public static boolean isClose(char sign) {
        for (char ch : CLOSE_PARENTHESES) {
            if (ch == sign) {
                return true;
            }
        }
        return false;
    }

    public static char getClose(char openScobe) {
        for (int i = 0; i < OPEN_PARENTHESES.length; i++) {
            if (OPEN_PARENTHESES[i] == openScobe) {
                return CLOSE_PARENTHESES[i];
            }
        }
        return '-';
    }

    public static int indexOfFirstBracket(final String s) {
        int minIndex = s.length();
        for (final char bracket : OPEN_PARENTHESES) {
            int index = s.indexOf(bracket);
            if (index > -1) {
                minIndex = Math.min(minIndex, index);
            }
        }

        if (minIndex == s.length()) {
            return -1;
        }

        return minIndex;
    }
}
