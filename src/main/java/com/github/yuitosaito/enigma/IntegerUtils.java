package com.github.yuitosaito.enigma;

public class IntegerUtils {
    /**
     * 文字列が10進数の整数であり、かつintの範疇に収まるかどうかを判定する。
     * @param str 判定対象の文字列。
     * @return 文字列が10進数の整数であり、かつintの範疇に収まる場合はTrue.
     */
    public static boolean isInteger(String str) {
        if (str == null
                || str.isEmpty()
                || str.equals("+")
                || str.equals("-")) {
            return false;
        }

        char first = str.charAt(0);
        int i = (first == '+' || first == '-') ? 1 : 0;
        int sign = (first == '-') ? -1 : 1;
        int len = str.length();
        long integer = 0;

        while (i < len) {
            char ch = str.charAt(i++);
            int digit = Character.digit(ch, 10);
            if (digit == -1) {
                return false;
            }

            integer = integer * 10 + sign * digit;
            if (integer < Integer.MIN_VALUE || Integer.MAX_VALUE < integer) {
                return false;
            }
        }
        return true;
    }

}
