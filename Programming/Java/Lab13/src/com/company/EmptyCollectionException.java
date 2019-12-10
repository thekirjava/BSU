package com.company;

public class EmptyCollectionException extends Exception {
    EmptyCollectionException() {
        super("Collection is empty");
    }
}
