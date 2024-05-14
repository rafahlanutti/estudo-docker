package br.com.estudos.estudosdocker.config;

public class PessoaNotFindException extends RuntimeException {
    public PessoaNotFindException(final String message) {
        super(message);
    }
}
