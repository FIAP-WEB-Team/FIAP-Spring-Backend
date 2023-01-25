package br.com.fiap.spring.service;

import br.com.fiap.spring.model.dto.auth.*;

public interface UserService {
    UserDTO create(CreateUserDTO createUserDTO);

    JwtDTO login(AuthDTO authDTO);
}
