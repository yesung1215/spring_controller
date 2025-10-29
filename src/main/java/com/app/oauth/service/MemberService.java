package com.app.oauth.service;

import com.app.oauth.domain.dto.MemberResponseDTO;
import com.app.oauth.domain.vo.MemberInsertSocialVO;
import com.app.oauth.domain.vo.MemberSocialVO;
import com.app.oauth.domain.vo.MemberVO;

import java.util.Map;

public interface MemberService {

    // 회원 아이디 조회
    public Long getMemberIdByMemberEmail(String memberEmail);

    // 회원 정보 조회
    public MemberResponseDTO getMemberById(Long id);

    // 이메일 중복 확인
    public boolean existsByMemberEmail(String memberEmail);

    // 회원 가입 후 로그인을 처리할 수 있도록
    public Map<String, String> register(MemberVO memberVO);

    // 회원 가입(소셜 로그인)
    public Map<String, String> registerSocial(MemberInsertSocialVO memberInsertSocialVO, MemberSocialVO memberSocialVO);

    // 회원 정보 수정
    public void modify(MemberVO memberVO);

    // 회원 탈퇴
    public void withdraw(Long id);

}
