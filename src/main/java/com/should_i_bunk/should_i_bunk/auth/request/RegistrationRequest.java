package com.should_i_bunk.should_i_bunk.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    @NotBlank(message = "Please Enter Your First Name")
    private String firstName;

    @NotBlank(message = "Please Enter Your Last Name")
    private String lastName;
    @NotBlank(message = "Please Enter Your Email")
    @Email(message = "Please Enter a Valid Email")
    private String email;
    @NotBlank(message = "Please Enter Your Password")
    @Size(
            min = 8,
            max = 20
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must be 8-20 characters long, include at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    @Schema(example = "<PASSWORD>")
    private String password;
    @NotBlank(message = "Please Confirm Your Password")
    @Schema(example = "<PASSWORD>")
    private String confirmPassword;

}
