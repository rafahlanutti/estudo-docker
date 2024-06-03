package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.RefreshToken;

import java.util.Optional;


public interface RefreshTokenService {

    RefreshToken createRefreshToken(final String username);
    Optional<RefreshToken> findByToken(final String token);
    RefreshToken verifyExpiration(final RefreshToken token);

}
