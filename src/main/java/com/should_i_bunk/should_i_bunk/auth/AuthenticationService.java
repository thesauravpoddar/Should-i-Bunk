package com.should_i_bunk.should_i_bunk.auth;

import com.should_i_bunk.should_i_bunk.auth.request.AuthenticationRequest;
import com.should_i_bunk.should_i_bunk.auth.request.RefreshRequest;
import com.should_i_bunk.should_i_bunk.auth.request.RegistrationRequest;
import com.should_i_bunk.should_i_bunk.auth.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest req);
}
