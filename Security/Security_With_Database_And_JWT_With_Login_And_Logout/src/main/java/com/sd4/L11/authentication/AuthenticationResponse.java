package com.sd4.L11.authentication;

import lombok.Data;

/*
This class is used to send the generated JWT back to the client after
successful authentication.
 */
@Data
public class AuthenticationResponse {
    private final String accessToken;
    private final String refreshToken; //i added this field to allow me to send the refresh token back in the response

}