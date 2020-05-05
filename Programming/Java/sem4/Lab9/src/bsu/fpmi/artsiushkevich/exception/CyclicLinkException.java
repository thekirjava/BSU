package bsu.fpmi.artsiushkevich.exception;

public class CyclicLinkException extends Exception {
    public CyclicLinkException() {
        super("Links are cyclic!");
    }
}
