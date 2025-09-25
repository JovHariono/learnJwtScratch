package com.alibu2.spring_security_asymetric_encryption2.auth;

import com.alibu2.spring_security_asymetric_encryption2.auth.request.AuthenticationRequest;
import com.alibu2.spring_security_asymetric_encryption2.auth.request.RefreshRequest;
import com.alibu2.spring_security_asymetric_encryption2.auth.request.RegistrationRequest;
import com.alibu2.spring_security_asymetric_encryption2.auth.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
