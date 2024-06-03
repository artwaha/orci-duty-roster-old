package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.payload.request.ShiftDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/shifts")
@Tag(name = "Shift Controller")
public interface ShiftController {
    @GetMapping()
    @Operation(summary = "Get All Shifts")
    ResponseEntity<GenericResponse<Shift>> getAllShifts(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update Shift", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Shift> updateShift(
            @Valid @PathVariable Long id,
            @Valid @RequestBody ShiftDto shiftDto
    );
}
