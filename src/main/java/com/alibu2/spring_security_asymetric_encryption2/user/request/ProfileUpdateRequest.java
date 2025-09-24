package com.alibu2.spring_security_asymetric_encryption2.user.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateRequest {

    //assume cant change email & phoneNumber
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
