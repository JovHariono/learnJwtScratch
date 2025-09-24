package com.alibu2.spring_security_asymetric_encryption2.user;

import com.alibu2.spring_security_asymetric_encryption2.user.request.ProfileUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public void mergerUserInfo(User user, ProfileUpdateRequest request) {

        if (StringUtils.isNotBlank(request.getFirstName())
                && !user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNotBlank(request.getLastName())
                && !user.getLastName().equals(request.getLastName())) {
            user.setLastName(request.getLastName());
        }

        if (request.getDateOfBirth() != null
                && !request.getDateOfBirth().equals(user.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
    }
}
