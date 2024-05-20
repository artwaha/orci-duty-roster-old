package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tz.or.orci.orcidutyroster.payload.request.LoginRequestDto;
import tz.or.orci.orcidutyroster.payload.request.RegisterByAdminRequestDto;
import tz.or.orci.orcidutyroster.payload.request.SelfRegisterRequestDto;
import tz.or.orci.orcidutyroster.payload.response.AuthResponseDto;
import tz.or.orci.orcidutyroster.payload.response.UserDto;

@RequestMapping("api/v1/auth")
@Tag(name = "Auth controller")
public interface AuthController {
    @PostMapping("logout")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Logout")
    void logout();

    @PostMapping("login")
    @Operation(summary = "Login")
    ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequest);

    @PostMapping("self-register")
    @Operation(summary = "Self-registration")
    ResponseEntity<UserDto> selfRegister(@RequestBody @Valid SelfRegisterRequestDto registerRequest);

    @PostMapping("register-by-admin")
    @Operation(summary = "Register by Admin", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDto> registerByAdmin(@RequestBody @Valid RegisterByAdminRequestDto registerRequest);
}
