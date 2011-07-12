package edu.umd.cs.guitar.exception;

public class InvalidProxyException extends GException {

    public InvalidProxyException(String name) {
        super("Invalid proxy of name " + name);
    }
}
