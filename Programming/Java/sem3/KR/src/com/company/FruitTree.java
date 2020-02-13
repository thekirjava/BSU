package com.company;

public class FruitTree extends Tree {

    public FruitTree() {
        super();
        this.harvestAmount = 0;
    }

    public FruitTree(String name, int amount, TreeType treeType, int harvestAmount) {
        super(name, amount, treeType);
        this.harvestAmount = harvestAmount;
    }

    @Override
    public String toString() {
        return super.toString() +
                " harvestAmount=" + harvestAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FruitTree fruitTree = (FruitTree) o;
        return harvestAmount == fruitTree.harvestAmount;
    }

    public int getHarvestAmount() {
        return harvestAmount;
    }

    public void setHarvestAmount(int harvestAmount) {
        this.harvestAmount = harvestAmount;
    }

    private int harvestAmount;
}
