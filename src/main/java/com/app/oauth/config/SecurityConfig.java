package com.app.oauth.config;

import com.app.oauth.domain.dto.TokenDTO;
import com.app.oauth.filter.JwtAuthenticationFilter;
import com.app.oauth.handler.JwtAuthenticationEntryPoint;
import com.app.oauth.handler.OAuth2LoginSuccessHandler;
import com.app.oauth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final AuthService authService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())      // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/private/**").authenticated()
                    .anyRequest().permitAll() // 모든 요청 허용
            )
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2Login(oauth2 -> oauth2
                    .successHandler(oAuth2LoginSuccessHandler))
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("http://localhost:3000/sign-in")
                    .logoutSuccessHandler((request, response, authentication) -> {
                        HttpSession session = request.getSession(false);
                        if(session != null) {
                            session.invalidate();
                        }

//                        쿠키를 들고와서 삭제한다
                        Cookie[] cookies = request.getCookies();
                        if(cookies != null) {
                            for(Cookie cookie : cookies) {
                                if(cookie.getName().equals("refreshToken")) {
//                                    블랙리스트에 추가해서 접근 못하게 막는다
                                    String refreshToken = cookie.getValue();
                                    TokenDTO tokenDTO = new TokenDTO();
                                    tokenDTO.setRefreshToken(refreshToken);
                                    authService.saveBlacklistedToken(refreshToken);
                                }
                            }
                        }

//                        리프레쉬 토큰을 블랙리스트에 추가

                        response.sendRedirect("http://localhost:3000");
                    })
                    .permitAll()
            );

        return http.build();
    }


    // Cors 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // React 앱 주소
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 요청 헤더 허용
        configuration.setAllowCredentials(true); // 인증 정보 허용 - cookie 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
        return source;
    }

}