package de.gravitex.banking.client.exception;

public class ConsoleInteractionException extends Exception {

    private static final long serialVersionUID = 681921730074850208L;

    public ConsoleInteractionException(String message, Throwable t) {
        super(message, t);
    }
}