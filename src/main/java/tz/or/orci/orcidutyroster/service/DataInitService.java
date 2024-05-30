package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.payload.request.DepartmentRequestDto;
import tz.or.orci.orcidutyroster.payload.request.RegisterByAdminRequestDto;
import tz.or.orci.orcidutyroster.payload.request.ShiftDto;
import tz.or.orci.orcidutyroster.payload.request.WorkstationDto;
import tz.or.orci.orcidutyroster.repository.*;

import java.util.List;

import static tz.or.orci.orcidutyroster.model.enums.DepartmentEnum.DEFAULT_DEPARTMENT;
import static tz.or.orci.orcidutyroster.model.enums.RoleEnum.*;
import static tz.or.orci.orcidutyroster.model.enums.ShiftEnum.DEFAULT_SHIFT;
import static tz.or.orci.orcidutyroster.model.enums.WorkstationEnum.DEFAULT_WORKSTATION;

@Service
@RequiredArgsConstructor
public class DataInitService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;
    private final ShiftRepository shiftRepository;
    private final ShiftService shiftService;
    private final WorkstationRepository workstationRepository;
    private final WorkstationService workstationService;

    public void addDefaultRoles() {
        if (!roleRepository.existsByName(ADMIN))
            roleRepository.save(Role.builder().name(ADMIN).description("Admin").build());

        if (!roleRepository.existsByName(HEAD_OF_DEPARTMENT))
            roleRepository.save(Role.builder().name(HEAD_OF_DEPARTMENT).description("Head of Department").build());

        if (!roleRepository.existsByName(SUPERVISOR))
            roleRepository.save(Role.builder().name(SUPERVISOR).description("Supervisor").build());

        if (!roleRepository.existsByName(STAFF))
            roleRepository.save(Role.builder().name(STAFF).description("Staff").build());

        if (!roleRepository.existsByName(VOLUNTEER))
            roleRepository.save(Role.builder().name(VOLUNTEER).description("Volunteer").build());

        if (!roleRepository.existsByName(INTERN))
            roleRepository.save(Role.builder().name(INTERN).description("Intern").build());
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

    public void addDefaultDepartments() {
        if (!departmentRepository.existsByName(DEFAULT_DEPARTMENT)) {
            departmentService.addDepartment(DepartmentRequestDto.builder().name("Default Department").build());
        }
    }

    public void addDefaultShifts() {
        if (!shiftRepository.existsByName(DEFAULT_SHIFT))
            shiftService.addShift(ShiftDto.builder().name(DEFAULT_SHIFT).description("Default Shit").build());
    }

    public void addDefaultWorkstations() {
        if (!workstationRepository.existsByName(DEFAULT_WORKSTATION))
            workstationService.addWorkstation(WorkstationDto.builder().name(DEFAULT_WORKSTATION).description("Default Workstation").build());
    }

}
