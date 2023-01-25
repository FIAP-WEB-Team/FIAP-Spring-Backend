package br.com.fiap.spring.service.firebase_impl;

import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.spring.model.dto.auth.*;
import br.com.fiap.spring.repository.FirebaseRepository;
import br.com.fiap.spring.security.JwtTokenUtil;
import br.com.fiap.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseRepository repository;

    public UserServiceImpl(JwtTokenUtil jwtTokenUtil,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, FirebaseRepository repository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public UserDTO create(CreateUserDTO createUserDTO) {
        CreateUserDTO toSave = new CreateUserDTO();
        toSave.username = createUserDTO.username;
        toSave.password = passwordEncoder.encode(createUserDTO.password);
        try {
            String id = repository.createSingleDocumentInCollection("GolData/Data/Users", toSave);
            UserDTO userDTO = new UserDTO();

            userDTO.id = id;
            userDTO.username = toSave.username;
            return userDTO;
        } catch (ExecutionException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @Override
    public JwtDTO login(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.username, authDTO.password));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        String token = jwtTokenUtil.generateToken(authDTO.username);
        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.token = token;

        return jwtDTO;
    }

}