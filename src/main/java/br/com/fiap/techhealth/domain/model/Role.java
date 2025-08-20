package br.com.fiap.techhealth.domain.model;

public enum Role {

    DOCTOR("doctor"),

    NURSE("nurse"),

    PATIENT("patient");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
