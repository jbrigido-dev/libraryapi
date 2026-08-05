package com.jbrigido.library.service;

import com.jbrigido.library.core.PassEncoder;
import com.jbrigido.library.dto.UserRequestDTO;
import com.jbrigido.library.entity.User;
import com.jbrigido.library.exception.PasswordException;
import com.jbrigido.library.exception.UserAlreadyExistsException;
import com.jbrigido.library.mapper.UserMapper;
import com.jbrigido.library.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PassEncoder encoder;

    public UserService(UserRepository repository, UserMapper mapper, PassEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public void register(UserRequestDTO request) {
        if (existByUsername(request.username()))
            throw new UserAlreadyExistsException("An User already exist with this username.");
        if (request.password().length() < 8) {
            throw new PasswordException("The password must have at least 8 characters");
        }

        User mapped = mapper.toEntity(request);
        mapped.setPassword(encoder.getEncoder().encode(mapped.getPassword()));

        repository.save(mapped);
    }

    public boolean existByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
