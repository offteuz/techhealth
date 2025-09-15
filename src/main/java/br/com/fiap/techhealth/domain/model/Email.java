package br.com.fiap.techhealth.domain.model;

public record Email(

        String to,

        String subject,

        String body
) {
}
