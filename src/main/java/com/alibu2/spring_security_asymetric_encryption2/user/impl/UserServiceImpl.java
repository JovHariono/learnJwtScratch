package com.alibu2.spring_security_asymetric_encryption2.user.impl;

import com.alibu2.spring_security_asymetric_encryption2.exception.BusinessException;
import com.alibu2.spring_security_asymetric_encryption2.exception.ErrorCode;
import com.alibu2.spring_security_asymetric_encryption2.user.User;
import com.alibu2.spring_security_asymetric_encryption2.user.UserMapper;
import com.alibu2.spring_security_asymetric_encryption2.user.UserRepository;
import com.alibu2.spring_security_asymetric_encryption2.user.UserService;
import com.alibu2.spring_security_asymetric_encryption2.user.request.ChangePasswordRequest;
import com.alibu2.spring_security_asymetric_encryption2.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public void updateProfileInfo(ProfileUpdateRequest request, String userId) {
        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        this.userMapper.mergerUserInfo(savedUser, request);
        this.userRepository.save(savedUser);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }

        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if (!this.passwordEncoder.matches(request.getCurrentPassword(), savedUser.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        final String encoded = this.passwordEncoder.encode(request.getNewPassword());
        savedUser.setPassword(encoded);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deactivateAccount(String userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!user.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        user.setEnabled(false);
        this.userRepository.save(user);
    }

    @Override
    public void reactivateAccount(String userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(user.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_ACTIVE);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(String userId) {
        // this method need the rest of the entites
        // the logic is just to schedule a profile for deletion
        // and the scheduled job will pick the profiles and delete everything


    }

    @Override
    public UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));
    }
}
