package tp.eni_store.security;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tp.eni_store.response.ApiResponse;

import java.util.Arrays;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) return true;

        IsGranted isGranted = handlerMethod.getMethod().getAnnotation(IsGranted.class);
        if (isGranted == null) return true;

        String token = extractTokenFromCookies(request);
        if (token == null) {
            JsonResponseUtil.sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                    new ApiResponse<>(690, "Token manquant", false));
            return false;
        }

        try {
            Claims claims = jwtService.parseToken(token);
            if (!hasRequiredRole(claims, isGranted)) {
                JsonResponseUtil.sendJson(response, HttpServletResponse.SC_FORBIDDEN,
                        new ApiResponse<>(688, "Accès refusé", false));
                return false;
            }
        } catch (Exception e) {
            JsonResponseUtil.sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                    new ApiResponse<>(689, "Token invalide", false));
            return false;
        }

        return true;
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) return cookie.getValue();
        }
        return null;
    }

    private boolean hasRequiredRole(Claims claims, IsGranted isGranted) {
        String userRole = claims.get("role", String.class);
        return Arrays.asList(isGranted.roles()).contains(userRole);
    }
}
