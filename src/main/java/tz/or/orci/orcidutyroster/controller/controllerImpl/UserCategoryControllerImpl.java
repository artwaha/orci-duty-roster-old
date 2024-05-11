package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.UserCategoryController;
import tz.or.orci.orcidutyroster.model.entities.UserCategory;
import tz.or.orci.orcidutyroster.payload.request.UserCategoryDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.UserCategoryService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserCategoryControllerImpl implements UserCategoryController {
    private final UserCategoryService userCategoryService;

    @Override
    public ResponseEntity<UserCategory> addUserCategory(UserCategoryDto userCategoryDto) {
        return ResponseEntity.status(CREATED).body(userCategoryService.addUserCategory(userCategoryDto));
    }

    @Override
    public ResponseEntity<UserCategory> getUserCategoryById(Long id) {
        return ResponseEntity.ok(userCategoryService.getUserCategoryById(id));
    }

    @Override
    public ResponseEntity<GenericResponse<UserCategory>> getAllUserCategories(int pageNumber, int pageSize) {
        return ResponseEntity.ok(userCategoryService.getAllUserCategories(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<GenericResponse<UserCategory>> getAllUserCategoriesByDepartment(Long id, int pageNumber, int pageSize) {
        return ResponseEntity.ok(userCategoryService.getAllUserCategoriesByDepartment(id, pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<UserCategory> updateUserCategory(Long id, UserCategoryDto userCategoryDto) {
        return ResponseEntity.ok(userCategoryService.updateUserCategory(id, userCategoryDto));
    }
}
