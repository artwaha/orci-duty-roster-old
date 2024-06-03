package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.model.enums.ShiftEnum;
import tz.or.orci.orcidutyroster.payload.request.ShiftDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.ShiftRepository;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final Utils utils;

    public void addShift(ShiftEnum shiftName) {
        if (!shiftRepository.existsByName(shiftName)) {
            Shift shift = Shift.builder().name(shiftName).claimable(true).build();
            shiftRepository.save(shift);
        }
    }

    public GenericResponse<Shift> getAllShifts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Shift> shiftPage = shiftRepository.findAll(pageable);
        return utils.generateGenericResponse(
                OK.value(),
                shiftPage.getNumber(),
                shiftPage.getSize(),
                shiftPage.getTotalPages(),
                shiftPage.getTotalElements(),
                shiftPage.getContent()
        );
    }

    public Shift updateShift(Long shiftId, ShiftDto shiftDto) {
        Shift savedShift = shiftRepository.findById(shiftId).orElseThrow(() -> new EntityNotFoundException("Shift with Id " + shiftId + " not found"));

        if (shiftDto.getClaimable() != null)
            savedShift.setClaimable(shiftDto.getClaimable());

        return shiftRepository.save(savedShift);
    }
}
