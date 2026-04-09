package tp.eni_store.security;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
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

        // HandlerMethod = une méthode Java d'un controller
        if (handler instanceof HandlerMethod handlerMethod) {

            // Est-ce que la méthode a mon annotation ?
            IsGranted isGranted = handlerMethod.getMethod().getAnnotation(IsGranted.class);

            // Si pas d'annotation, comportement normal par défaut
            if (isGranted == null) return true;

            // Guard : vérifier que le header Authorization est présent et bien formé
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                JsonResponseUtil.sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                        new ApiResponse<>(690, "Token manquant", false));
                return false;
            }

            String token = authHeader.substring(7);

            // Vérifier, décoder le token et contrôler le rôle
            try {
                Claims claims = jwtService.parseToken(token);

                String userRole = claims.get("role", String.class);
                boolean hasRole = Arrays.asList(isGranted.roles()).contains(userRole);

                if (!hasRole) {
                    JsonResponseUtil.sendJson(response, HttpServletResponse.SC_FORBIDDEN,
                            new ApiResponse<>(688, "Accès refusé", false));
                    return false;
                }

            } catch (Exception e) {
                JsonResponseUtil.sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                        new ApiResponse<>(689, "Token invalide", false));
                return false;
            }
        }

        return true;
    }
}
