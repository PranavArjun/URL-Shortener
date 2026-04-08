package com.app.url_shortener.util;

import java.util.Random;

public class ShortKeyGenerator {
    private static final String CHAR_SET="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH=6;
    private static final Random random = new Random();

    public static String generateShortKey(){
        StringBuilder key = new StringBuilder();
        for(int i = 0; i<LENGTH; i++){
            key.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return key.toString();
    }

}
