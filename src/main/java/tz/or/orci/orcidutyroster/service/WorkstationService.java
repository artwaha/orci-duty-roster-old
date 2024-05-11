package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.request.WorkstationDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.WorkstationRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class WorkstationService {

    private final DepartmentRepository departmentRepository;
    private final WorkstationRepository workstationRepository;
    private final ModelMapper modelMapper;
    private final Utils utils;

    public Workstation addWorkstation(WorkstationDto workstationDto) {
        Department department = departmentRepository.findById(workstationDto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department with Id " + workstationDto.getDepartmentId() + " not found"));

        Optional<Workstation> workstationOptional = workstationRepository.findByNameIgnoreCase(workstationDto.getName());
        if (workstationOptional.isPresent())
            throw new CustomException("Workstation with name " + workstationDto.getName() + " Already Exists");

        Workstation workstation = modelMapper.map(workstationDto, Workstation.class);
        workstation.setDepartment(department);

        return workstationRepository.save(workstation);
    }

    public Workstation getWorkstationById(Long workstationId) {
        return workstationRepository.findById(workstationId).orElseThrow(() -> new EntityNotFoundException("Workstation with Id " + workstationId + " not found"));
    }

    public GenericResponse<Workstation> getAllWorkstations(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Workstation> workstationPage = workstationRepository.findAll(pageable);
        return utils.generateGenericResponse(
                OK.value(),
                workstationPage.getNumber(),
                workstationPage.getSize(),
                workstationPage.getTotalPages(),
                workstationPage.getTotalElements(),
                workstationPage.getContent()
        );
    }

    public GenericResponse<Workstation> getAllWorkstationsByDepartment(Long departmentId, int pageNumber, int pageSize) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFoundException("Department with Id " + departmentId + " not found"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Workstation> workstationPage = workstationRepository.findByDepartment(department, pageable);
        return utils.generateGenericResponse(
                OK.value(),
                workstationPage.getNumber(),
                workstationPage.getSize(),
                workstationPage.getTotalPages(),
                workstationPage.getTotalElements(),
                workstationPage.getContent()
        );
    }

    public Workstation updateWorkstation(Long workstationId, WorkstationDto workstationDto) {
        Workstation savedWorkstation = workstationRepository.findById(workstationId).orElseThrow(() -> new EntityNotFoundException("Workstation with Id " + workstationId + " not found"));

        if (workstationDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(workstationDto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department with Id " + workstationDto.getDepartmentId() + " not found"));
            savedWorkstation.setDepartment(department);
        }

        if (workstationDto.getName() != null) {
            Optional<Workstation> workstationOptional = workstationRepository.findByNameIgnoreCase(workstationDto.getName());
            if (workstationOptional.isPresent())
                throw new CustomException("Workstation with name " + workstationDto.getName() + " already Exists");
            savedWorkstation.setName(workstationDto.getName());
        }

        return workstationRepository.save(savedWorkstation);
    }
}
