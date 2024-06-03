package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/work-stations")
@Tag(name = "Workstation Controller")
public interface WorkstationController {
    @GetMapping()
    @Operation(summary = "Get All Workstations")
    ResponseEntity<GenericResponse<Workstation>> getAllWorkstations(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );
}
