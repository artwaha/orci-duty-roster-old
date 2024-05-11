package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.UserController;
import tz.or.orci.orcidutyroster.payload.request.UserUpdateRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.payload.response.UserDto;
import tz.or.orci.orcidutyroster.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<GenericResponse<UserDto>> getUsers(Boolean active, int pageNumber, int pageSize) {
        return ResponseEntity.ok(userService.getUsers(active, pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long id, UserUpdateRequestDto updateRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateRequest));
    }
}
