package com.alibu2.spring_security_asymetric_encryption2.auth.impl;

import com.alibu2.spring_security_asymetric_encryption2.auth.AuthenticationService;
import com.alibu2.spring_security_asymetric_encryption2.auth.request.AuthenticationRequest;
import com.alibu2.spring_security_asymetric_encryption2.auth.request.RefreshRequest;
import com.alibu2.spring_security_asymetric_encryption2.auth.request.RegistrationRequest;
import com.alibu2.spring_security_asymetric_encryption2.auth.response.AuthenticationResponse;
import com.alibu2.spring_security_asymetric_encryption2.exception.BusinessException;
import com.alibu2.spring_security_asymetric_encryption2.exception.ErrorCode;
import com.alibu2.spring_security_asymetric_encryption2.role.Role;
import com.alibu2.spring_security_asymetric_encryption2.role.RoleRepository;
import com.alibu2.spring_security_asymetric_encryption2.security.JwtService;
import com.alibu2.spring_security_asymetric_encryption2.user.User;
import com.alibu2.spring_security_asymetric_encryption2.user.UserMapper;
import com.alibu2.spring_security_asymetric_encryption2.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        final Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final User user = (User) auth.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user odes not exist"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        this.roleRepository.save(userRole);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }

    private void checkUserEmail(String email) {
        final boolean emailExists = this.userRepository.existsByEmailIgnoreCase(email);
        if (emailExists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumber(phoneNumber);
        if (phoneNumberExists) {
            throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }


}
