package br.com.fiap.techhealth.exception;

public class ConsultationNotFoundException extends RuntimeException {

    public ConsultationNotFoundException(String message) {
        super(message);
    }

    public ConsultationNotFoundException() {
        super("Consulta não encontrada. Verifique!");
    }
}
