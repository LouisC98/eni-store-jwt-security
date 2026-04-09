package tp.eni_store.rest.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.eni_store.dto.AuthDTO;
import tp.eni_store.response.ApiResponse;
import tp.eni_store.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AuthDTO dto) {
        ApiResponse<String> response = authService.register(dto);
        int httpStatus = response.getCode() == 206 ? 201 : 409;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody AuthDTO dto) {
        ApiResponse<String> response = authService.login(dto);
        int httpStatus = response.getCode() == 200 ? 200 : 401;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
