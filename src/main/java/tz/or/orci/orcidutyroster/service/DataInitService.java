package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.payload.request.DepartmentRequestDto;
import tz.or.orci.orcidutyroster.payload.request.RegisterByAdminRequestDto;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.RoleRepository;
import tz.or.orci.orcidutyroster.repository.UserRepository;

import java.util.List;

import static tz.or.orci.orcidutyroster.model.enums.RoleEnum.*;

@Service
@RequiredArgsConstructor
public class DataInitService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;

    public void addDefaultRoles() {
        if (!roleRepository.existsByName(ADMIN))
            roleRepository.save(Role.builder().name(ADMIN).description(ADMIN.name()).build());

        if (!roleRepository.existsByName(HEAD_OF_DEPARTMENT))
            roleRepository.save(Role.builder().name(HEAD_OF_DEPARTMENT).description(HEAD_OF_DEPARTMENT.name()).build());

        if (!roleRepository.existsByName(SUPERVISOR))
            roleRepository.save(Role.builder().name(SUPERVISOR).description(SUPERVISOR.name()).build());

        if (!roleRepository.existsByName(STAFF))
            roleRepository.save(Role.builder().name(STAFF).description(STAFF.name()).build());

        if (!roleRepository.existsByName(VOLUNTEER))
            roleRepository.save(Role.builder().name(VOLUNTEER).description(VOLUNTEER.name()).build());

        if (!roleRepository.existsByName(INTERN))
            roleRepository.save(Role.builder().name(INTERN).description(INTERN.name()).build());
    }

    public void addDefaultDepartments() {
        if (!departmentRepository.existsByNameIgnoreCase("Default Department")) {
            departmentService.addDepartment(
                    DepartmentRequestDto
                            .builder()
                            .name("Default Department")
                            .build()
            );
        }
    }

    public void addDefaultUsers() {
        if (!userRepository.existsByUsernameIgnoreCase("james.bond")) {
            Department department = departmentRepository.findByNameIgnoreCase("Default Department").orElseThrow(() -> new EntityNotFoundException("Department with Name 'Default Department' not found"));
            RegisterByAdminRequestDto jamesBondRequest = RegisterByAdminRequestDto
                    .builder()
                    .username("James.bond")
                    .fullName("James Bond")
                    .departmentId(department.getId())
                    .roles(List.of(STAFF, ADMIN))
                    .build();
            authService.registerByAdmin(jamesBondRequest);
        }
    }
}
