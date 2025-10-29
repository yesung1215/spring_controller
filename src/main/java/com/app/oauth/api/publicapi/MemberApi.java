package com.app.oauth.api.publicapi;

import com.app.oauth.domain.dto.ApiResponseDTO;
import com.app.oauth.domain.vo.MemberVO;
import com.app.oauth.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members/*")
public class MemberApi {

    private final MemberService memberService;

    // 스웨거 추가
    // 회원가입
    @PostMapping("register")
    public ResponseEntity<ApiResponseDTO> register(@RequestBody MemberVO memberVO){
        memberService.register(memberVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("회원가입이 완료되었습니다")); // 201
    }

    // 회원수정
    @PutMapping("modify")
    public ResponseEntity<ApiResponseDTO> modify(@RequestBody MemberVO memberVO){
        memberService.modify(memberVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("정보 수정이 완료되었습니다.")); // 200
    }

    // 회원탈퇴
    @DeleteMapping("unregister")
    public ResponseEntity<ApiResponseDTO> unregister(@RequestBody Long id){
        memberService.withdraw(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.of("회원 탈퇴가 완료되었습니다.")); // 204
    }


}
