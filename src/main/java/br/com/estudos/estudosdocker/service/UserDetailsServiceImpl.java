package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.helpers.CustomUserDetails;
import br.com.estudos.estudosdocker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.debug("Buscando por usuario.");
        final var user = userRepository.findByUsername(username);
        if(user == null){
            logger.error("Usuario não encontrado: " + username);
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        logger.info("Usuário autenticado com sucesso.");
        return new CustomUserDetails(user);
    }
}