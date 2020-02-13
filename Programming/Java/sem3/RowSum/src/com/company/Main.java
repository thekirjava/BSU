package com.company;

public class Main {
    private static double sum3(double x, double eps) {
        double ans = 0;
        double cur = 1;
        for (int k = 1; Math.abs(cur) >= eps; k++) {
            cur *= -1;
            cur *= x;
            cur /= 3;
            cur += (cur / k);
            ans += cur;
        }
        return ans;
    }

    public static void main(String[] args) {
        double x;
        double eps;
        try {
            if (args.length != 2){
                throw new Exception();
            }
            x = Double.parseDouble(args[0]);
            eps = Double.parseDouble(args[1]);
        }
        catch(Exception e){
            System.out.println("Incorrect input!");
            return;
        }
        System.out.println("Sum of Row = " + sum3(x, eps));

    }
}