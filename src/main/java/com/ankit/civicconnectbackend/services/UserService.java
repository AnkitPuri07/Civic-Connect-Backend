package com.ankit.civicconnectbackend.services;

import com.ankit.civicconnectbackend.configs.JwtUtil;
import com.ankit.civicconnectbackend.dto.LoginRequest;
import com.ankit.civicconnectbackend.entities.User;
import com.ankit.civicconnectbackend.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest loginRequest) {

        User user =
                userRepo.findByEmail(
                        loginRequest.getEmail()
                );

        if (user == null) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        boolean matches =
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        return jwtUtil.generateToken(
                user.getEmail()
        );
    }

    public void addUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }
}
