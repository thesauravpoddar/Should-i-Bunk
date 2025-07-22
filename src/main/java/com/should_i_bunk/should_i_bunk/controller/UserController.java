package com.should_i_bunk.should_i_bunk.controller;

import com.should_i_bunk.should_i_bunk.user.User;
import com.should_i_bunk.should_i_bunk.user.UserService;
import com.should_i_bunk.should_i_bunk.user.request.ChangePasswordRequest;
import com.should_i_bunk.should_i_bunk.user.request.ProfileUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;
    @PatchMapping("/me")
    @ResponseStatus(code = NO_CONTENT)
    public void updateProfileInfo(@RequestBody
                                       @Valid
                                       final String userId,
                                  final ProfileUpdateRequest request,
                                  final Authentication principal
                                  ) {

        // This method will handle the profile update logic.
        // The actual implementation will depend on the request body and how you want to process it.
        // For now, it's just a placeholder.
        this.userService.updateProfileInfo(request , getUserId(principal));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = NO_CONTENT)
    public void updatePassword(@RequestBody
                               @Valid
                               final ChangePasswordRequest request,
                               final Authentication principal
                               ) {
                // here we have to write the implementation for updating the password or what we call the logic but we have defined the the method for changing the password in UserService class and the implementation of that or logic for that in UserServiceImpl class we are doing this to keep the codes and classes clean
        // ultimately we can define the whole logic here but since we have already defined we will just call that class UserService
        this.userService.changePassword(request , getUserId(principal));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = NO_CONTENT)
    public void decativateAccount(
                    final Authentication principal
    ) {
        this.userService.deactivateAccount(getUserId(principal));
    }

    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = NO_CONTENT)
    public void reactivateAccount(
            final Authentication principal
    ) {
        this.userService.reactivateAccount(getUserId(principal));
    }

    @DeleteMapping("/me")
    @ResponseStatus(code = NO_CONTENT)
        public void deleteAccount(
                final Authentication principal
        ) {
        this.userService.deleteAccount(getUserId(principal));
        }


    private String getUserId(final Authentication principal) {
        // Assuming the authentication object contains the user ID in its principal.
        // Adjust this method based on your authentication setup.
        return ((User)principal.getPrincipal()).getId(); // or however you retrieve the user ID
    }

}
