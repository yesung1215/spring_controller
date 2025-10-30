package com.app.oauth.api.publicapi;

import com.app.oauth.domain.dto.ApiResponseDTO;
import com.app.oauth.domain.dto.TokenDTO;
import com.app.oauth.domain.vo.MemberVO;
import com.app.oauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth/*")
public class AuthApi {

    private final AuthService authService;
    private final RedisTemplate redisTemplate;

    // 로그인
    @PostMapping("login")
    public ResponseEntity<ApiResponseDTO> login(@RequestBody MemberVO memberVO){
        Map<String, String> tokens = authService.login(memberVO);


//        refreshToken은 cookie로 전달
//        cookie: 웹 브라우저로 전송하는 단순한 문자열(세션, refreshToken)
//        XSS 탈취 위험을 방지하기 위해서 http Only로 안전하게 처리한다. 즉, JS로 접근할 수 없다.
        String refreshToken = tokens.get("refreshToken");
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true) // *필수
//                .secure(true) // https에서 사용
                .path("/") // 모든 경로에 쿠키 전송 사용
                .maxAge(60 * 60 * 24 * 7)
                .build();

        tokens.remove(refreshToken);
//        accessToken은 그대로 발급
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString()) // 브라우저에 쿠키를 심는다.
                .body(ApiResponseDTO.of("로그인이 성공했습니다", tokens));
    }

    // 토큰 재발급
    @PostMapping("refresh")
    public ResponseEntity<ApiResponseDTO> refresh(@CookieValue("refreshToken") String refreshToken,@RequestBody TokenDTO tokenDTO){
        Map<String, String> response = new HashMap<String, String>();
        tokenDTO.setRefreshToken(refreshToken);
        String newAccessToken = authService.reissueAccessToken(tokenDTO);
        response.put("accessToken", newAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("토큰이 재발급 되었습니다", response));
    }


    // 키를 교환
    @GetMapping("/oauth2/success")
    public ResponseEntity<ApiResponseDTO> oauth2Success(@RequestParam("key") String key){
        Map<String, String> tokens = redisTemplate.opsForHash().entries(key);
        if(tokens == null || tokens.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseDTO.of("유효 시간 만료", null));
        }
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("로그인 성공", tokens));
    }
}












