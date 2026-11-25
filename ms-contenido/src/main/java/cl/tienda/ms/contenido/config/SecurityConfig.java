package cl.tienda.ms.contenido.config;
import cl.tienda.ms.contenido.dto.ErrorResponse;
import cl.tienda.ms.contenido.security.JwtAuthenticationFilter;
import cl.tienda.ms.contenido.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwt,
            ObjectMapper mapper, @Qualifier("corsConfigurationSource") CorsConfigurationSource cors) throws Exception {
        return http.csrf(c -> c.disable()) // API stateless; no autenticación por cookies.
            .cors(c -> c.configurationSource(cors))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(f -> f.disable()).httpBasic(b -> b.disable()).logout(l -> l.disable())
            .authorizeHttpRequests(a -> a
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/blogs", "/api/blogs/**", "/api/categorias-blog", "/api/categorias-blog/**", "/api/comentarios", "/api/comentarios/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contactos").permitAll()
                .requestMatchers("/api/comentarios", "/api/comentarios/**").hasAnyRole("ADMIN", "CLIENTE")
                .requestMatchers("/api/blogs", "/api/blogs/**", "/api/categorias-blog", "/api/categorias-blog/**", "/api/contactos", "/api/contactos/**").hasRole("ADMIN")
                .anyRequest().denyAll())
            .exceptionHandling(e -> e
                .authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(401); res.setContentType("application/json;charset=UTF-8");
                    res.setHeader("WWW-Authenticate", "Bearer");
                    mapper.writeValue(res.getOutputStream(), new ErrorResponse(401, "Token ausente, inválido o vencido"));
                })
                .accessDeniedHandler((req, res, ex) -> {
                    res.setStatus(403); res.setContentType("application/json;charset=UTF-8");
                    mapper.writeValue(res.getOutputStream(), new ErrorResponse(403, "No tienes permisos para este recurso"));
                }))
            .addFilterBefore(new JwtAuthenticationFilter(jwt), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origins}") String origins) {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(Arrays.stream(origins.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList());
        c.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        c.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        c.setExposedHeaders(List.of("Location"));
        c.setAllowCredentials(false);
        c.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", c);
        return source;
    }
}
