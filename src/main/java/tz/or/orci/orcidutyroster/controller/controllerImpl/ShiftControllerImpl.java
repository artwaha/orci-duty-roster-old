package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.ShiftController;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.payload.request.ShiftDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.ShiftService;

@RestController
@RequiredArgsConstructor
public class ShiftControllerImpl implements ShiftController {
    private final ShiftService shiftService;

    @Override
    public ResponseEntity<GenericResponse<Shift>> getAllShifts(int pageNumber, int pageSize) {
        return ResponseEntity.ok(shiftService.getAllShifts(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<Shift> updateShift(Long id, ShiftDto shiftDto) {
        return ResponseEntity.ok(shiftService.updateShift(id, shiftDto));
    }
}
