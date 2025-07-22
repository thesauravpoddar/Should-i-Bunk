package com.should_i_bunk.should_i_bunk.user.impl;

import com.should_i_bunk.should_i_bunk.exceptionHandling.BusinessException;
import com.should_i_bunk.should_i_bunk.exceptionHandling.ErrorCode;
import com.should_i_bunk.should_i_bunk.user.User;
import com.should_i_bunk.should_i_bunk.user.UserRepository;
import com.should_i_bunk.should_i_bunk.user.UserService;
import com.should_i_bunk.should_i_bunk.user.mapper.UserMapper;
import com.should_i_bunk.should_i_bunk.user.request.ChangePasswordRequest;
import com.should_i_bunk.should_i_bunk.user.request.ProfileUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public void updateProfileInfo(ProfileUpdateRequest request, String userId) {
        // first get that user from database
        User saveduser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND , userId ));

        //this.userMapper.mergerUserInfo(saveduser, request); we have defined the same thing in mapper class and calling we did this to keep clean our userServiceimplementation
        // basic for all the service that wer are provideing to the user we habe to define the implementation and logics here but to keep clean this class and we, our defining in mapper class
        if(StringUtils.isNotBlank(request.getFirstName())
                &&!saveduser.getFirstName().equals(request.getFirstName())) {
            saveduser.setFirstName(request.getFirstName());
        }
        if(StringUtils.isNotBlank(request.getLastName())
                &&!saveduser.getLastName().equals(request.getLastName())) {
            saveduser.setLastName(request.getLastName());
        }
        this.userRepository.save(saveduser);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }
        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND , userId ));

        if(!this.passwordEncoder.matches(request.getOldPassword(), savedUser.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_OLD_PASSWORD);
        }
        final String encodedNewPassword = this.passwordEncoder.encode(request.getNewPassword());
        savedUser.setPassword(encodedNewPassword);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deactivateAccount(String userId) {
        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND , userId ));
        // Logic for deactivating the account can be implemented here
        if(!savedUser.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED, userId);
        }
        savedUser.setEnabled(false);
        this.userRepository.save(savedUser);
    }

    @Override
    public void reactivateAccount(String userId) {
        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND , userId ));
        // Logic for deactivating the account can be implemented here
        if(savedUser.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED, userId);
        }
        savedUser.setEnabled(true);
        this.userRepository.save(savedUser);
    }
    @Override
    public void deleteAccount(String userId) {


    }
}
