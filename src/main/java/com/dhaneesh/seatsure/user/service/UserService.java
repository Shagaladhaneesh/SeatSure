package com.dhaneesh.seatsure.user.service;

import com.dhaneesh.seatsure.exceptions.EmailAlreadyExistException;
import com.dhaneesh.seatsure.exceptions.UserNotFoundException;
import com.dhaneesh.seatsure.exceptions.EmailAlreadyExistException;
import com.dhaneesh.seatsure.user.dto.CreateUserRequest;
import com.dhaneesh.seatsure.user.dto.UserResponse;
import com.dhaneesh.seatsure.user.entity.User;
import com.dhaneesh.seatsure.user.mapper.UserMapper;
import com.dhaneesh.seatsure.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,
                       UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder=passwordEncoder;

    }

    public UserResponse createUser(CreateUserRequest request) {

        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new EmailAlreadyExistException("email exists");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        return userMapper.toResponse(user);
    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("user not found");
        }

        userRepository.deleteById(id);
    }
}