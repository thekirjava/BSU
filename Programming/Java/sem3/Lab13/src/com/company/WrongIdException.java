package com.company;

public class WrongIdException extends Exception {
    WrongIdException() {
        super("Two different students have the same id");
    }
}
