package org.example.foodbudgetbackendspring.user.service;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.dto.RegisterRequest;
import org.example.foodbudgetbackendspring.user.model.User;
import org.example.foodbudgetbackendspring.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
    }
}
