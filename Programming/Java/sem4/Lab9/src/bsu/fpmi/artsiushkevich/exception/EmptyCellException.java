package bsu.fpmi.artsiushkevich.exception;

public class EmptyCellException extends Exception{
    public EmptyCellException() {
        super("Referred cell is empty");
    }
}
