package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.payload.request.UserUpdateRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.payload.response.UserDto;

@RequestMapping("api/v1/users")
@Tag(name = "User Controller")
@SecurityRequirement(name = "jwt")
public interface UserController {
    @GetMapping("{id}")
    @Operation(summary = "Get User by Id", description = "Admin Protected")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDto> getUserById(@PathVariable @Valid Long id);

    @GetMapping("users")
    @Operation(summary = "Get Users", description = "Admin, Supervisor & HOD Protected")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','HEAD_OF_DEPARTMENT')")
    ResponseEntity<GenericResponse<UserDto>> getUsers(
            @Valid @RequestParam(required = false) Boolean active,
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update User", description = "Admin, Supervisor & HOD Protected")
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','HEAD_OF_DEPARTMENT')")
    ResponseEntity<UserDto> updateUser(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto updateRequest
    );
}
