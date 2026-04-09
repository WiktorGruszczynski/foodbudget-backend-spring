package org.example.foodbudgetbackendspring.user;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserReference(UUID id) {
        return userRepository.getReferenceById(id);
    }
}
