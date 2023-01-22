package br.com.fiap.spring.security;

import br.com.fiap.spring.model.dto.UserDTO;
import br.com.fiap.spring.repository.FirebaseRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.*;

@Component
public class JwtUserDetailService implements UserDetailsService {
    private final FirebaseRepository firebaseRepository;

    public JwtUserDetailService(FirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user;
        try {
            List<UserDTO> users = firebaseRepository.getMultipleDocumentsFromCollection(UserDTO.class, "GolData" +
                    "/Data/Users", 1, Map.of("username", username));
            if (users.isEmpty())
                throw new InvalidParameterException("Username " + username + " not found");
            user = users.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                new ArrayList<>()
        );
    }
}