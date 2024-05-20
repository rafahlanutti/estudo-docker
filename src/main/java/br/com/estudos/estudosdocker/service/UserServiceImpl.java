package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.dto.UserRequest;
import br.com.estudos.estudosdocker.dto.UserResponse;
import br.com.estudos.estudosdocker.mapper.UserMaper;
import br.com.estudos.estudosdocker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMaper modelMapper;

    @Override
    public UserResponse saveUser(final UserRequest userRequest) {
        final var encoder = new BCryptPasswordEncoder();
        final var rawPassword = userRequest.getPassword();
        final var encodedPassword = encoder.encode(rawPassword);

        final var user = modelMapper.map(userRequest);
        user.setPassword(encodedPassword);
        if(userRequest.getId() != null){
            UserInfo oldUser = userRepository.findFirstById(userRequest.getId());
            if(oldUser != null){
                throw new RuntimeException("Usuario já cadastrado.");
                //TODO: Criar exception personalizada
            }
        }
        return modelMapper.map(userRepository.save(user));
    }

    @Override
    public UserResponse getUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var userDetail = (UserDetails) authentication.getPrincipal();
        final var usernameFromAccessToken = userDetail.getUsername();
        final var user = userRepository.findByUsername(usernameFromAccessToken);
        return modelMapper.map(user);
    }

    @Override
    public List<UserResponse> getAllUser() {
        final var users = (List<UserInfo>) userRepository.findAll();
        return users.stream().map(modelMapper::map).toList();
    }

}
