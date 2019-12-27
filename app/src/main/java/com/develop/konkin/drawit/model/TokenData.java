package com.develop.konkin.drawit.model;

import java.util.NoSuchElementException;

public class TokenData {
    private final Token token;
    private final String value;
    private final TokenData[] tokensHardExpression;

    public TokenData(final Token token, final String value) {
        this(token, value, new TokenData[0]);
    }

    public TokenData(final Token token, final String value, TokenData[] tokensHardExpression) {
        this.token = token;
        this.value = value;
        this.tokensHardExpression = tokensHardExpression;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TokenData) {
            TokenData other = (TokenData) obj;
            return token == other.token && value.equals(other.value);
        }
        return false;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }

    public TokenData[] getTokensHardExpression() {
        if(tokensHardExpression.length == 0){
            throw new NoSuchElementException("Hard expression is empty");
        }
        return tokensHardExpression;
    }
}
