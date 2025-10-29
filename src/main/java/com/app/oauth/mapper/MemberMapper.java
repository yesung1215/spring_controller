package com.app.oauth.mapper;

import com.app.oauth.domain.vo.MemberInsertSocialVO;
import com.app.oauth.domain.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    // 회원 조회
    public Optional<MemberVO> select(Long id);

    // 회원의 이메일로 아이디 조회
    public Long selectIdByMemberEmail(String memberEmail);

    // 회원 전체 조회
    public List<MemberVO> selectAll();

    // 이메일 중복 확인
    public boolean existsByMemberEmail(String memberEmail);

    // 회원 가입
    public void insert(MemberVO memberVO);

    // 회원 가입 (소셜)
    public void insertSocial(MemberInsertSocialVO memberInsertSocialVO);

    // 회원 정보 수정
    public void update(MemberVO memberVO);

    // 회원 탈퇴
    public void delete(Long id);

}
