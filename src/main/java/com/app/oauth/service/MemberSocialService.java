package com.app.oauth.service;

import com.app.oauth.domain.vo.MemberSocialVO;

import java.util.List;

public interface MemberSocialService {
    public void registerMemberSocial(MemberSocialVO memberSocialVO);
    public List<String> findAllProvidersById(Long id);
}
