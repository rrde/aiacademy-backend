package com.voice2learn.AIAcademy.auth.service;

import com.voice2learn.AIAcademy.auth.dto.LoginRequest;
import com.voice2learn.AIAcademy.auth.dto.LoginResponse;
import com.voice2learn.AIAcademy.auth.dto.RegisterRequest;
import com.voice2learn.AIAcademy.security.JwtService;
import com.voice2learn.AIAcademy.user.entity.Role;
import com.voice2learn.AIAcademy.user.entity.User;
import com.voice2learn.AIAcademy.user.repository.RoleRepository;
import com.voice2learn.AIAcademy.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role studentRole =
                roleRepository.findByName("ROLE_STUDENT")
                        .orElseThrow();

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRoles(Set.of(studentRole));

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(
            LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(
                        request.getEmail()
                );

        return new LoginResponse(token);
    }


}