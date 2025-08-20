package br.com.fiap.techhealth.exception;

public class ValidateTokenErrorException extends RuntimeException{

    public ValidateTokenErrorException(String message) {
        super(message);
    }
}
