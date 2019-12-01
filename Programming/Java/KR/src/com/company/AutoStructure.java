package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class AutoStructure<T extends Auto> extends ArrayList<T> {
    public void printAll() {
        for (T t : this) {
            System.out.print(t + " ");
        }
    }

    public int frequency(T o) throws EmptyException {
        if (this.isEmpty()) {
            throw new EmptyException();
        }
        return Collections.frequency(this, o);
    }

    public T binarySearch(T o) throws EmptyException {
        {
            if (this.isEmpty()) {
                throw new EmptyException();
            }
        }
        AutoStructure<T> temp = (AutoStructure<T>) this.clone();
        Collections.sort(temp);
        int index = Collections.binarySearch(temp, o);
        if (index < 0) {
            return null;
        }
        return temp.get(index);
    }

    public T max() throws EmptyException {
        if (this.isEmpty()) {
            throw new EmptyException();
        }
        return Collections.max(this);
    }
}
