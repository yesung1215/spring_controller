package com.app.oauth.mapper;

import com.app.oauth.domain.vo.MemberSocialVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberSocialMapper {
    // 소셜 테이블에 추가
    public void insert(MemberSocialVO memberSocialVO);

    // 회원의 Provider를 전체조회
    public List<String> selectAll(Long id);

}
