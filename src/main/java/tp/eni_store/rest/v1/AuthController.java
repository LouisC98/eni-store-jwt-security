package tp.eni_store.rest.v1;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AuthDTO dto, HttpServletResponse httpResponse) {
        ApiResponse<String> response = authService.register(dto);
        int httpStatus = response.getCode() == 206 ? 201 : 409;
        if (response.getData() != null) {
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, buildTokenCookie(response.getData()).toString());
        }
        return ResponseEntity.status(httpStatus).body(new ApiResponse<>(response.getCode(), response.getMessage(), null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody AuthDTO dto, HttpServletResponse httpResponse) {
        ApiResponse<String> response = authService.login(dto);
        int httpStatus = response.getCode() == 200 ? 200 : 401;
        if (response.getData() != null) {
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, buildTokenCookie(response.getData()).toString());
        }
        return ResponseEntity.status(httpStatus).body(new ApiResponse<>(response.getCode(), response.getMessage(), null));
    }

    private ResponseCookie buildTokenCookie(String token) {
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .maxAge(86400)
                .sameSite("Lax")
                .secure(true)
                .build();
    }
}
