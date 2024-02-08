package br.com.amz.hostfillit.usecases.exception;

public class DateOverlapException extends IllegalArgumentException {

    public DateOverlapException(String message) {
        super(message);
    }
}
