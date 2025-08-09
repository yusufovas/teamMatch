package com.example.teamMatch.services.auth;

import com.example.teamMatch.components.JwtUtility;
import com.example.teamMatch.dto.LoginDto;
import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.exception.UserAlreadyExistsException;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtility jwtUtility) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public Map<String, String> signup(UserDto userDto) {
        Users user = new Users();

        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User with this email was registered before");
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );

        String token = jwtUtility.generateToken((UserDetails) authentication.getPrincipal());

        return Collections.singletonMap("token", token);
    }

    @Override
    public String signin(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtility.generateToken((UserDetails) authentication.getPrincipal());
    }
}
