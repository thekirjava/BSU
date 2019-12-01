package com.company;

public enum Material {
    LEATHER, FIBRE, FUR;
    @Override
    public String toString() {
        switch (this) {
            case FUR:
                return new String("fur");
            case FIBRE:
                return new String("fibre");
            case LEATHER:
                return new String("leather");
        }
        return null;
    }

    public static Material toType(String s) throws EnumIncorrectException{
        Material ans;
        switch (s) {
            case "fur":
                ans = FUR;
                break;
            case "fibre":
                ans = FIBRE;
                break;
            case "leather":
                ans = LEATHER;
                break;
            default:
                throw new EnumIncorrectException("Wrong material type");
        }
        return ans;
    }
}
