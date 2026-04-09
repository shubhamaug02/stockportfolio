package com.portfolio.stockportfolio.service;

import com.portfolio.stockportfolio.entity.User;
import com.portfolio.stockportfolio.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String login(String username, String password){
        Optional<User> existedUser = userRepository.findByUsername(username);
        if(existedUser.isEmpty()){
            throw new RuntimeException("user does not exist");
        }
        User user = existedUser.get();
         if(!passwordEncoder.matches(password, user.getPassword())){
             throw new RuntimeException("Invalid credentials");
         }
         return jwtService.generateToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("User not Found: " + username));
    }
}
