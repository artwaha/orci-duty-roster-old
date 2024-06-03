package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.model.entities.User;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;
import tz.or.orci.orcidutyroster.payload.request.UserUpdateRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.payload.response.UserDto;
import tz.or.orci.orcidutyroster.payload.response.ValidationResult;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.RoleRepository;
import tz.or.orci.orcidutyroster.repository.UserDesignationRepository;
import tz.or.orci.orcidutyroster.repository.UserRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static tz.or.orci.orcidutyroster.model.enums.RoleEnum.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Utils utils;
    private final DepartmentRepository departmentRepository;
    private final UserDesignationRepository userDesignationRepository;
    private final RoleRepository roleRepository;

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found."));
        return utils.userEntityToUserDto(user);
    }

    public GenericResponse<UserDto> getUsers(Boolean active, int pageNumber, int pageSize) {
        User authenticatedUser = utils.getAuthenticatedUser();
        List<RoleEnum> userRoles = authenticatedUser.getRoles().stream().map(Role::getName).toList();
        Department department = authenticatedUser.getDepartment();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> userPage;

        if (userRoles.size() == 2) {
            // This user is ADMIN
            if (active != null)
                userPage = userRepository.findByActive(active, pageable);
            else
                userPage = userRepository.findAll(pageable);
        } else {
            RoleEnum userRole = userRoles.get(0);
            switch (userRole) {
                case HEAD_OF_DEPARTMENT -> {
                    // Fetch All Supervisors in his Department
                    if (active == null)
                        userPage = userRepository.findByRoles_NameAndDepartment(SUPERVISOR, department, pageable);
                    else
                        userPage = userRepository.findByRoles_NameAndDepartmentAndActive(SUPERVISOR, department, active, pageable);
                }
                case SUPERVISOR -> {
                    // Fetch All users in his Section
                    if (active == null)
                        userPage = userRepository.findByRoles_NameInAndDepartment(List.of(INTERN, VOLUNTEER, STAFF), department, pageable);
                    else
                        userPage = userRepository.findByRoles_NameInAndDepartmentAndActive(List.of(INTERN, VOLUNTEER, STAFF), department, active, pageable);
                }
                default -> throw new IllegalStateException("Unexpected value: " + userRole);
            }
        }

        List<UserDto> data = userPage.getContent().stream().map(utils::userEntityToUserDto).toList();

        return utils.generateGenericResponse(
                OK.value(),
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalPages(),
                userPage.getTotalElements(),
                data
        );
    }

    public UserDto updateUser(Long userId, UserUpdateRequestDto userUpdateRequest) {
        User savedUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with Id " + userId + " not found"));

        if (userUpdateRequest.getFullName() != null)
            savedUser.setFullName(userUpdateRequest.getFullName());

        if (userUpdateRequest.getActive() != null)
            savedUser.setActive(userUpdateRequest.getActive());

        if (userUpdateRequest.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userUpdateRequest.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department with Id " + userUpdateRequest.getDepartmentId() + " not found"));
            savedUser.setDepartment(department);
        }

        if (userUpdateRequest.getUserCategoryId() != null) {
            UserDesignation userDesignation = userDesignationRepository.findById(userUpdateRequest.getUserCategoryId()).orElseThrow(() -> new EntityNotFoundException("User Category with Id " + userUpdateRequest.getUserCategoryId() + " not found"));
            savedUser.setUserDesignation(userDesignation);
        }

        if (userUpdateRequest.getRoles() != null) {
            ValidationResult roleValidationResult = utils.validateRoles(userUpdateRequest.getRoles());
            if (!roleValidationResult.isValid()) {
                throw new CustomException(roleValidationResult.getMessage());
            }

            List<Role> userRoles = userUpdateRequest
                    .getRoles()
                    .stream()
                    .map(roleRepository::findByName)
                    .toList();

            savedUser.getRoles().clear();
            savedUser.getRoles().addAll(userRoles);
        }

        User updatedUser = userRepository.save(savedUser);
        return utils.userEntityToUserDto(updatedUser);
    }
}
