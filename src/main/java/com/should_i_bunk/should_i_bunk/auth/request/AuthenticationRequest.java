package com.should_i_bunk.should_i_bunk.auth.request;

import com.should_i_bunk.should_i_bunk.auth.AuthenticationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "Please Enter Your password")
    private String password;
    @Email(message = "Please Enter a Valid Email")
    @NotBlank(message = "Please Enter Your Email")
    private String email;

}
