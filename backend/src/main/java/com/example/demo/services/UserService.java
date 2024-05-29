package com.example.demo.services;

import com.example.demo.dtos.UserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.Tournament;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Util util;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO > getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).toList();
    }

    public UserDTO  getUserById(Long id){
//        User user = util.getUser(id);
//        return userMapper.toDTO(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return userMapper.toDTO(currentUser);
    }

    public UserDTO  createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    public UserDTO  updateUser(Long id, UserDTO dto) {
        User existingUser = util.getUser(id);

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Current password must be provided for verification");
        }

        if (!passwordEncoder.matches(dto.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (dto != null) {
            userMapper.updateEntity(existingUser, dto);
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    public String deleteUser(Long id) {
        User user = util.getUser(id);
        Set<Tournament> tournaments = user.getTournaments();
        Iterator<Tournament> iterator = tournaments.iterator();
        while (iterator.hasNext()) {
            Tournament tournament = iterator.next();
            iterator.remove();
            tournament.getUsers().remove(user);
        }
        userRepository.deleteById(id);
        return "User " + id + " deleted";
    }
}