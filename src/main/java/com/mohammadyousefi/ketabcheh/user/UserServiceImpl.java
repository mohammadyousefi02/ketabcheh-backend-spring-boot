package com.mohammadyousefi.ketabcheh.user;

import com.mohammadyousefi.ketabcheh.auth.LoginDto;
import com.mohammadyousefi.ketabcheh.auth.SignupDto;
import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.profile.Profile;
import com.mohammadyousefi.ketabcheh.util.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Jwt jwt;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, Jwt jwt, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signUp(SignupDto signupDto) {
        Optional<User> user = userRepository.findByEmail(signupDto.getEmail());
        if (user.isPresent()) throw new BadRequestException("this email has been used before");
        User newUser = new User();
        newUser.setEmail(signupDto.getEmail());
        newUser.setUsername(signupDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        Profile profile = new Profile();
        profile.setUser(newUser);
        newUser.setProfile(profile);
        newUser = userRepository.save(newUser);
        return jwt.generateToken(newUser.getId(), newUser.getRole().toString().describeConstable());
    }

    @Override
    public String login(LoginDto loginDto, Boolean adminRoute) {
        String error = "email or password is incorrect";
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isEmpty()) throw new BadRequestException(error);
        if (!passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword()))
            throw new BadRequestException(error);
        if (Boolean.TRUE.equals(adminRoute) && !user.get().getRole().toString().equals("ADMIN"))
            throw new BadRequestException(error);
        return jwt.generateToken(user.get().getId(), user.get().getRole().toString().describeConstable());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String deleteById(Long id) {
        Optional<User> user = findById(id);
        if (user.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("user"));
        userRepository.deleteById(id);
        return "successfully deleted";
    }
}
