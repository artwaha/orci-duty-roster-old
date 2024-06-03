package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.UserDesignationController;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;
import tz.or.orci.orcidutyroster.payload.request.UserDesignationDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.UserDesignationService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserDesignationControllerImpl implements UserDesignationController {
    private final UserDesignationService userDesignationService;

    @Override
    public ResponseEntity<UserDesignation> addUserDesignation(UserDesignationDto userDesignationDto) {
        return ResponseEntity.status(CREATED).body(userDesignationService.addUserDesignation(userDesignationDto));
    }

    @Override
    public ResponseEntity<UserDesignation> getUserCategoryById(Long id) {
        return ResponseEntity.ok(userDesignationService.getUserCategoryById(id));
    }

    @Override
    public ResponseEntity<GenericResponse<UserDesignation>> getAllUserCategories(int pageNumber, int pageSize) {
        return ResponseEntity.ok(userDesignationService.getAllUserCategories(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<UserDesignation> updateUserCategory(Long id, UserDesignationDto userDesignationDto) {
        return ResponseEntity.ok(userDesignationService.updateUserCategory(id, userDesignationDto));
    }
}
