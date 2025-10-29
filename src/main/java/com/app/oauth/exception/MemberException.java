package com.app.oauth.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberException extends RuntimeException {
    public MemberException(String message) {
        super(message);
    }
}
