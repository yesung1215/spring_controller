package com.app.oauth.handler;

import com.app.oauth.domain.dto.MemberResponseDTO;
import com.app.oauth.service.MemberService;
import com.app.oauth.util.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;

    // 소셜로그인 인가된 데이터가 들어온다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication instanceof OAuth2AuthenticationToken authToken){
            OAuth2User user = authToken.getPrincipal();
            Map<String,Object> attributes = user.getAttributes();
//            naver, google, kakao
            String memberProvider = authToken.getAuthorizedClientRegistrationId();
            String memberEmail = null;
            String memberName = null;
            String providerId = null;
            Long memberId = null;
            Map<String,String> tokens = new HashMap<String, String>();

            log.info("user: {}", user);
            // 1. 어디로 들어왔는지를 확인
            if(memberProvider.equals("google")){
                memberEmail = (String)attributes.get("email");
                memberName = (String)attributes.get("name");
                providerId = (String)attributes.get("sub");
            } else if(memberProvider.equals("naver")){
                memberEmail = (String)attributes.get("email");
                memberName = (String)attributes.get("name");
                providerId = (String)attributes.get("id");
            } else if(memberProvider.equals("kakao")){
                memberEmail = (String)attributes.get("email");
                memberName = (String)attributes.get("nickname");
                providerId = (String)attributes.get("id");
            }

            // 2. 이미 회원가입이 되어있는지
            if(memberService.existsByMemberEmail(memberEmail)){
                memberId = memberService.getMemberIdByMemberEmail(memberEmail);
                MemberResponseDTO foundMember = memberService.getMemberById(memberId);

            // 3. 어디로 접속했는지 확인!
            // 4. 이미 회원가입이라면 토큰 발급



            } else {
            // 5. 신규 회원가입 토큰 발급

            }

            // 6. redis로 토큰을 등록
            // 7. 프론트 React 리다이렉트(3000번포트로)

        }
    }
}



















