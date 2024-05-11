package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.DepartmentController;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.payload.request.DepartmentRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.DepartmentService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class DepartmentControllerImpl implements DepartmentController {
    private final DepartmentService departmentService;

    @Override
    public ResponseEntity<Department> addDepartment(DepartmentRequestDto department) {
        return ResponseEntity.status(CREATED).body(departmentService.addDepartment(department));
    }

    @Override
    public ResponseEntity<Department> getDepartmentById(Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @Override
    public ResponseEntity<GenericResponse<Department>> getAllDepartments(int pageNumber, int pageSize) {
        return ResponseEntity.ok(departmentService.getAllDepartments(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<Department> updateDepartment(Long id, DepartmentRequestDto department) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }
}