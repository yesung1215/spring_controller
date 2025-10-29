package com.app.oauth.domain.vo;

import lombok.Data;

@Data
public class MemberInsertSocialVO {
    private Long id;
    private String memberEmail;
    private String memberPicturePath;
    private String memberPictureName;
    private String memberName;
    private String memberNickname;
    private String memberProvider;

    {
        this.setMemberPicturePath("/default");
        this.setMemberPictureName("member.jpg");
        this.setMemberNickname("개복치 1단계");
        this.setMemberProvider("local");
    }

}
