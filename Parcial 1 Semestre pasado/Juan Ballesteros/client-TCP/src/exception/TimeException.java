package exception;

public class TimeException extends Exception {
    private String message;

    public TimeException() {
        message = ("Please check call startTime and stopTime before use getTime method");
    }

    public String getMessage() {
        return message;
    }
}
