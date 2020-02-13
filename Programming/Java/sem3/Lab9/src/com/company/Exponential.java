package com.company;

public class Exponential extends Series {
    Exponential() {
        super();
    }

    Exponential(double b, double st, int s) throws SeriesSizeException{
        super(b, st, s);
    }
    public double getN(int k) {
        double ans = this.base;
        for (int i = 1; i < k; i++) {
            ans *= this.step;
        }
        return ans;
    }
}
