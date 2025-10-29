package com.app.oauth.api.privateapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/private/my-page/*")
public class MyPageApi {

    @PostMapping("private-test")
    public void privateTest(Authentication authentication){
        log.info(authentication.getPrincipal().toString());
    }
}
