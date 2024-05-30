package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.payload.request.ShiftDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.ShiftRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final DepartmentRepository departmentRepository;
    private final ShiftRepository shiftRepository;
    private final ModelMapper modelMapper;
    private final Utils utils;

    public Shift addShift(ShiftDto shiftDto) {
        Optional<Shift> shiftOptional = shiftRepository.findByName(shiftDto.getName());
        if (shiftOptional.isPresent())
            throw new CustomException("Shift with name " + shiftDto.getName() + " already Exists");

        Shift shift = modelMapper.map(shiftDto, Shift.class);

        return shiftRepository.save(shift);
    }

    public Shift getShiftById(Long shiftId) {
        return shiftRepository.findById(shiftId).orElseThrow(() -> new EntityNotFoundException("Shift with Id " + shiftId + " not found"));
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

        if (shiftDto.getDescription() != null)
            savedShift.setDescription(shiftDto.getDescription());

        if (shiftDto.getInformation() != null)
            savedShift.setInformation(shiftDto.getInformation());

        if (shiftDto.getName() != null) {
            Optional<Shift> shiftOptional = shiftRepository.findByName(shiftDto.getName());
            if (shiftOptional.isPresent())
                throw new CustomException("Shift with name " + shiftDto.getName() + " already Exists");
            savedShift.setName(shiftDto.getName());
        }

        return shiftRepository.save(savedShift);
    }
}
