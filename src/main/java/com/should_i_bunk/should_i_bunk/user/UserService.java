package com.should_i_bunk.should_i_bunk.user;

import com.should_i_bunk.should_i_bunk.user.request.ChangePasswordRequest;
import com.should_i_bunk.should_i_bunk.user.request.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {

    void updateProfileInfo(ProfileUpdateRequest request , String userId);

    void changePassword(ChangePasswordRequest request , String userId);

    void deactivateAccount(String userId);

    void deleteAccount(String userId);

    void reactivateAccount(String userId);

}
