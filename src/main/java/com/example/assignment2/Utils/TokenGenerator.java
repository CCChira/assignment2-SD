package com.example.assignment2.Utils;

import java.security.SecureRandom;

public class TokenGenerator {

    private static final int TOKEN_LENGTH = 128;
    private static final String TOKEN_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";

    private final SecureRandom random;
    private final byte[] seed;

    public TokenGenerator(String input) {
        this.random = new SecureRandom();
        this.seed = input.getBytes();
        random.setSeed(seed);
    }

    public String generateToken() {
        byte[] bytes = new byte[TOKEN_LENGTH];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < bytes.length; i++) {
            int index = Math.abs(bytes[i] % TOKEN_CHARS.length());
            sb.append(TOKEN_CHARS.charAt(index));
        }
        System.out.println("HERE" + sb);
        return sb.toString();
    }
}