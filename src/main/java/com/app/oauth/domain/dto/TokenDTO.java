package com.app.oauth.domain.dto;

import lombok.Data;

@Data
public class TokenDTO {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
}
