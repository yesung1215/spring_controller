package com.app.oauth.repository;

import com.app.oauth.domain.vo.MemberInsertSocialVO;
import com.app.oauth.domain.vo.MemberSocialVO;
import com.app.oauth.domain.vo.MemberVO;
import com.app.oauth.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDAO {
    private final MemberMapper memberMapper;

    // 회원 조회
    public Optional<MemberVO> findById(Long id){
        return memberMapper.select(id);
    }

    // 전체 조회
    public List<MemberVO> findAll(){
        return memberMapper.selectAll();
    }

    // 이메일로 아이디 찾기
    public Long findIdByMemberEmail(String memberEmail){
        return memberMapper.selectIdByMemberEmail(memberEmail);
    }

    // 이메일 중복 확인
    public boolean existsByMemberEmail(String memberEmail){
        return memberMapper.existsByMemberEmail(memberEmail);
    }

    // 회원 가입
    public void save(MemberVO memberVO){
        memberMapper.insert(memberVO);
    }

    // 회원 가입(소셜용)
    public void saveSocialMember(MemberInsertSocialVO memberInsertSocialVO){
        memberMapper.insertSocial(memberInsertSocialVO);
    }

    // 회원 수정
    public void update(MemberVO memberVO){
        memberMapper.update(memberVO);
    }

    // 회원 탈퇴
    public void delete(Long id){
        memberMapper.delete(id);
    }
}
