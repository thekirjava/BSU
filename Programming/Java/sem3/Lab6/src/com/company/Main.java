package com.company;

import static java.lang.Character.isDigit;

public class Main {

    public static void main(String[] args) {
        for (String arg : args) {
            StringBuffer sb = new StringBuffer(arg);
            for (int i = 0; i < sb.length() - 1; i++) {
                if ((i == 0) || (((!isDigit(sb.charAt(i - 1))) && (sb.charAt(i - 1) != '.')))) {
                    if ((sb.charAt(i) == '0') && ((sb.charAt(i + 1) != '.') && ((sb.charAt(i + 1) >= '0') && (sb.charAt(i + 1) <= '9')))) {
                        sb.deleteCharAt(i);
                        i--;
                    }
                }
            }
            System.out.print(sb + " ");
        }
    }
}
