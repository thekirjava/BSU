package com.company;

public enum Fuel {
    BENSIN, DIESEL;

    @Override
    public String toString() {
        switch (this) {
            case BENSIN:
                return new String("bensin");
            case DIESEL:
                return new String("diesel");
        }
        return null;
    }

    public Fuel toType(String s) throws EnumIncorrectException{
        Fuel ans;
        switch (s) {
            case "bensin":
                ans = BENSIN;
                break;
            case "diesel":
                ans = DIESEL;
                break;
            default:
                throw new EnumIncorrectException();
        }
        return ans;
    }
}
