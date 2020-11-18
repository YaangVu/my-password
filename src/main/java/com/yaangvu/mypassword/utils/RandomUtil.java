package com.yaangvu.mypassword.utils;

import java.util.Random;

public class RandomUtil {
    
    /**
     * Generate a random alphanumeric string
     *
     * @param length Length of random String
     *
     * @return Alphanumeric Random String
     */
    public static String alphanumeric(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        
    }
    
    /**
     * Generate a random alphabetic string
     *
     * @param length Length of random String
     *
     * @return Alphanumeric Random String
     */
    public static String alphabetic(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        
        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    
    /**
     * Generate a random numeric
     *
     * @param length Length of random String
     *
     * @return Integer Random Number
     */
    public static Integer numeric(int length) {
        int leftLimit = 48; // letter '0'
        int rightLimit = 57; // letter '9'
        Random random = new Random();
        
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        
        return Integer.parseInt(generatedString);
    }
}
