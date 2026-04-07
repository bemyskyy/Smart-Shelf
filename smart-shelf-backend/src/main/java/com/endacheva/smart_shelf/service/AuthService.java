package com.endacheva.smart_shelf.service;

import com.endacheva.smart_shelf.dto.record.AuthRequest;
import com.endacheva.smart_shelf.dto.AuthResponse;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.repository.UserRepository;
import com.endacheva.smart_shelf.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Такой пользователь уже существует");
        }

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        var user = userRepository.findByUsername(request.username())
                .orElseThrow();
        var jwtToken = jwtUtil.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}
