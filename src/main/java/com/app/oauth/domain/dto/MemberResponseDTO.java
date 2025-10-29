package com.app.oauth.domain.dto;

import com.app.oauth.domain.vo.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// Serializable
// Redis에 저장하려는 객체가 반드시 java.io.Serializable 인터페이스를 구현해야한다.

@Data
@NoArgsConstructor @AllArgsConstructor
public class MemberResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public MemberResponseDTO(MemberVO memberVO) {
        this.id =  memberVO.getId();
        this.memberEmail = memberVO.getMemberEmail();
        this.memberPicturePath = memberVO.getMemberPicturePath();
        this.memberPictureName = memberVO.getMemberPictureName();
        this.memberName = memberVO.getMemberName();
        this.memberNickname = memberVO.getMemberNickname();
        this.memberProvider = memberVO.getMemberProvider();
    }


}
