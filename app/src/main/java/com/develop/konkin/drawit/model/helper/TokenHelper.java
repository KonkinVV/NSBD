package com.develop.konkin.drawit.model.helper;

import com.develop.konkin.drawit.model.Function;
import com.develop.konkin.drawit.model.Token;
import com.develop.konkin.drawit.model.TokenData;
import com.develop.konkin.drawit.model.opn.OPNParser;

import java.util.HashMap;
import java.util.Map;

public final class TokenHelper {
    private static final String[] VARS = new String[]{"x", "e", "pi"};
    private static final String[] LAW_SIGNS = new String[]{"+", "-"};
    private static final String[] MID_SIGNS = new String[]{"*", "/", ":"};
    private static final String[] HIGH_SIGNS = new String[]{"^",
                                                            "sin",
                                                            "cos",
                                                            "tg",
                                                            "tan",
                                                            "ctg",
                                                            "acos",
                                                            "asin",
                                                            "atan",
                                                            "actg",
                                                            "atg",
                                                            "abs",
                                                            "sqrt",
                                                            "lg",
                                                            "ln",
                                                            "log"};
    private static final Map<String, Token> map;

    static {
        map = new HashMap<>(VARS.length + LAW_SIGNS.length + MID_SIGNS.length + HIGH_SIGNS.length + 20);
        addToMap(VARS, Token.VAR);
        addToMap(LAW_SIGNS, Token.LOW_SIGN);
        addToMap(MID_SIGNS, Token.MID_SIGN);
        addToMap(HIGH_SIGNS, Token.HIGH_SIGN);
    }

    private TokenHelper() {
    }

    private static void addToMap(final String[] data, final Token token) {
        for (String s : data) {
            TokenHelper.map.put(s, token);
        }
    }

    public static Token typeToken(final String s) {
        final Token token = map.get(s);
        if (token != null) {
            return token;
        }

        byte countOfPoints = 0;
        boolean isNumber = true;
        for (char ch : s.toCharArray()) {
            if (ch == '.') {
                countOfPoints++;
            }
            if ((!Character.isDigit(ch) && ch != '.') || countOfPoints > 1) {
                isNumber = false;
                break;
            }
        }
        if (isNumber) {
            return Token.NUMBER;
        }

        if (BracketHelper.checkParentheses(s)) {
            int indexOfBracket = BracketHelper.indexOfFirstBracket(s);
            if (indexOfBracket == -1) {
                return Token.DRIVEL;
            }
            if (indexOfBracket == 0) {
                return Token.HARD_EXPRESSION;
            }
            final String func = s.substring(0, indexOfBracket);
            return map.containsKey(func) ? Token.HARD_EXPRESSION : Token.DRIVEL;
        }

        return Token.DRIVEL;
    }

    public static boolean isBinary(final String sign) {
        Token token = map.get(sign);
        if (token == null) {
            return false;
        }
        if (token == Token.LOW_SIGN || token == Token.MID_SIGN) {
            return true;
        }
        return sign.equals("log") || sign.equals("^");
    }

    public static int compareTo(final Token token1, final Token token2) {
        int priority1 = token1 == Token.HIGH_SIGN ? 3 : token1 == Token.MID_SIGN ? 2 : 1;
        int priority2 = token2 == Token.HIGH_SIGN ? 3 : token2 == Token.MID_SIGN ? 2 : 1;
        return priority1 - priority2;
    }

    public static TokenData loging(final String s1) {
        final int index = s1.indexOf(';');
        if (index < 0) {
            throw new IllegalArgumentException("log(a ; b) - right format to log");
        }
        final String strBase = s1.substring(0, index);
        final String strArg = s1.substring(index + 1);

        final Function base = OPNParser.readFunc(strBase);
        final Function arg = OPNParser.readFunc(strArg);

        String hard = base.getStringPostTokens() + " " + arg.getStringPostTokens() + " log";
        hard = hard.replaceAll(" {2}", " ");

        final TokenData[] summaryTokens = new TokenData[base.getPostTokens().length + arg.getPostTokens().length];
        System.arraycopy(base.getPostTokens(), 0, summaryTokens, 0, base.getPostTokens().length);
        System.arraycopy(arg.getPostTokens(), 0, summaryTokens, base.getPostTokens().length, arg.getPostTokens().length);

        return new TokenData(Token.HARD_EXPRESSION, hard, summaryTokens);
    }


}
