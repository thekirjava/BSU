package com.company;

public enum TreeType {
    LEAF,
    NEEDLE,
    UNDEFINED;

    @Override
    public String toString() {
        switch (this) {
            case LEAF:
                return "leaf";
            case NEEDLE:
                return "needle";
            default:
                return "undefined";
        }
    }

    public static TreeType toType(String s) {
        TreeType ans;
        s = s.toLowerCase();
        switch (s) {
            case "leaf":
                ans = LEAF;
                break;
            case "needle":
                ans = NEEDLE;
                break;
            default:
                ans = UNDEFINED;
                break;
        }
        return ans;
    }
}
