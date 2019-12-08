package com.company;

public class EmptyCollectionException extends Exception {
    EmptyCollectionException() {
        super("File with data wasn't chosen");
    }
}
