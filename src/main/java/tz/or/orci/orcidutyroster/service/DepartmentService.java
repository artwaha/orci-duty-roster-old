package tz.or.orci.orcidutyroster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.enums.DepartmentEnum;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final Utils utils;

    public void addDepartment(DepartmentEnum departmentEnum) {
        if (!departmentRepository.existsByName(departmentEnum)) {
            Department department = Department.builder().name(departmentEnum).build();
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
}
