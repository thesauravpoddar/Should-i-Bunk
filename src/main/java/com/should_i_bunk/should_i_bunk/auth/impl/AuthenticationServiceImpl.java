package com.should_i_bunk.should_i_bunk.auth.impl;

import com.should_i_bunk.should_i_bunk.auth.AuthenticationService;
import com.should_i_bunk.should_i_bunk.auth.request.AuthenticationRequest;
import com.should_i_bunk.should_i_bunk.auth.request.RefreshRequest;
import com.should_i_bunk.should_i_bunk.auth.request.RegistrationRequest;
import com.should_i_bunk.should_i_bunk.auth.response.AuthenticationResponse;
import com.should_i_bunk.should_i_bunk.exceptionHandling.BusinessException;
import com.should_i_bunk.should_i_bunk.exceptionHandling.ErrorCode;
import com.should_i_bunk.should_i_bunk.role.Role;
import com.should_i_bunk.should_i_bunk.role.RoleRepository;
import com.should_i_bunk.should_i_bunk.security.JwtService;
import com.should_i_bunk.should_i_bunk.user.User;
import com.should_i_bunk.should_i_bunk.user.UserRepository;
import com.should_i_bunk.should_i_bunk.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthenticationResponse login(final AuthenticationRequest request) {
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
        checkPassword(request.getPassword() , request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exist"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        final User user = this.userMapper.toUser(request);
       user.setRoles(roles);
       log.debug("saving user {}" , user);
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

    private void checkPassword(String password , String confirmPassword) {
        if(password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
