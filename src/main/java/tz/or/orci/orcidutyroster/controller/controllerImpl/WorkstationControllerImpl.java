package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.WorkstationController;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.WorkstationService;

@RestController
@RequiredArgsConstructor
public class WorkstationControllerImpl implements WorkstationController {
    private final WorkstationService workstationService;

    @Override
    public ResponseEntity<GenericResponse<Workstation>> getAllWorkstations(int pageNumber, int pageSize) {
        return ResponseEntity.ok(workstationService.getAllWorkstations(pageNumber, pageSize));
    }

}
