package com.alibu2.spring_security_asymetric_encryption2.user.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
