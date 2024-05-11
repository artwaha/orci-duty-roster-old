package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.AuthController;
import tz.or.orci.orcidutyroster.payload.request.LoginRequestDto;
import tz.or.orci.orcidutyroster.payload.request.RegisterByAdminRequestDto;
import tz.or.orci.orcidutyroster.payload.request.SelfRegisterRequestDto;
import tz.or.orci.orcidutyroster.payload.response.AuthResponseDto;
import tz.or.orci.orcidutyroster.payload.response.UserDto;
import tz.or.orci.orcidutyroster.service.AuthService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public void logout() {

    }

    @Override
    public ResponseEntity<AuthResponseDto> login(LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Override
    public ResponseEntity<AuthResponseDto> selfRegister(SelfRegisterRequestDto registerRequest) {
        return ResponseEntity.ok(authService.selfRegister(registerRequest));
    }

    @Override
    public ResponseEntity<UserDto> registerByAdmin(RegisterByAdminRequestDto registerRequest) {
        return ResponseEntity.status(CREATED).body(authService.registerByAdmin(registerRequest));
    }
}
