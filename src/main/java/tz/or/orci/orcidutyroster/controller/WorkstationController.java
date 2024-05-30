package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.request.WorkstationDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/work-stations")
@Tag(name = "Workstation Controller")
@SecurityRequirement(name = "jwt")
public interface WorkstationController {
    @PostMapping()
    @Operation(summary = "Add Workstation", description = "Admin Protected")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Workstation> addWorkstation(@Valid @RequestBody WorkstationDto workstationDto);

    @GetMapping("{id}")
    @Operation(summary = "Get Workstation by Id")
    ResponseEntity<Workstation> getWorkstationById(@PathVariable @Valid Long id);

    @GetMapping()
    @Operation(summary = "Get All Workstations")
    ResponseEntity<GenericResponse<Workstation>> getAllWorkstations(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update Workstation", description = "Admin Protected")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Workstation> updateWorkstation(
            @Valid @PathVariable Long id,
            @Valid @RequestBody WorkstationDto workstationDto
    );
}
