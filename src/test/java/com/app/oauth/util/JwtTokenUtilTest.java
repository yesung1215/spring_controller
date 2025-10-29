package com.app.oauth.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Slf4j
class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void generateTokenTest() {
        Map<String, String> claims = new HashMap<>();
        claims.put("memberEmail", "test123@gmail.com");
        String token = jwtTokenUtil.generateAccessToken(claims);
        log.info("payload: {}", jwtTokenUtil.getMemberEmailFromToken(token));
        log.info("expiryTime:{}", jwtTokenUtil.getTokenExpiry(token));
    }

}