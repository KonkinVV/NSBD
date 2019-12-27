package com.develop.konkin.drawit.model;

import com.develop.konkin.drawit.model.helper.BracketHelper;
import com.develop.konkin.drawit.model.helper.TokenHelper;
import com.develop.konkin.drawit.model.opn.OPNParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function {
    private TokenData[] infixTokens;
    private TokenData[] postTokens;
    private final String data;

    public Function(final String data) {
        this.data = data;
        infixTokens = createTokens();
    }

    public TokenData[] createTokens() {
        if (!BracketHelper.checkParentheses(data)) {
            throw new IllegalArgumentException("Expression has mistakes in parentheses");
        }

        final List<TokenData> a = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add(data);
        final String[] signs = new String[]{"+", "-", "*", "/", "^"};

        for (final String sign : signs) {
            list = separation(list, sign);
        }

        for (String s : list) {
            Token token = TokenHelper.typeToken(s);
            if (token == Token.DRIVEL) {
                throw new IllegalArgumentException("Ошибка ввода");
            }

            if (token != Token.HARD_EXPRESSION) {
                a.add(new TokenData(token, s));
                continue;
            }

            final int indexOfBracket = s.indexOf('(');
            String s1 = s.substring(indexOfBracket);
            final String strSign = s.substring(0, indexOfBracket);
            final Token sign = TokenHelper.typeToken(s.substring(0, indexOfBracket));
            if (TokenHelper.typeToken(s1) == Token.HARD_EXPRESSION) {
                s1 = s1.substring(1, s1.length() - 1);
                if (indexOfBracket == 0) {
                    final Function function = OPNParser.readFunc(s1);
                    a.add(new TokenData(Token.HARD_EXPRESSION, function.getStringPostTokens(), function.postTokens));
                    continue;
                }

                if (strSign.equals("log")) {
                    a.add(TokenHelper.loging(s1));
                    continue;
                }

                if (sign == Token.HIGH_SIGN) {
                    final Function function = OPNParser.readFunc(s1);
                    String hard = function.getStringPostTokens() + " " + strSign;
                    hard = hard.replace("  ", " ");
                    final List<TokenData> list1 = new ArrayList<>(Arrays.asList(function.postTokens));
                    list1.add(new TokenData(sign, strSign));
                    a.add(new TokenData(Token.HARD_EXPRESSION, hard, list1.toArray(new TokenData[0])));
                    continue;
                }
                //TODO конкретизировать ошибку
                throw new IllegalArgumentException("Enter error");
            } else {
                throw new IllegalArgumentException("Enter error");
            }
        }
        return a.toArray(new TokenData[0]);
    }

    public static List<String> separation(final List<String> list, final String sign) {
        final List<String> mas = new ArrayList<>();
        for (String s : list) {
            for (int i = 0, j = 0, count = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch == '(') {
                    count++;
                }
                if (ch == ')') {
                    count--;
                }

                if (count == 0 & sign.equals(ch + "")) {
                    String s1 = s.substring(j, i);
                    if (!s1.equals("")) mas.add(s1);
                    mas.add(sign);
                    j = i + 1;
                    continue;
                }
                if (i == s.length() - 1) {
                    mas.add(s.substring(j));
                }
            }
        }
        return mas;
    }


    public TokenData[] getInfixTokens() {
        return infixTokens;
    }


    public void setPostTokens(TokenData[] postTokens) {
        this.postTokens = postTokens;
    }

    public TokenData[] getPostTokens() {
        postTokens = updatePostTokens(postTokens);
        return postTokens;
    }

    private TokenData[] updatePostTokens(final TokenData[] tokens) {
        final List<TokenData> list = new ArrayList<>(postTokens.length);
        for (final TokenData tokenData : tokens) {
            if (tokenData.getToken() == Token.HARD_EXPRESSION) {
                list.addAll(Arrays.asList(updatePostTokens(tokenData.getTokensHardExpression())));
                continue;
            }
            list.add(tokenData);
        }
        return list.toArray(new TokenData[0]);
    }

    public String getStringPostTokens() {
        final StringBuilder sb = new StringBuilder();
        for (TokenData tokenData : postTokens) {
            sb.append(tokenData.getValue()).append(" ");
        }
        return sb.toString().replaceAll(" {2}", " ").trim();
    }

    public void setInfixTokens(TokenData[] infixTokens) {
        this.infixTokens = infixTokens;
    }
}
