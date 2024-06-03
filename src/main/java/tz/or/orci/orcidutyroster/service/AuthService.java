package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.*;
import tz.or.orci.orcidutyroster.payload.request.LoginRequestDto;
import tz.or.orci.orcidutyroster.payload.request.RegisterByAdminRequestDto;
import tz.or.orci.orcidutyroster.payload.request.SelfRegisterRequestDto;
import tz.or.orci.orcidutyroster.payload.response.AuthResponseDto;
import tz.or.orci.orcidutyroster.payload.response.UserDto;
import tz.or.orci.orcidutyroster.payload.response.ValidationResult;
import tz.or.orci.orcidutyroster.repository.*;
import tz.or.orci.orcidutyroster.security.CustomException;
import tz.or.orci.orcidutyroster.security.JwtService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ActiveDirectoryLdapAuthenticationProvider authenticationManager;
    private final JwtService jwtService;
    private final Utils utils;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final UserDesignationRepository userDesignationRepository;
    private final DepartmentRepository departmentRepository;
    private final WorkstationRepository workstationRepository;

    public AuthResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByUsernameIgnoreCase(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.generateToken(user);

        utils.revokeAllUserTokens(user);

        utils.saveUserToken(accessToken, user);

        return AuthResponseDto
                .builder()
                .accessToken(accessToken)
                .build();
    }

    public UserDto selfRegister(SelfRegisterRequestDto registerRequest) {

        if (!utils.userExistsInActiveDirectory(registerRequest.getUsername()))
            throw new EntityNotFoundException("Username " + registerRequest.getUsername() + " not found in Domain.");

        if (userRepository.existsByUsernameIgnoreCase(registerRequest.getUsername()))
            throw new CustomException("User with username " + registerRequest.getUsername() + " already exists");

        ValidationResult roleValidationResult = utils.validateRole(registerRequest.getRoleName());
        if (!roleValidationResult.isValid()) {
            throw new CustomException(roleValidationResult.getMessage());
        }

        User newUser = modelMapper.map(registerRequest, User.class);

        Role userRole = roleRepository.findByName(registerRequest.getRoleName());
        newUser.setRoles(List.of(userRole));

        Department department = departmentRepository.findByName(registerRequest.getDepartmentName());
        newUser.setDepartment(department);

        ValidationResult validateWorkstationResult = utils.validateWorkstations(registerRequest.getRoleName(), registerRequest.getWorkstationNames());
        if (!validateWorkstationResult.isValid()) {
            throw new CustomException(roleValidationResult.getMessage());
        }

        List<Workstation> workstationList = registerRequest.getWorkstationNames().stream().map(workstationRepository::findByName).toList();
        newUser.setWorkstations(workstationList);

        User savedUser = userRepository.save(newUser);

        return utils.userEntityToUserDto(savedUser);
    }

    public UserDto registerByAdmin(RegisterByAdminRequestDto registerRequest) {
        boolean usernameIsValid = utils.userExistsInActiveDirectory(registerRequest.getUsername());

        if (!usernameIsValid)
            throw new EntityNotFoundException("Username " + registerRequest.getUsername() + " not found in Domain.");

        boolean userExists = userRepository.existsByUsernameIgnoreCase(registerRequest.getUsername());

        if (userExists)
            throw new CustomException("User with username " + registerRequest.getUsername() + " already exists");

        ValidationResult roleValidationResult = utils.validateRoles(registerRequest.getRoles());
        if (!roleValidationResult.isValid())
            throw new CustomException(roleValidationResult.getMessage());

        User newUser = modelMapper.map(registerRequest, User.class);

        newUser.setActive(true);

        Department department = departmentRepository.findByName(registerRequest.getDepartmentName());
        newUser.setDepartment(department);

        ValidationResult validateWorkstationResult = utils.validateWorkstations(utils.extractUserRoleOtherThanAdmin(registerRequest.getRoles()).orElseThrow(), registerRequest.getWorkstationNames());
        if (!validateWorkstationResult.isValid()) {
            throw new CustomException(roleValidationResult.getMessage());
        }

        List<Workstation> workstationList = registerRequest.getWorkstationNames().stream().map(workstationRepository::findByName).toList();
        newUser.setWorkstations(workstationList);

        if (registerRequest.getUserDesignationId() != null) {
            UserDesignation userDesignation = userDesignationRepository.findById(registerRequest.getUserDesignationId()).orElseThrow(() -> new EntityNotFoundException("User Designation with Id " + registerRequest.getUserDesignationId() + " not found."));
            newUser.setUserDesignation(userDesignation);
        }

        List<Role> userRoles = registerRequest
                .getRoles()
                .stream()
                .map(roleRepository::findByName)
                .toList();
        newUser.setRoles(userRoles);

        User savedUser = userRepository.save(newUser);

        return utils.userEntityToUserDto(savedUser);
    }
}
