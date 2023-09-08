package com.ale.blog.service;

import com.ale.blog.entity.User;
import com.ale.blog.repository.UserRepository;
import com.ale.blog.security.UserAccessDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AtomicReference<UserDetails> atomicReference = new AtomicReference<>();
        userRepository.findFirstByUsername(username).ifPresentOrElse(user -> {
            atomicReference.set(new UserAccessDetails(user));
        }, () -> {
            throw new UsernameNotFoundException(username);
        });
        return atomicReference.get();
    }

    @Override
    public Optional<UserDetails> loadUserByUuid(UUID uuid) {
        AtomicReference<Optional<UserDetails>> atomicReference = new AtomicReference<>();
        userRepository.findByUuid(uuid).ifPresentOrElse(user -> {
            atomicReference.set(
                    Optional.of(new UserAccessDetails(user))
            );
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    /**
     * throw AppException if can't find id
     */
    @Override
    public User getById(UUID uuid) {
        return defaultGetById(uuid, userRepository);
    }

    @Override
    public Optional<User> findFistUser() {
        return userRepository.findFirstByOrderByUuid();
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
