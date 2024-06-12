package tz.or.orci.orcidutyroster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.repository.UserDesignationRepository;
import tz.or.orci.orcidutyroster.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class DataInitService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final DepartmentService departmentService;
    private final ShiftService shiftService;
    private final WorkstationService workstationService;
    private final RoleService roleService;
    private final UserDesignationService userDesignationService;
    private final UserDesignationRepository userDesignationRepository;

//    public void addDefaultRoles() {
//        roleService.addRole(ADMIN);
//        roleService.addRole(HEAD_OF_DEPARTMENT);
//        roleService.addRole(SUPERVISOR);
//        roleService.addRole(STAFF);
//        roleService.addRole(VOLUNTEER);
//        roleService.addRole(INTERN);
//    }
//
//    public void addDefaultUsers() {
//        if (!userRepository.existsByUsernameIgnoreCase("james.bond")) {
//            RegisterByAdminRequestDto jamesBondRequest = RegisterByAdminRequestDto
//                    .builder()
//                    .username("James.bond")
//                    .fullName("James Bond")
//                    .departmentName(DEFAULT_DEPARTMENT)
//                    .roles(List.of(STAFF, ADMIN))
//                    .workstationNames(List.of(DEFAULT_WORKSTATION))
//                    .userDesignationId(1L)
//                    .build();
//            authService.registerByAdmin(jamesBondRequest);
//        }
//    }
//
//    public void addDefaultDepartments() {
////        departmentService.addDepartment(DEFAULT_DEPARTMENT);
////        departmentService.addDepartment(DEFAULT_DEPARTMENT2);
//    }
//
//    public void assignShiftsToDepartment() {
//        departmentService.assignShifts(DEFAULT_DEPARTMENT, List.of(DEFAULT_SHIFT));
//    }
//
//    public void assignUserDesignationsToDepartment() {
//        departmentService.assignUserDesignations(DEFAULT_DEPARTMENT, List.of(1L));
//    }
//
//    public void addDefaultWorkstations() {
//        workstationService.addWorkstation(DEFAULT_WORKSTATION, DEFAULT_DEPARTMENT);
//    }
//
//    public void addDefaultShifts() {
//        shiftService.addShift(DEFAULT_SHIFT);
//    }
//
//    public void addDefaultUserDesignations() {
//        if (!userDesignationRepository.existsByName("Default User Designation")) {
//            userDesignationService.addUserDesignation(UserDesignationDto.builder().name("Default User Designation").build());
//        }
//    }
}
