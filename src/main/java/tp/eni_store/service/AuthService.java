package tp.eni_store.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tp.eni_store.bo.User;
import tp.eni_store.dao.UserDAO;
import tp.eni_store.dto.AuthDTO;
import tp.eni_store.helper.LocaleHelper;
import tp.eni_store.response.ApiResponse;
import tp.eni_store.security.JwtService;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final JwtService jwtService;
    private final LocaleHelper localeHelper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserDAO userDAO, JwtService jwtService, LocaleHelper localeHelper) {
        this.userDAO = userDAO;
        this.jwtService = jwtService;
        this.localeHelper = localeHelper;
    }

    public ApiResponse<String> register(AuthDTO dto) {
        User foundUser = userDAO.selectByUsername(dto.getUsername());

        if (foundUser != null) {
            return new ApiResponse<>(706, localeHelper.getMessage("auth.register.exists"), null);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_USER");
        userDAO.save(user);

        return new ApiResponse<>(206, localeHelper.getMessage("auth.register.success"), jwtService.generateToken(user));
    }

    public ApiResponse<String> login(AuthDTO dto) {
        User foundUser = userDAO.selectByUsername(dto.getUsername());

        if (foundUser == null || !passwordEncoder.matches(dto.getPassword(), foundUser.getPassword())) {
            return new ApiResponse<>(707, localeHelper.getMessage("auth.login.failed"), null);
        }

        return new ApiResponse<>(200, localeHelper.getMessage("auth.login.success"), jwtService.generateToken(foundUser));
    }
}
