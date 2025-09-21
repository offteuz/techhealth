package br.com.fiap.techhealth.exception;

public class AccessDeniedGraphQLException extends RuntimeException {
    public AccessDeniedGraphQLException(String message) {
        super(message);
    }
}