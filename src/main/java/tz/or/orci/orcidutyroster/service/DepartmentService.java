package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.payload.request.DepartmentRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final Utils utils;

    public Department addDepartment(DepartmentRequestDto departmentRequestDto) {
        Optional<Department> departmentOptional = departmentRepository.findByNameIgnoreCase(departmentRequestDto.getName());
        if (departmentOptional.isPresent())
            throw new CustomException("Department with name " + departmentRequestDto.getName() + " Already Exists");
        Department department = modelMapper.map(departmentRequestDto, Department.class);
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFoundException("Department with Id " + departmentId + " not found"));
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

    public Department updateDepartment(Long departmentId, DepartmentRequestDto department) {
        Department savedDepartment = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFoundException("Department with Id " + departmentId + " not found"));
        Optional<Department> departmentOptional = departmentRepository.findByNameIgnoreCase(department.getName());
        if (departmentOptional.isPresent())
            throw new CustomException("Department with name " + department.getName() + " Already Exists");
        savedDepartment.setName(department.getName());
        return departmentRepository.save(savedDepartment);
    }
}
