package com.example.demo.services;

import com.example.demo.dtos.UserDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.responses.LoginResponse;
import com.example.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    private User signup(UserDTO input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    private User authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email, password
                )
        );

        return userRepository.findByEmail(email);
    }

    public LoginResponse login(String email, String password) {
        User user = authenticate(email, password);

        if (user == null) {
            throw new ResourceNotFoundException(User.class, email);
        }

        String jwtToken = jwtService.generateToken(user);

        return new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
    }

    public UserDTO  register(UserDTO userDTO) {
        User user = signup(userDTO);
        return userMapper.toDTO(user);
    }
}
