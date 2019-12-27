package com.develop.konkin.drawit.model.opn;

import com.develop.konkin.drawit.model.Function;
import com.develop.konkin.drawit.model.Token;
import com.develop.konkin.drawit.model.TokenData;
import com.develop.konkin.drawit.model.helper.TokenHelper;
import com.develop.konkin.drawit.model.tree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public final class OPNParser {

    private OPNParser() {
    }

    public static Function readFunc(String s) {
        s = s.replaceAll(",", ".");
        s = s.replaceAll(" ", "");
        if (s.length() == 0) {
            throw new IllegalArgumentException("Enter function!");
        }
        final Function function = new Function(s);
        readFunc(function);
        return function;
    }

    public static Node createTree(final String function) {
        return createTree(readFunc(function));
    }

    private static Node createTree(final TokenData[] tokens) {
        final Stack<Node> stack = new Stack<>();
        for (final TokenData tokenData : tokens) {
            if (tokenData.getToken() == Token.NUMBER || tokenData.getToken() == Token.VAR) {
                stack.push(Node.ofNumber(tokenData));
                continue;
            }

            if (tokenData.getToken() == Token.HARD_EXPRESSION) {
                final Node node = createTree(tokenData.getTokensHardExpression());
                stack.push(node);
                continue;
            }

            if (TokenHelper.isBinary(tokenData.getValue())) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Неправильный постфикс");
                }
                final Node right = stack.pop();
                final Node left = stack.pop();
                stack.push(Node.ofBinaryOperation(left, right, tokenData));
            } else {
                if (stack.size() < 1) {
                    throw new IllegalArgumentException("Неправильный постфикс");
                }
                final Node node = stack.pop();
                stack.push(Node.ofUnaryOperation(node, tokenData));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Неправильный постфикс");
        }
        return stack.pop();
    }

    public static Node createTree(final Function function) {
        return createTree(function.getPostTokens());
    }

    public static void readFunc(final Function function) {
        List<TokenData> infTokens = new ArrayList<>(Arrays.asList(function.getInfixTokens()));

        for (int i = 0; i < infTokens.size(); i++) {
            if (infTokens.get(i).getValue().equals("^")) {
                veryHighPriority(infTokens, i, infTokens.get(i));
                i = 0;
            }
        }

        for (int i = 0; i < infTokens.size(); i++) {
            if (infTokens.get(i).getValue().equals("log")) {
                veryHighPriority(infTokens, i, infTokens.get(i));
                i = 0;
            }
        }

        final Stack<TokenData> stack = new Stack<>();
        final List<TokenData> answer = new ArrayList<>(infTokens.size());

        for (final TokenData data : infTokens) {
            switch (data.getToken()) {
                case NUMBER:
                case VAR:
                case HARD_EXPRESSION:
                    answer.add(data);
                    break;
                case HIGH_SIGN:
                case LOW_SIGN:
                case MID_SIGN:
                    if (!stack.isEmpty()) {
                        TokenData tokenData = stack.peek();
                        if (TokenHelper.compareTo(tokenData.getToken(), data.getToken()) >= 0) {
                            answer.add(stack.pop());
                        }
                    }
                    stack.push(data);
                    break;
                default:
                    throw new IllegalArgumentException("Неверное выражение");
            }
        }

        while (!stack.isEmpty()) {
            answer.add(stack.pop());
        }

        function.setPostTokens(answer.toArray(new TokenData[0]));
    }

    /**
     * log a(b); a ^ b
     */
    //TODO remove this method
    public static void veryHighPriority(final List<TokenData> a,
                                        final int i,
                                        final TokenData sign) {
        final TokenData tokenData1 = a.get(i - 1);
        final TokenData tokenData2 = a.get(i + 1);
        final String s = tokenData1.getValue() + " " + tokenData2.getValue() + " " + sign.getValue();
        final List<TokenData> tokens = new ArrayList<>();
        if (tokenData1.getToken() == Token.HARD_EXPRESSION) {
            tokens.addAll(Arrays.asList(tokenData1.getTokensHardExpression()));
        } else {
            tokens.add(tokenData1);
        }
        if (tokenData2.getToken() == Token.HARD_EXPRESSION) {
            tokens.addAll(Arrays.asList(tokenData2.getTokensHardExpression()));
        } else {
            tokens.add(tokenData2);
        }
        tokens.add(sign);

        TokenData token = new TokenData(Token.HARD_EXPRESSION, s, tokens.toArray(new TokenData[0]));
        a.add(i - 1, token);

        for (int j = 0; j < 3; j++) {
            a.remove(i);
        }
    }
}
