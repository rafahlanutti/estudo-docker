package br.com.estudos.estudosdocker.config;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(final String message) {
        super(message);
    }
}
