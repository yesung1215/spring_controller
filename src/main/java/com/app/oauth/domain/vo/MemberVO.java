package com.app.oauth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
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

    public MemberVO(MemberInsertSocialVO memberInsertSocialVO) {
        this.id = memberInsertSocialVO.getId();
        this.memberEmail = memberInsertSocialVO.getMemberEmail();
        this.memberPicturePath = memberInsertSocialVO.getMemberPicturePath();
        this.memberPictureName = memberInsertSocialVO.getMemberPictureName();
        this.memberName = memberInsertSocialVO.getMemberName();
        this.memberNickname = memberInsertSocialVO.getMemberNickname();
        this.memberProvider = memberInsertSocialVO.getMemberProvider();
    }

}
