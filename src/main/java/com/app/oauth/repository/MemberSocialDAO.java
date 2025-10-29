package com.app.oauth.repository;

import com.app.oauth.domain.vo.MemberSocialVO;
import com.app.oauth.mapper.MemberSocialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSocialDAO {

    final MemberSocialMapper memberSocialMapper;

    // 소셜 정보 추가
    public void save(MemberSocialVO memberSocialVO){
        memberSocialMapper.insert(memberSocialVO);
    }

    // 소셜 프로바이더 조회
    public List<String> findSocialProvidersById(Long id){
        return memberSocialMapper.selectAll(id);
    }
}
