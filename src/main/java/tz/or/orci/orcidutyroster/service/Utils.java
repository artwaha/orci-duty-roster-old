package tz.or.orci.orcidutyroster.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.model.entities.Token;
import tz.or.orci.orcidutyroster.model.entities.User;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;
import tz.or.orci.orcidutyroster.payload.response.ErrorResponseDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.payload.response.UserDto;
import tz.or.orci.orcidutyroster.payload.response.ValidationResult;
import tz.or.orci.orcidutyroster.repository.TokenRepository;

import java.util.List;
import java.util.Map;

import static tz.or.orci.orcidutyroster.model.enums.RoleEnum.*;

@Component
@RequiredArgsConstructor
public class Utils {
    private final LdapTemplate ldapTemplate;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean userExistsInActiveDirectory(String username) {
        List<String> searchResult = ldapTemplate.search(
                "OU=Ocean Road Cancer Institute(ORCI)",
                "(sAMAccountName=" + username + ")",
                (AttributesMapper<String>) attributes -> attributes.get("cn").get().toString()
        );
        return !searchResult.isEmpty();
    }

    public void saveUserToken(String token, User user) {
        Token newToken = Token.builder().token(token).revoked(false).user(user).build();
        tokenRepository.save(newToken);
    }

    public void revokeAllUserTokens(User user) {
        List<Token> allTokensByUser = tokenRepository.findByUserAndRevokedFalse(user);
        allTokensByUser.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(allTokensByUser);
    }

    public ErrorResponseDto generateErrorResponse(HttpStatus status, String path, String message, Map<String, String> details) {
        return ErrorResponseDto
                .builder()
                .path(path)
                .status(status.value())
                .message(message)
                .details(details)
                .build();
    }

    public <T> GenericResponse<T> generateGenericResponse(int status, int pageNumber, int pageSize, int totalPages, long totalElements, List<T> data) {
        return GenericResponse
                .<T>builder()
                .status(status)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .data(data)
                .build();
    }

    public UserDto userEntityToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        if (user.getUserCategory() != null)
            userDto.setUserCategory(user.getUserCategory());
        return userDto;
    }

    public ValidationResult validateRoles(List<RoleEnum> roles) {
        ValidationResult result = new ValidationResult();

        // Rule 0: If size is one, role can't be ADMIN
        if (roles.size() == 1 && roles.contains(ADMIN)) {
            result.setMessage("Role ADMIN must be paired with another Role.");
            result.setValid(false);
            return result;
        }

        // Rule 1: Make sure user can have at most 2 roles
        if (roles.size() > 2) {
            result.setMessage("User cannot be assigned more than 2 roles.");
            result.setValid(false);
            return result;
        }

        // Rule 2: Disallow duplicate ADMIN roles
        long adminCount = roles.stream().filter(role -> role == ADMIN).count();
        if (adminCount > 1) {
            result.setMessage("Duplicate ADMIN roles are not allowed.");
            result.setValid(false);
            return result;
        }

        // Rule 3: If user has 2 roles, one of them must be ADMIN
        if (roles.size() == 2 && !roles.contains(ADMIN)) {
            result.setMessage("If user has multiple roles, one of them must be ADMIN.");
            result.setValid(false);
            return result;
        }

        // Rule 4: Only a combination of ADMIN and either HEAD_OF_DEPARTMENT,
        // SUPERVISOR, STAFF, or VOLUNTEER is allowed
        if (roles.size() == 2 && roles.contains(ADMIN)) {
            List<RoleEnum> allowedCombinations = List.of(ADMIN, HEAD_OF_DEPARTMENT, SUPERVISOR, STAFF, VOLUNTEER);
            List<RoleEnum> invalidRoles = roles.stream()
                    .filter(role -> !allowedCombinations.contains(role))
                    .toList();
            if (!invalidRoles.isEmpty()) {
                result.setMessage("Invalid User-roles combination. If ADMIN is present, it should be only combined with HEAD_OF_DEPARTMENT, SUPERVISOR, STAFF, or VOLUNTEER.");
                result.setValid(false);
                return result;
            }
        }

        result.setValid(true);
        return result;
    }

    public ValidationResult validateRole(RoleEnum role) {
        ValidationResult validationResult = new ValidationResult();
        if (role == ADMIN) {
            validationResult.setValid(false);
            validationResult.setMessage("For Self Registration, user can oly be assigned Roles: Staff | Intern | Volunteer | Supervisor | Head of Department");
        } else {
            validationResult.setValid(true);
        }

        return validationResult;
    }
}
