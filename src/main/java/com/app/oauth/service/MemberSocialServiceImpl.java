package com.app.oauth.service;

import com.app.oauth.domain.vo.MemberSocialVO;
import com.app.oauth.repository.MemberSocialDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberSocialServiceImpl implements MemberSocialService {

    private final MemberSocialDAO memberSocialDAO;

    @Override
    public void registerMemberSocial(MemberSocialVO memberSocialVO) {
        memberSocialDAO.save(memberSocialVO);
    }

    @Override
    public List<String> findAllProvidersById(Long id) {
        return memberSocialDAO.findSocialProvidersById(id);
    }
}
