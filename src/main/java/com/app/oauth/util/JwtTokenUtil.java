package com.app.oauth.util;

import com.app.oauth.exception.JwtTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // Access Token 생성
    public String generateAccessToken(Map<String, String> claims) {
        String memberEmail = claims.get("memberEmail");

        Long expirationTimeInMillis =  1000 * 60 * 30L;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeInMillis);

        return Jwts.builder()
                .claim("memberEmail", memberEmail) // 클레임 추가(이메일)
                .setExpiration(expirationDate) // 만료시간
                .setIssuer("sehwan")
                .signWith(SignatureAlgorithm.HS256, secretKey) // SHA-256 알고리즘
                .setHeaderParam("type", "JWT") // JWT 타입
                .compact(); // 생성
    }

    // Refresh Token 생성
    public String generateRefreshToken(Map<String, String> claims) {
        String memberEmail = claims.get("memberEmail");

        Long expirationTimeInMillis =  1000 * 60 * 30 * 24L;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeInMillis);

        return Jwts.builder()
                .claim("memberEmail", memberEmail) // 클레임 추가(이메일)
                .setExpiration(expirationDate) // 만료시간
                .setIssuer("sehwan")
                .signWith(SignatureAlgorithm.HS256, secretKey) // SHA-256 알고리즘
                .setHeaderParam("type", "JWT") // JWT 타입
                .compact(); // 생성
    }

    // 토큰이 유효한지 검사
    public boolean verifyJwtToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) { // 파싱이 안될 때
            return false;
        } catch (JwtException | IllegalArgumentException e) { // 변조된 토큰, 잘못된 토큰
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰으로 이메일 정보를 추출
    public Claims getMemberEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // 토큰의 남은 시간을 확인
    public Long getTokenExpiry(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            Date now = new Date();

            Long remainingTime = expiration.getTime() - now.getTime(); // ms
            return remainingTime > 0 ? remainingTime : 0L;
        } catch (ExpiredJwtException e) { // 파싱이 안될 때
            return 0L;
        } catch (JwtException | IllegalArgumentException e) { // 변조된 토큰, 잘못된 토큰
            throw new JwtTokenException("유효하지 않은 토큰");
        }
    }

}
