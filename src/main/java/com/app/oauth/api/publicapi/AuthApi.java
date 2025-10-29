package com.app.oauth.api.publicapi;

import com.app.oauth.domain.dto.ApiResponseDTO;
import com.app.oauth.domain.dto.TokenDTO;
import com.app.oauth.domain.vo.MemberVO;
import com.app.oauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("로그인이 성공했습니다", tokens));
    }

    // 토큰 재발급
    @PostMapping("refresh")
    public ResponseEntity<ApiResponseDTO> refresh(@RequestBody TokenDTO tokenDTO){
        Map<String, String> response = new HashMap<String, String>();
        String newAccessToken = authService.reissueAccessToken(tokenDTO);
        response.put("accessToken", newAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("토큰이 재발급 되었습니다", response));
    }


    // 키를 교환



}















