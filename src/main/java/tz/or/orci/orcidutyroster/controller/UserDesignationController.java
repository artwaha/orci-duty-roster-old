package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;
import tz.or.orci.orcidutyroster.payload.request.UserDesignationDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/user-designations")
@Tag(name = "User Designations Controller")
public interface UserDesignationController {
    @PostMapping()
    @Operation(summary = "Add User Designation", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDesignation> addUserDesignation(@Valid @RequestBody UserDesignationDto userDesignationDto);

    @GetMapping("{id}")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get User Designation by Id")
    ResponseEntity<UserDesignation> getUserCategoryById(@PathVariable @Valid Long id);

    @GetMapping()
    @Operation(summary = "Get All User Designations")
    ResponseEntity<GenericResponse<UserDesignation>> getAllUserCategories(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update User Category", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDesignation> updateUserCategory(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UserDesignationDto userDesignationDto
    );
}
