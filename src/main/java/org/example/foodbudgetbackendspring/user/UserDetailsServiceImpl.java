package org.example.foodbudgetbackendspring.user;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.model.CustomUserDetails;
import org.example.foodbudgetbackendspring.user.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Nonnull
    public UserDetails loadUserByUsername(@Nonnull String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole())
                )
        );
    }
}
