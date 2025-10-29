package com.app.oauth.mapper;

import com.app.oauth.domain.vo.MemberSocialVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Slf4j
class MemberSocialMapperTest {

    @Autowired
    private MemberSocialMapper memberSocialMapper;

    @Test
    void insert() {
        MemberSocialVO memberSocialVO = new MemberSocialVO();
        memberSocialVO.setMemberSocialProviderId("test123");
        memberSocialVO.setMemberSocialProvider("google");
        memberSocialVO.setMemberId(2L);
        memberSocialMapper.insert(memberSocialVO);
    }

    @Test
    void selectAll() {
        log.info("{}", memberSocialMapper.selectAll(1L));
    }
}