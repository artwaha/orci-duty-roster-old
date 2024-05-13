package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.ShiftController;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.payload.request.ShiftDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.ShiftService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class ShiftControllerImpl implements ShiftController {
    private final ShiftService shiftService;

    @Override
    public ResponseEntity<Shift> addShift(ShiftDto shiftDto) {
        return ResponseEntity.status(CREATED).body(shiftService.addShift(shiftDto));
    }

    @Override
    public ResponseEntity<Shift> getShiftById(Long id) {
        return ResponseEntity.ok(shiftService.getShiftById(id));
    }

    @Override
    public ResponseEntity<GenericResponse<Shift>> getAllShifts(int pageNumber, int pageSize) {
        return ResponseEntity.ok(shiftService.getAllShifts(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<GenericResponse<Shift>> getAllShiftsByDepartment(Long id, int pageNumber, int pageSize) {
        return ResponseEntity.ok(shiftService.getAllShiftsByDepartment(id, pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<Shift> updateShift(Long id, ShiftDto shiftDto) {
        return ResponseEntity.ok(shiftService.updateShift(id, shiftDto));
    }
}
