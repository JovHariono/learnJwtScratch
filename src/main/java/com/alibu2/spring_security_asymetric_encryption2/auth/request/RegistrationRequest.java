package com.alibu2.spring_security_asymetric_encryption2.auth.request;

import com.alibu2.spring_security_asymetric_encryption2.validation.NonDisposableEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotBlank(message = "VALIDATION.REGISTRATION.FIRST_NAME.NOT_BLANK")
    @Size(
            min = 3,
            max = 50,
            message = "VALIDATION.REGISTRATION.FIRST_NAME.SIZE"
    )
    @Pattern(
            regexp = "^[\\p{L} '-]+$",
            message = "VALIDATION.REGISTRATION.FIRST_NAME.PATTERN"
    )
    @Schema(example = "Ali")
    private String firstName;

    @NotBlank(message = "VALIDATION.REGISTRATION.LAST_NAME.NOT_BLANK")
    @Size(
            min = 3,
            max = 50,
            message = "VALIDATION.REGISTRATION.LAST_NAME.SIZE"
    )
    @Pattern(
            regexp = "^[\\p{L} '-]+$",
            message = "VALIDATION.REGISTRATION.LAST_NAME.PATTERN"
    )
    @Schema(example = "Ali")
    private String lastName;

    @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.NOT_BLANK")
    @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT")
    @Schema(example = "ali@mail.com")
    @NonDisposableEmail(message = "VALIDATION.REGISTRATION.EMAIL.DISPOSABLE")
    private String email;

    @NotBlank(message = "VALIDATION.REGISTRATION.PHONE_NUMBER.NOT_BLANK")
    @Pattern(
            regexp = "^\\+?[0-9]{10,13}$",
            message = "VALIDATION.REGISTRATION.PHONE.FORMAT"
    )
    @Schema(example = "+6217519361231")
    private String phoneNumber;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 50,
            message = "VALIDATION.REGISTRATION.PASSWORD.SIZE"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$"
    )
    @Schema(example = "<PASSWORD>")
    private String password;
    @NotBlank(message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 50,
            message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.SIZE"
    )
    private String confirmPassword;
}
