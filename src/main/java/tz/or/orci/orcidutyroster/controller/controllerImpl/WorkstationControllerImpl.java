package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.WorkstationController;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.request.WorkstationDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.WorkstationService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class WorkstationControllerImpl implements WorkstationController {
    private final WorkstationService workstationService;

    @Override
    public ResponseEntity<Workstation> addWorkstation(WorkstationDto workstationDto) {
        return ResponseEntity.status(CREATED).body(workstationService.addWorkstation(workstationDto));
    }

    @Override
    public ResponseEntity<Workstation> getWorkstationById(Long id) {
        return ResponseEntity.ok(workstationService.getWorkstationById(id));
    }

    @Override
    public ResponseEntity<GenericResponse<Workstation>> getAllWorkstations(int pageNumber, int pageSize) {
        return ResponseEntity.ok(workstationService.getAllWorkstations(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<GenericResponse<Workstation>> getAllWorkstationsByDepartment(Long id, int pageNumber, int pageSize) {
        return ResponseEntity.ok(workstationService.getAllWorkstationsByDepartment(id, pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<Workstation> updateWorkstation(Long id, WorkstationDto workstationDto) {
        return ResponseEntity.ok(workstationService.updateWorkstation(id, workstationDto));
    }
}
