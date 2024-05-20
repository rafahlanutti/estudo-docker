package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.dto.UserRequest;
import br.com.estudos.estudosdocker.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse saveUser(UserRequest userRequest);
    UserResponse getUser();
    List<UserResponse> getAllUser();
}
