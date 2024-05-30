package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.DepartmentController;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.DepartmentService;

@RestController
@RequiredArgsConstructor
public class DepartmentControllerImpl implements DepartmentController {
    private final DepartmentService departmentService;

    @Override
    public ResponseEntity<GenericResponse<Department>> getAllDepartments(int pageNumber, int pageSize) {
        return ResponseEntity.ok(departmentService.getAllDepartments(pageNumber, pageSize));
    }
}
