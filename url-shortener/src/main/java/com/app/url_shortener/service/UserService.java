package com.app.url_shortener.service;

import com.app.url_shortener.dto.UserLogInDto;
import com.app.url_shortener.dto.UserRegisterRequestDto;
import com.app.url_shortener.entities.User;
import com.app.url_shortener.model.Role;
import com.app.url_shortener.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public User register(UserRegisterRequestDto registerRequestDto){
//        Check if email already present in DB
        if (userRepository.findByEmail(registerRequestDto.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already register. Please log in");
        }
//        Create User object
        User user = new User();
        user.setName(registerRequestDto.name());
        user.setEmail(registerRequestDto.email());

//        Encrypt Password
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));

        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

    public User login(UserLogInDto logInDto){
        User user = userRepository.findByEmail(logInDto.email())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(logInDto.password(),user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        return user;
    }
}
