package com.alibu2.spring_security_asymetric_encryption2.auth.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {

    private String refreshToken;
}
