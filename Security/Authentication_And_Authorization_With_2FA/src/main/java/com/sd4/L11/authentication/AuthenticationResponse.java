package com.sd4.L11.authentication;

import lombok.Data;

/*
This class is used to send the generated JWT back to the client after
successful authentication.
 */
@Data
public class AuthenticationResponse {
    private final String accessToken;
    private final String refreshToken; //i added a field here for the refresh token

}