package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.model.enums.DepartmentEnum;
import tz.or.orci.orcidutyroster.model.enums.ShiftEnum;
import tz.or.orci.orcidutyroster.payload.request.DepartmentRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.ShiftRepository;
import tz.or.orci.orcidutyroster.repository.UserDesignationRepository;
import tz.or.orci.orcidutyroster.repository.WorkstationRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final Utils utils;
    private final ShiftRepository shiftRepository;
    private final UserDesignationRepository userDesignationRepository;
    private final WorkstationRepository workstationRepository;

    public void addDepartment(DepartmentEnum departmentName) {
        if (!departmentRepository.existsByName(departmentName)) {
            Department department = Department.builder().name(departmentName).build();
            departmentRepository.save(department);
        }
    }

    public GenericResponse<Department> getAllDepartments(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Department> departmentsPage = departmentRepository.findAll(pageable);
        return utils.generateGenericResponse(
                OK.value(),
                departmentsPage.getNumber(),
                departmentsPage.getSize(),
                departmentsPage.getTotalPages(),
                departmentsPage.getTotalElements(),
                departmentsPage.getContent()
        );
    }

    public Department getDepartmentById(Long departmentID) {
        return departmentRepository.findById(departmentID).orElseThrow(() -> new EntityNotFoundException("Department  with Id " + departmentID + " not found"));
    }

    public Department updateDepartment(Long departmentId, DepartmentRequestDto departmentRequestDto) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isEmpty()) {
            throw new EntityNotFoundException("Department with id " + departmentId + " not found");
        }

        Department savedDepartment = departmentOptional.get();

        if (departmentRequestDto.getUserDesignationIDs() != null) {
            List<UserDesignation> userDesignationList = departmentRequestDto.getUserDesignationIDs().stream().map(id -> userDesignationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invalid User Designation Id " + id))).toList();
            savedDepartment.getUserDesignations().clear();
            savedDepartment.getUserDesignations().addAll(userDesignationList);
        }

        if (departmentRequestDto.getWorkstationNames() != null) {
            List<Workstation> workstationList = departmentRequestDto.getWorkstationNames().stream().map(workstationRepository::findByName).toList();
            savedDepartment.getWorkstations().clear();
            savedDepartment.getWorkstations().addAll(workstationList);
        }

        if (departmentRequestDto.getShiftNames() != null) {
            List<Shift> shiftList = departmentRequestDto.getShiftNames().stream().map(shiftRepository::findByName).toList();
            savedDepartment.getShifts().clear();
            savedDepartment.getShifts().addAll(shiftList);
        }

        return departmentRepository.save(savedDepartment);
    }

    public void assignShifts(DepartmentEnum departmentName, List<ShiftEnum> shiftEnums) {
        if (departmentRepository.existsByName(departmentName)) {
            Department department = departmentRepository.findDepartmentByName(departmentName);
            List<Shift> shifts = shiftEnums.stream().map(shiftRepository::findByName).toList();
            department.setShifts(shifts);
            departmentRepository.save(department);
        }
    }

    public void assignUserDesignations(DepartmentEnum departmentName, List<Long> userDesignations) {
        if (departmentRepository.existsByName(departmentName)) {
            Department department = departmentRepository.findDepartmentByName(departmentName);
            List<UserDesignation> userDesignationList = userDesignations.stream().map(userDesignationId -> userDesignationRepository.findById(userDesignationId).orElseThrow(() -> new EntityNotFoundException("Invalid User Designation Id [" + userDesignationId + "]"))).toList();
            department.setUserDesignations(userDesignationList);
            departmentRepository.save(department);
        }
    }
}
