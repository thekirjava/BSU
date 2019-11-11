package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

class SeriesSizeException extends Exception {
    SeriesSizeException(String s) {
        super(s);
    }
}

public abstract class Series {
    Series() {
        this.base = 0;
        this.step = 0;
        this.size = 0;
    }

    Series(double b, double st, int s) throws SeriesSizeException {
        this.base = b;
        this.step = st;
        if (s < 0) {
            throw new SeriesSizeException("Size must be positive");
        }
        this.size = s;
    }

    public abstract double getN(int k);

    public double sum() {
        double ans = 0;
        for (int i = 1; i <= this.size; i++) {
            ans += this.getN(i);
        }
        return ans;
    }

    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int i = 1; i <= this.size; i++) {
            ans.append(this.getN(i));
            ans.append(" ");
        }
        return ans.toString();
    }

    public void saveToFile(File f) throws java.io.IOException {
        PrintStream filestream = new PrintStream(f);

        filestream.println(this.sum());
        filestream.print(this.toString());
    }

    protected double step;
    protected double base;
    protected int size;

}
