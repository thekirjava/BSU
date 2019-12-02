package com.company;


public class ForestTree extends Tree {
    public ForestTree() {
        super();
        woodAmount = 0;
    }

    public ForestTree(String name, int amount, TreeType treeType, int woodAmount) {
        super(name, amount, treeType);
        this.woodAmount = woodAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ForestTree that = (ForestTree) o;
        return woodAmount == that.woodAmount;
    }

    @Override
    public String toString() {
        return  super.toString() +
                " woodAmount=" + woodAmount;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public void setWoodAmount(int woodAmount) {
        this.woodAmount = woodAmount;
    }

    private int woodAmount;


}
