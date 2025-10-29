package com.app.oauth.domain.vo;

import lombok.Data;

@Data
public class MemberSocialVO {
    private Long id;
    private String memberSocialProviderId;
    private String memberSocialProvider;
    private Long memberId;
}
