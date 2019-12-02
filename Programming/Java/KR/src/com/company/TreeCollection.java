package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class TreeCollection<T extends Tree> extends ArrayList<T> {

    public TreeCollection(int initialCapacity) {
        super(initialCapacity);
    }

    public TreeCollection() {
        super();
    }

    public TreeCollection(Collection<? extends T> c) {
        super(c);
    }

    public void printAll() {
        TreeCollection temp = (TreeCollection) this.clone();
        Collections.sort(temp, new PrintComparator<T>());
        for (Object o : temp) {
            System.out.println(o);
        }
    }

    public int frequency(T tree) throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException();
        }
        return Collections.frequency(this, tree);
    }

    public T min() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException();
        }
        return Collections.min(this);
    }

    public int count(TreeType type) throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException();
        }
        return this.stream().filter(t -> t.treeType == type).mapToInt(Tree::getAmount).sum();
    }
}
