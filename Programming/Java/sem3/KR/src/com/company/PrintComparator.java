package com.company;

import java.util.Comparator;

public class PrintComparator<T extends Tree> implements Comparator<T> {
    @Override
    public int compare(T t, T t1) {
        return t1.amount - t.amount;
    }
}
