package com.company;

import java.util.Objects;

public class Tree implements Comparable<Tree> {
    public Tree() {
        this.name = "";
        this.amount = 0;
        this.treeType = TreeType.UNDEFINED;
    }

    public Tree(String name, int amount, TreeType treeType) {
        this.name = name;
        this.amount = amount;
        this.treeType = treeType;
    }

    @Override
    public int compareTo(Tree tree) {
        if (this.amount != tree.amount) {
            return this.amount - tree.amount;
        }
        return -this.name.compareTo(tree.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return amount == tree.amount &&
                Objects.equals(name, tree.name) &&
                treeType == tree.treeType;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", amount=" + amount +
                ", treeType=" + treeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public void setTreeType(TreeType treeType) {
        this.treeType = treeType;
    }

    protected String name;
    protected int amount;
    protected TreeType treeType;


}
