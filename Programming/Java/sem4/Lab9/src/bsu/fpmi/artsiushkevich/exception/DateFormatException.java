package bsu.fpmi.artsiushkevich.exception;

public class DateFormatException extends Exception{
    public DateFormatException() {
        super("Input data isn't look like formula or date");
    }
}
